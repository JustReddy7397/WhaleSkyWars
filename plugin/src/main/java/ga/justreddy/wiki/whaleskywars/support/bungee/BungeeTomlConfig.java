package ga.justreddy.wiki.whaleskywars.support.bungee;

import com.moandjiezana.toml.Toml;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author JustReddy
 */
public class BungeeTomlConfig {

    private static final String VERSION_KEY = "config-version";

    private final String name;

    private File file;

    private Toml toml;

    public BungeeTomlConfig(final String name) throws IOException {

        if (!Bungee.getInstance().getDataFolder().exists()) {
            Bungee.getInstance().getDataFolder().mkdirs();
        }

        final String completeName = name.endsWith(".toml") ? name : name + ".toml";
        this.name = completeName;
        final File configFile = new File(WhaleSkyWars.getInstance().getDataFolder().getAbsolutePath(), completeName);
        if (!configFile.exists()) {
            Files.copy(Bungee.getInstance()
                    .getResourceAsStream(completeName), configFile.toPath()); // Copy the file out of our jar into our plugins Data Folder
        }

        file = configFile;
        toml = new Toml(
                new Toml().read(Bungee.getInstance().getResourceAsStream(completeName))
        ).read(file);

    }

    public void reload() {
        toml = new Toml()
                .read(file);
    }

    public void save() {
        toml = new Toml()
                .read(file);
    }

    /**
     * Checks if the config version integer equals or is less than the current version.
     *
     * @param currentVersion The expected value of the config version
     * @return True if the config is outdated, false otherwise
     */
    public boolean isOutdated(final int currentVersion) {
        if (getInteger(VERSION_KEY, -1) < currentVersion) {
            throw new IllegalStateException(TextUtil.color("&cThe config file " + name + " is outdated! Please delete it and restart the server!"));
        }
        return false;
    }

    public int getInteger(String key) {
        return toml.getLong(key).intValue();
    }

    public int getInteger(String key, int defaultValue) {
        return toml.getLong(key, (long) defaultValue).intValue();
    }

    public String getString(String key) {
        return toml.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return toml.getString(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return toml.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return toml.getBoolean(key, defaultValue);
    }

    public double getDouble(String key) {
        return toml.getDouble(key);
    }

    public double getDouble(String key, double defaultValue) {
        return toml.getDouble(key, defaultValue);
    }

    public long getLong(String key) {
        return toml.getLong(key);
    }

    public long getLong(String key, long defaultValue) {
        return toml.getLong(key, defaultValue);
    }

    public List<String> getStringList(String key) {
        return toml.getList(key);
    }

    public List<String> getStringList(String key, List<String> defaultValue) {
        return toml.getList(key, defaultValue);
    }

    public List<Integer> getIntegerList(String key) {
        return toml.getList(key);
    }

    public List<Integer> getIntegerList(String key, List<Integer> defaultValue) {
        return toml.getList(key, defaultValue);
    }

    public Toml getTable(String key) {
        return toml.getTable(key);
    }

}
