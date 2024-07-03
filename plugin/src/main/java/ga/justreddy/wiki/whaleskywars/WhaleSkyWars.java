package ga.justreddy.wiki.whaleskywars;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import ga.justreddy.wiki.whaleskywars.api.model.game.map.IGameMap;
import ga.justreddy.wiki.whaleskywars.manager.*;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
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

    // Misc
    private SlimePlugin slimePlugin;
    private SlimeLoader slimeLoader;
    private IGameMap gameMap;

    // Managers
    private WorldManager worldManager;
    private GameManager gameManager;
    private CageManager cageManager;
    private GameEventManager gameEventManager;
    private PlayerManager playerManager;

    // Configs
    private TomlConfig settingsConfig;
    private TomlConfig databaseConfig;

    // Bungee
    private ServerMode serverMode;

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

        serverMode = ServerMode.valueOf(settingsConfig.getString("modules.mode", "MULTI_ARENA"));

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

    private void loadManagers() {
        worldManager = new WorldManager();
        gameEventManager = new GameEventManager();
        playerManager = new PlayerManager();
        gameManager = new GameManager();
        cageManager = new CageManager();
        gameManager.start();
        cageManager.start();
    }

    private void stopManagers() {
        gameManager.die();
        cageManager.die();
        playerManager.die();
        gameEventManager.die();
    }

}
