package ga.justreddy.wiki.whaleskywars.model.chests;

import ga.justreddy.wiki.whaleskywars.api.model.chest.AChestType;
import ga.justreddy.wiki.whaleskywars.api.model.chest.ILootItem;
import ga.justreddy.wiki.whaleskywars.model.config.CustomTomlReader;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JustReddy
 */
public class CustomChest extends AChestType {

    private final String identifier;
    private final int minAmount;
    private final int maxAmount;

    public CustomChest(String identifier, CustomTomlReader section, int minAmount, int maxAmount) {
        this.identifier = identifier;
        for (String key : section.keys()) {
            addChestType(key);
            CustomTomlReader reader = section.getTable(key);
            for (String lootItemKey : reader.keys()) {
                CustomTomlReader lootItem = reader.getTable(lootItemKey);
                ILootItem item = new LootItem(lootItem);
                getItemsForChestTypes()
                        .computeIfAbsent(key, k ->
                                new ArrayList<>())
                        .add(item);
            }
        }
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public CustomChest(String identifier, FileConfiguration config, int minAmount, int maxAmount) {
        this.identifier = identifier;
        for (String key : config.getKeys(false)) {
            addChestType(key);
            ConfigurationSection reader = config.getConfigurationSection(key);
            for (String lootItemKey : reader.getKeys(false)) {
                ConfigurationSection lootItem = reader.getConfigurationSection(lootItemKey);
                ILootItem item = new LootItem(lootItem);
                getItemsForChestTypes()
                        .computeIfAbsent(key, k ->
                                new ArrayList<>())
                        .add(item);
            }
        }
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int getMinAmount() {
        return minAmount;
    }

    @Override
    public int getMaxAmount() {
        return maxAmount;
    }
}
