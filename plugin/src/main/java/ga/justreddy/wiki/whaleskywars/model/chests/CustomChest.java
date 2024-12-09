package ga.justreddy.wiki.whaleskywars.model.chests;

import ga.justreddy.wiki.whaleskywars.api.model.chest.ILootItem;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.ChestType;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author JustReddy
 */
@Getter
public class CustomChest {

    private final String name;
    private final List<ILootItem> basic;
    private final List<ILootItem> normal;
    private final List<ILootItem> insane;
    private final List<ILootItem> refillBasic;
    private final List<ILootItem> refillNormal;
    private final List<ILootItem> refillInsane;
    private final int minamount;
    private final int maxamount;

    public CustomChest(String name, TempConfig configuration) {
        this.name = name;
        this.basic = new ArrayList<>();
        this.normal = new ArrayList<>();
        this.insane = new ArrayList<>();
        this.refillBasic = new ArrayList<>();
        this.refillNormal = new ArrayList<>();
        this.refillInsane = new ArrayList<>();
        this.minamount = configuration.getInteger("min-items");
        this.maxamount = configuration.getInteger("max-items");
        ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection basic = configuration.getSection("basic");
        for (String key : basic.keys()) {
            ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection section
                    = basic.getSection(key);
            this.basic.add(new LootItem(section));
        }

        ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection normal = configuration.getSection("normal");
        for (String key : normal.keys()) {
            ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection section = normal.getSection(key);
            this.normal.add(new LootItem(section));
        }

        ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection insane = configuration.getSection("insane");
        for (String key : insane.keys()) {
            ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection section = insane.getSection(key);
            this.insane.add(new LootItem(section));
        }

        ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection refillBasic = configuration.getSection("refill_basic");
        for (String key : refillBasic.keys()) {
            ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection section = refillBasic.getSection(key);
            this.refillBasic.add(new LootItem(section));
        }

        ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection refillNormal = configuration.getSection("refill_normal");
        for (String key : refillNormal.keys()) {
            ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection section = refillNormal.getSection(key);
            this.refillNormal.add(new LootItem(section));
        }

        ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection refillInsane = configuration.getSection("refill_insane");
        for (String key : refillInsane.keys()) {
            ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection section = refillInsane.getSection(key);
            this.refillInsane.add(new LootItem(section));
        }

    }


    public CustomChest(String name, FileConfiguration config) {
        this.name = name;
        this.basic = new ArrayList<>();
        this.normal = new ArrayList<>();
        this.insane = new ArrayList<>();
        this.refillBasic = new ArrayList<>();
        this.refillNormal = new ArrayList<>();
        this.refillInsane = new ArrayList<>();
        this.minamount = config.getInt("min-items");
        this.maxamount = config.getInt("max-items");
        ConfigurationSection basic = config.getConfigurationSection("basic");
        for (String key : basic.getKeys(false)) {
            ConfigurationSection section = basic.getConfigurationSection(key);
            this.basic.add(new LootItem(section));
        }

        ConfigurationSection normal = config.getConfigurationSection("normal");
        for (String key : normal.getKeys(false)) {
            ConfigurationSection section = normal.getConfigurationSection(key);
            this.normal.add(new LootItem(section));
        }

        ConfigurationSection insane = config.getConfigurationSection("insane");
        for (String key : insane.getKeys(false)) {
            ConfigurationSection section = insane.getConfigurationSection(key);
            this.insane.add(new LootItem(section));
        }

        ConfigurationSection refillBasic = config.getConfigurationSection("refill_basic");
        for (String key : refillBasic.getKeys(false)) {
            ConfigurationSection section = refillBasic.getConfigurationSection(key);
            this.refillBasic.add(new LootItem(section));
        }

        ConfigurationSection refillNormal = config.getConfigurationSection("refill_normal");
        for (String key : refillNormal.getKeys(false)) {
            ConfigurationSection section = refillNormal.getConfigurationSection(key);
            this.refillNormal.add(new LootItem(section));
        }

        ConfigurationSection refillInsane = config.getConfigurationSection("refill_insane");
        for (String key : refillInsane.getKeys(false)) {
            ConfigurationSection section = refillInsane.getConfigurationSection(key);
            this.refillInsane.add(new LootItem(section));
        }
    }

    public void populateChest(Inventory inventory, ChestType chestType, ChestType defaultChestType){
        inventory.clear();
        List<ILootItem> refill = null;
        switch (defaultChestType) {
            case BASIC:
                refill = refillBasic;
                break;
            case NORMAL:
                refill = refillNormal;
                break;
            case INSANE:
            case REFILL:
                refill = refillInsane;
                break;
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (chestType == ChestType.BASIC) {
            fillRandomizer(inventory, random, basic);
        } else if (chestType == ChestType.NORMAL) {
            fillRandomizer(inventory, random, normal);
        } else if (chestType == ChestType.INSANE) {
            fillRandomizer(inventory, random, insane);
        }else if (chestType == ChestType.REFILL) {
            fillRandomizer(inventory, random, refill);
        }
    }

    private void fillRandomizer(Inventory inventory, ThreadLocalRandom random, List<ILootItem> refill) {
        System.out.println("Size: " + refill.size());
        Set<ILootItem> used = new HashSet<>();

        int amount = random.nextInt(minamount, maxamount) + maxamount;
        int placed = 0;

        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            if (placed == amount) break;
            ILootItem item = refill.get(random.nextInt(refill.size()));
            if (used.contains(item)) continue;
            used.add(item);
/*
            if (item.shouldFill(random)) {
*/
                ItemStack itemStack = item.getItemStack();
                inventory.setItem(slotIndex, itemStack);
                ++placed;
            /*}*/
        }

    }

}
