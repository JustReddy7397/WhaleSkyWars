package ga.justreddy.wiki.whaleskywars.model.chests;

import ga.justreddy.wiki.whaleskywars.api.model.chest.AChestType;
import ga.justreddy.wiki.whaleskywars.api.model.chest.ILootItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

/**
 * @author JustReddy
 */
public class CustomChest extends AChestType {

    private final String identifier;
    private final int minAmount;
    private final int maxAmount;

    public CustomChest(String identifier, ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection section, int minAmount, int maxAmount) {
        this.identifier = identifier;
        for (String key : section.keys()) {
            addChestType(key);
            ga.justreddy.wiki.whaleskywars.model.
                    config.toml.ConfigurationSection items = section.getSection(key);
            for (String lootItemKey : items.keys()) {
                ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection lootItem = items.getSection(lootItemKey);
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
