package ga.justreddy.wiki.whaleskywars.model.config;

import com.google.gson.GsonBuilder;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.config.toml.TomlConfiguration;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;

import java.io.File;

/**
 * @author JustReddy
 */
public class TomlConfig extends TomlConfiguration {

    private static final String VERSION_KEY = "config-version";

    public TomlConfig(String fileName) {
        super(WhaleSkyWars.getInstance().getDataFolder(), fileName);
        reload();
    }


    /**
     * Checks if the config version integer equals or is less than the current version.
     *
     * @param currentVersion The expected value of the config version
     * @return True if the config is outdated, false otherwise
     */
    public boolean isOutdated(final int currentVersion) {
        System.out.println(getInteger(VERSION_KEY, -1));
        if (getInteger(VERSION_KEY, -1) < currentVersion) {
            TextUtil.error(null, "Configuration file " + file.getName()  + " is outdated! Shutting down plugin.", true);
            Bukkit.getPluginManager().disablePlugin(WhaleSkyWars.getInstance());
            return true;
        }
        return false;
    }

}
