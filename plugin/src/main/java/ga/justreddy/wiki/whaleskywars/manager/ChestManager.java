package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.chest.AChestType;
import ga.justreddy.wiki.whaleskywars.model.chests.CustomChest;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class ChestManager {

    private final Map<String, AChestType> chests;
    private final File folder;
    private final boolean isUsingToml = WhaleSkyWars.getInstance().getSettingsConfig().getBoolean("modules.toml-for-chests", true);

    public ChestManager() {
        this.chests = new HashMap<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "chests");
        if (!folder.exists()) folder.mkdir();
    }

    public void start() {
        File[] chests = folder.listFiles();
        if (chests == null) return;
        for (File file : chests) {
            String name = file.getName();
            if (name.endsWith(".toml") && isUsingToml) {
                loadTomlChest(file);
            } else if (name.endsWith(".yml") && !isUsingToml) {
                loadYamlChest(file);
            }
        }
    }

    private void loadTomlChest(File file) {
        String name = file.getName().replaceAll(".toml", "");
        TempConfig reader = new TempConfig(folder, name + ".toml");
        CustomChest customChest = new CustomChest(name, reader, reader.getInteger("min-amount"), reader.getInteger("max-amount"));
        this.chests.put(name, customChest);
    }

    private void loadYamlChest(File file) {
        String name = file.getName().replaceAll(".yml", "");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        CustomChest customChest = new CustomChest(name, config, config.getInt("min-amount"), config.getInt("max-amount"));
        this.chests.put(name, customChest);
    }


    public Map<String, AChestType> getChests() {
        return chests;
    }
}
