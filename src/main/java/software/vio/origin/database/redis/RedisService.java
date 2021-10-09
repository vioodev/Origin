package software.vio.origin.database.redis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import software.vio.origin.Origin;
import software.vio.origin.database.redis.subscriber.RedisSubscriber;
import software.vio.origin.database.redis.subscriber.RedisSubscriberEntry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@Getter
public class RedisService {

    public static final String SPLIT_CHAR = "<==>";
    public static final JsonParser PARSER = new JsonParser();

    private final Origin origin;

    private final RedisConfig config;
    private final JedisPoolConfig poolConfig;

    private final Map<String, RedisSubscriber> subscribers;

    private JedisPool publisherPool, subscriberPool;
    private JedisPubSub pubsub;

    public RedisService(Origin origin) {
        this.origin = origin;

        this.subscribers = new HashMap<>();

        // All subscribers are initialized here
        new RedisSubscriberEntry(this);

        // Build our config
        this.config = this.buildConfig();
        this.poolConfig = this.buildPoolConfig();

        // Connect and start listening to our pub/sub
        this.connect();
        this.listen();
    }

    public void send(RedisMessage message) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try (Jedis jedis = this.publisherPool.getResource()) {
                if (this.config.isAuthentication()) jedis.auth(this.config.getPassword());

                jedis.publish(this.config.getChannel(), message.get());
            }
        });
    }

    public void send(String payload, JsonObject object) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try (Jedis jedis = this.publisherPool.getResource()) {
                if (this.config.isAuthentication()) jedis.auth(this.config.getPassword());

                jedis.publish(this.config.getChannel(), new RedisMessage(payload, object).get());
            }
        });
    }

    public void disconnect() {
        this.publisherPool.close();
        this.subscriberPool.close();
    }

    private void connect() {
        this.publisherPool = this.subscriberPool =
                new JedisPool(this.poolConfig, this.config.getHost(), this.config.getPort(), 20000, 1000, null, 0, null);
    }

    private void listen() {
        this.pubsub = new RedisListener(this);

        Executors.newSingleThreadExecutor().execute(() -> {
            try (Jedis jedis = this.subscriberPool.getResource()) {
                if (this.config.isAuthentication()) jedis.auth(this.config.getPassword());

                jedis.subscribe(this.pubsub, this.config.getChannel());
            }
        });
    }

    private RedisConfig buildConfig() {
        return new RedisConfig()
                .setHost(this.origin.getConfig().getString("REDIS.HOST"))
                .setPort(this.origin.getConfig().getInt("REDIS.PORT"))
                .setChannel(this.origin.getConfig().getString("REDIS.CHANNEL"))
                .setAuthentication(this.origin.getConfig().getBoolean("REDIS.AUTHENTICATION"))
                .setPassword(this.origin.getConfig().getString("REDIS.PASSWORD"));
    }

    private JedisPoolConfig buildPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(32);
        poolConfig.setMaxWaitMillis(3000L);
        poolConfig.setMinEvictableIdleTime(Duration.ofSeconds(30L));
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setTestOnBorrow(false);
        return poolConfig;
    }
}
