package software.vio.origin.database.redis.subscriber;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.vio.origin.database.redis.RedisMessage;

@Getter
@RequiredArgsConstructor
public abstract class RedisSubscriber {

    private final String payload;

    public abstract void onMessage(RedisMessage message);
}
