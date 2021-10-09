package software.vio.origin.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class JsonBuilder {

    private JsonObject object;

    public JsonBuilder add(String property, Number value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, String value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, Boolean value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, Character value) {
        this.object.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, JsonElement element) {
        this.object.add(property, element);
        return this;
    }

    public JsonObject get() {
        return this.object;
    }
}
