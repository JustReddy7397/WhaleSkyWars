package ga.justreddy.wiki.whaleskywars.model.entity.data;

import com.google.gson.*;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;

import java.lang.reflect.Type;

/**
 * @author JustReddy
 */
public class CustomPlayerDataExample implements ICustomPlayerData {

    private String sexy;

    // Recommended to have a constructor with no parameters
    // But optional, of course :)
    public CustomPlayerDataExample() {
        this.sexy = "sexy";
    }

    @Override
    public String getId() {
        return "custom_player_data_example";
    }

    public String getSexy() {
        return sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }

    @Override
    public ICustomPlayerData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        CustomPlayerDataExample customPlayerDataExample = new CustomPlayerDataExample();
        // Getting the value of the key "sexy" from the json object
        customPlayerDataExample.setSexy(object.get("sexy").getAsString());
        return customPlayerDataExample;
    }

    @Override
    public JsonElement serialize(ICustomPlayerData playerData, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        // Adding the key "sexy" with the value of the "sexy" field
        object.addProperty("sexy", sexy);
        return object;
    }
}
