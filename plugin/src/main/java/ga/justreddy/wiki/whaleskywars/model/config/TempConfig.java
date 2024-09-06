package ga.justreddy.wiki.whaleskywars.model.config;

import ga.justreddy.wiki.whaleskywars.model.config.toml.TomlConfiguration;

import java.io.File;

/**
 * @author JustReddy
 */
public class TempConfig extends TomlConfiguration {
    public TempConfig(File folder, String path) {
        super(folder, path);
    }
}
