package ga.justreddy.wiki.whaleskywars.api.model.entity.data;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Represents custom player data in the WhaleSkyWars game.
 * Custom player data is a data object that can be attached to a player.
 * Each custom player data has an id.
 * Custom player data can be used to store additional data for a player.
 * Please make sure to serialize and deserialize the custom player data.
 * So it can be properly saved and loaded.
 * @author JustReddy
 */
public interface ICustomPlayerData extends JsonSerializer<ICustomPlayerData>, JsonDeserializer<ICustomPlayerData>, Addon {

    /**
     * Returns the id of the custom player data.
     * @return the id of the custom player data
     */
    String getId();

}
