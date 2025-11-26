package ga.justreddy.wiki.whaleskywars.model.config;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;

import java.io.File;

/**
 * @author JustReddy
 */
public class TomlConfig extends SpigotTomlConfiguration {

    private static final String VERSION_KEY = "config-version";

    public TomlConfig(String fileName, int currentVersion) {
        super(WhaleSkyWars.getInstance().getDataFolder(), fileName.endsWith(".toml") ? fileName : fileName + ".toml");
        reload();
        isOutdated(currentVersion);
    }

    public TomlConfig(File folder, String fileName) {
        super(new File(WhaleSkyWars.getInstance().getDataFolder(), folder.getName()),fileName.endsWith(".toml") ? fileName : fileName + ".toml");
        reload();
    }


    /**
     * Checks if the config version integer equals or is less than the current version.
     *
     * @param currentVersion The expected value of the config version
     * @return True if the config is outdated, false otherwise
     */
    public boolean isOutdated(final int currentVersion) {
        if (getInteger(VERSION_KEY, -1) < currentVersion) {
            TextUtil.error(null, "Configuration file " + file.getName()  + " is outdated! Shutting down plugin.", true);
            Bukkit.getPluginManager().disablePlugin(WhaleSkyWars.getInstance());
            return true;
        }
        return false;
    }

}
