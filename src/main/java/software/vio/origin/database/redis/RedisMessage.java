package software.vio.origin.database.redis;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RedisMessage {

    private final String payload;
    private final JsonObject object;

    public String get() {
        return this.payload + RedisService.SPLIT_CHAR + this.object.toString();
    }
}
