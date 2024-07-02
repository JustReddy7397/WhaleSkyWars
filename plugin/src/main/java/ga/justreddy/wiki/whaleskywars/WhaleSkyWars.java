package ga.justreddy.wiki.whaleskywars;

import com.alessiodp.libby.Library;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import ga.justreddy.wiki.whaleskywars.manager.LibraryManager;
import ga.justreddy.wiki.whaleskywars.manager.WorldManager;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import ga.justreddy.wiki.whaleskywars.version.worldedit.ISchematic;
import ga.justreddy.wiki.whaleskywars.version.worldedit.IWorldEdit;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@Getter
public final class WhaleSkyWars extends JavaPlugin {

    @Getter
    private static WhaleSkyWars instance;

    // Version specific
    private INms nms;
    private IWorldEdit worldEdit;
    private ISchematic schematic;

    private SlimePlugin slimePlugin;
    private SlimeLoader slimeLoader;

    // Managers
    private WorldManager worldManager;

    // Configs
    private TomlConfig settingsConfig;
    private TomlConfig databaseConfig;

    @Override
    public void onLoad() {
        instance = this;
        LibraryManager libraryManager = new LibraryManager();
        libraryManager.loadDependencies();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!loadConfigs()) return;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean loadConfigs() {
        String loading = "N/A";
        try {
            loading = "settings.toml";
            settingsConfig = new TomlConfig("settings.toml");
            loading = "database.toml";
            databaseConfig = new TomlConfig("database.toml");
        }catch (IOException e) {
            TextUtil.error(e, "Failed to load config " + loading, true);
            return false;
        }
        return true;
    }

}
