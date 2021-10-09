package software.vio.origin.database.redis.subscriber.impl;

import software.vio.origin.database.redis.RedisMessage;
import software.vio.origin.database.redis.subscriber.RedisSubscriber;

public class ExampleSubscriber extends RedisSubscriber {

    public ExampleSubscriber() {
        super("example");
    }

    @Override
    public void onMessage(RedisMessage message) {
        System.out.println(message.getObject().get("message").getAsString());
    }
}
