package ga.justreddy.wiki.whaleskywars.storage.entities;

import com.google.gson.GsonBuilder;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ga.justreddy.wiki.whaleskywars.model.kits.Kit;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ItemStackAdapter;
import ga.justreddy.wiki.whaleskywars.storage.adapters.ListItemStackAdapter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
@DatabaseTable(tableName = "whaleskywars_kits")
public class KitEntity {

    @DatabaseField(columnName = "name", unique = true)
    public String name;

    @DatabaseField(columnName = "items", dataType = DataType.LONG_STRING)
    public String kitItems;

    @DatabaseField(columnName = "armor", dataType = DataType.LONG_STRING)
    public String kitArmor;

    @DatabaseField(columnName = "default", defaultValue = "false")
    public boolean isDefault;

    @DatabaseField(columnName = "gui_kit_item", dataType = DataType.LONG_STRING)
    public String guiKitItem;

    public KitEntity() {}

    public KitEntity(Kit kit) {
        this.name = kit.getName();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new ListItemStackAdapter());
        builder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
        this.kitItems = builder.create().toJson(kit.getKitItems(), List.class);
        this.kitArmor = builder.create().toJson(kit.getArmorItems(), List.class);
        this.isDefault = kit.isDefault();
        this.guiKitItem = builder.create().toJson(kit.getGuiKitItem(), ItemStack.class);
    }

}
