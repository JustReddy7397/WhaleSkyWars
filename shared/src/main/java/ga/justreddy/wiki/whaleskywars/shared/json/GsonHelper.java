package ga.justreddy.wiki.whaleskywars.shared.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author JustReddy
 */
public class GsonHelper {

    public static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

}
