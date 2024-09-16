package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.model.hotbar.HotBarItem;
import ga.justreddy.wiki.whaleskywars.model.hotbar.HotBarType;
import ga.justreddy.wiki.whaleskywars.model.hotbar.items.CustomItem;
import ga.justreddy.wiki.whaleskywars.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author JustReddy
 */
public class HotBarManager {

    private List<HotBarItem> items;

    public HotBarManager() {
        this.items = new ArrayList<>();

        TomlConfig config = WhaleSkyWars.getInstance().getHotbarConfig();

        ConfigurationSection items = config.getSection("items");

        for (String key : items.keys()) {
            ConfigurationSection section = items.getSection(key);
            ItemStack itemStack = ItemStackBuilder.getItemStack(section, null)
                    .build();
            CustomItem item = new CustomItem(this, section, itemStack, section.getInteger("slot"), key, HotBarType.valueOf(section.getString("type")));
            this.items.add(item);
        }

    }

    public void giveItems() {
        WhaleSkyWars.getInstance().getSpawn()
                .getWorld()
                .getPlayers()
                .forEach(player -> {
                    items.forEach(item -> item.giveItem(player));
                });
    }

    public void removeItems() {
        WhaleSkyWars.getInstance().getSpawn()
                .getWorld()
                .getPlayers()
                .forEach(player -> {
                    items.forEach(item -> item.removeItem(player));
                });
    }

}
