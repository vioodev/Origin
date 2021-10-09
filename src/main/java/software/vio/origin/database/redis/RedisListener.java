package software.vio.origin.database.redis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.JedisPubSub;
import software.vio.origin.util.CC;
import software.vio.origin.util.Msg;

@RequiredArgsConstructor
public class RedisListener extends JedisPubSub {

    private final RedisService service;

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(this.service.getConfig().getChannel())) return;

        String[] split = message.split(RedisService.SPLIT_CHAR);
        if (split.length < 2) return;
        String payload = split[0];

        try {
            JsonObject object = RedisService.PARSER.parse(split[1]).getAsJsonObject();

            this.service.getSubscribers().get(payload).onMessage(new RedisMessage(payload, object));
        } catch (JsonParseException e) {
            Msg.log(CC.GOLD + "Origin " + CC.YELLOW + "has failed to parse a redis message with payload " + CC.GOLD + payload + CC.YELLOW + ". Reason: " + CC.GOLD + e.getMessage());
        }
    }
}
