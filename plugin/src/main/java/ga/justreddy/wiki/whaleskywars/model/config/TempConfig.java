package ga.justreddy.wiki.whaleskywars.model.config;

import java.io.File;

/**
 * @author JustReddy
 */
public class TempConfig extends SpigotTomlConfiguration {
    public TempConfig(File folder, String path) {
        super(folder, path);
        reload();
    }
}
