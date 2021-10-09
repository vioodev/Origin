package software.vio.origin.database.redis.subscriber;

import software.vio.origin.database.redis.RedisService;
import software.vio.origin.database.redis.subscriber.impl.ExampleSubscriber;

import java.util.stream.Stream;

public class RedisSubscriberEntry {

    public RedisSubscriberEntry(RedisService service) {
        Stream.of(
                new ExampleSubscriber()
        ).forEach(subscriber -> service.getSubscribers().put(subscriber.getPayload(), subscriber));
    }
}
