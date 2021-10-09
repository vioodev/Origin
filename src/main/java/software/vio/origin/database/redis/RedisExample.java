package software.vio.origin.database.redis;

import software.vio.origin.util.JsonBuilder;

public class RedisExample extends Thread {

    private final RedisService service;

    public RedisExample(RedisService service) {
        super("Redis Sender - Example");

        this.service = service;

        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.service.send("example", new JsonBuilder()
                        .add("subject", "Hello World!")
                        .get());
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
