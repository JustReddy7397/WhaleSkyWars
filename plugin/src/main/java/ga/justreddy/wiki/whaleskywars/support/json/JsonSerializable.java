package ga.justreddy.wiki.whaleskywars.support.json;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * @author JustReddy
 */
public abstract class JsonSerializable {

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonParseException {
        if (json == null || json.isEmpty())
            return null;

        try {
            return GsonHelper.GSON.fromJson(json, clazz);
        } catch (JsonSyntaxException ex) {
            throw new JsonParseException("Failed to parse JSON for " + clazz.getName(), ex);
        }
    }

    public static <T> T fromJson(String json, Type type) throws JsonParseException {
        if (json == null || json.isEmpty())
            return null;

        try {
            return GsonHelper.GSON.fromJson(json, type);
        } catch (JsonSyntaxException ex) {
            throw new JsonParseException("Failed to parse JSON for " + type.getTypeName(), ex);
        }
    }

    public static <T> T fromJson(Reader reader, Class<T> clazz) throws JsonParseException {
        try {
            return GsonHelper.GSON.fromJson(reader, clazz);
        } catch (JsonSyntaxException ex) {
            throw new JsonParseException("Failed to parse JSON for " + clazz.getName(), ex);
        }
    }

    public static <T> T fromJson(Reader reader, Type type) throws JsonParseException {
        try {
            return GsonHelper.GSON.fromJson(reader, type);
        } catch (JsonSyntaxException ex) {
            throw new JsonParseException("Failed to parse JSON for " + type.getTypeName(), ex);
        }
    }

    public String toJson() {
        return GsonHelper.GSON.toJson(this);
    }

    public void toJson(Appendable writer) {
        GsonHelper.GSON.toJson(this, writer);
    }


}
