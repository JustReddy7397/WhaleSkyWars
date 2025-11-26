package ga.justreddy.wiki.whaleskywars.bungeecord.config;

import ga.justreddy.wiki.whaleskywars.bungeecord.Bungeecord;

import java.io.File;

/**
 * @author JustReddy
 */
public class TomlConfig extends BungeeTomlConfiguration {

    public TomlConfig(String fileName) {
        super(Bungeecord.getInstance().getDataFolder(), fileName.endsWith(".toml") ? fileName : fileName + ".toml");
        reload();
    }

    public TomlConfig(File folder, String fileName) {
        super(new File(Bungeecord.getInstance().getDataFolder(), folder.getName()),fileName.endsWith(".toml") ? fileName : fileName + ".toml");
        reload();
    }
}
