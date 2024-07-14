package ga.justreddy.wiki.whaleskywars.storage.adapters;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * @author JustReddy
 */
public interface Adapter<T> extends JsonDeserializer<T>, JsonSerializer<T> {}
