package ga.justreddy.wiki.whaleskywars.model.entity.data;

import com.google.gson.*;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.ICustomPlayerData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class PlayerRanked implements ICustomPlayerData {

    private int elo;

    public PlayerRanked() {
        this.elo = 0;
    }

    @Override
    public String getId() {
        return "ranked";
    }

    @Override
    public ICustomPlayerData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        PlayerRanked playerRanked = new PlayerRanked();
        playerRanked.elo = object.get("elo").getAsInt();
        return playerRanked;
    }

    @Override
    public JsonElement serialize(ICustomPlayerData playerData, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("elo", elo);
        return object;
    }
    
    public int getElo() {
        return elo;
    }

    public int getPosition() {
        return 1;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
