package ga.justreddy.wiki.whaleskywars.support.bungee;

import ga.justreddy.wiki.whaleskywars.model.config.toml.TomlConfiguration;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import net.md_5.bungee.api.ProxyServer;

/**
 * @author JustReddy
 */
public class BungeeTomlConfig extends TomlConfiguration {

    private static final String VERSION_KEY = "config-version";

    public BungeeTomlConfig(String fileName) {
        super(Bungee.getInstance().getDataFolder(), fileName);
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
            TextUtil.errorBungee(null, "Configuration file " + file.getName()  + " is outdated! Shutting down plugin.", true);
            ProxyServer.getInstance().stop();
            return true;
        }
        return false;
    }
}
