package ga.justreddy.wiki.whaleskywars.util;

import com.google.gson.GsonBuilder;
import ga.justreddy.wiki.whaleskywars.storage.adapters.Adapter;

/**
 * @author JustReddy
 */
public class GsonUtil {

    public static String toJson(Object object, Class<?> clazz, Adapter<?> adapter) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(clazz, adapter);
        return builder.create().toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz, Adapter<T> adapter) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(clazz, adapter);
        return builder.create().fromJson(json, clazz);
    }

    public static String toJson(Object object, Class<?> clazz) {
        return new GsonBuilder().create().toJson(object, clazz);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return new GsonBuilder().create().fromJson(json, clazz);
    }


}
