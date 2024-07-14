package ga.justreddy.wiki.whaleskywars.storage.adapters;

import com.google.gson.*;
import ga.justreddy.wiki.whaleskywars.util.EncodeUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

/**
 * @author JustReddy
 */
public class ItemStackAdapter implements Adapter<ItemStack>{
    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        return EncodeUtil.decode(object.get("stack").getAsString());
    }

    @Override
    public JsonElement serialize(ItemStack stack, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("stack", EncodeUtil.encode(stack));
        return object;
    }
}
