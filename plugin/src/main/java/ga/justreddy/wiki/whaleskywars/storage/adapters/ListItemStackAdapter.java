package ga.justreddy.wiki.whaleskywars.storage.adapters;

import com.google.gson.*;
import ga.justreddy.wiki.whaleskywars.util.EncodeUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class ListItemStackAdapter implements Adapter<List<ItemStack>> {

    @Override
    public List<ItemStack> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<ItemStack> itemStacks = new ArrayList<>();
        JsonObject object = jsonElement.getAsJsonObject();
        List<String> serialisedItemStacks = jsonDeserializationContext.deserialize(object.get("itemStacks"), List.class);
        for (String serialisedItemStack : serialisedItemStacks) {
            itemStacks.add(EncodeUtil.decode(serialisedItemStack));
        }
        return itemStacks;
    }

    @Override
    public JsonElement serialize(List<ItemStack> itemStackList, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject object = new JsonObject();
        List<String> serialisedItemStacks = new ArrayList<>();
        for (ItemStack itemStack : itemStackList) {
            serialisedItemStacks.add(EncodeUtil.encode(itemStack));
        }

        object.add("itemStacks", jsonSerializationContext.serialize(serialisedItemStacks));
        return object;
    }
}
