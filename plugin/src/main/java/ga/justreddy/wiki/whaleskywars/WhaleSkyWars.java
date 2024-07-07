package ga.justreddy.wiki.whaleskywars;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import ga.justreddy.wiki.whaleskywars.api.ApiProvider;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import ga.justreddy.wiki.whaleskywars.api.model.game.map.IGameMap;
import ga.justreddy.wiki.whaleskywars.listeners.MainListener;
import ga.justreddy.wiki.whaleskywars.manager.*;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.board.SkyWarsBoard;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.creator.CageCreator;
import ga.justreddy.wiki.whaleskywars.model.game.map.BukkitGameMap;
import ga.justreddy.wiki.whaleskywars.model.game.map.SlimeGameMap;
import ga.justreddy.wiki.whaleskywars.tasks.SyncTask;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import ga.justreddy.wiki.whaleskywars.version.worldedit.ISchematic;
import ga.justreddy.wiki.whaleskywars.version.worldedit.IWorldEdit;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@Getter
public final class WhaleSkyWars extends JavaPlugin {


    // Config Version
    private static final int SETTINGS_VERSION = 1;
    private static final int DATABASE_VERSION = 1;
    private static final int SCOREBOARD_VERSION = 1;
    private static final String VERSION = getVersion(Bukkit.getServer());
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
    private SkyWarsBoard skyWarsBoard;
    // Managers
    private WorldManager worldManager;
    private GameManager gameManager;
    private CageManager cageManager;
    private GameEventManager gameEventManager;
    private PlayerManager playerManager;
    // Configs
    private TomlConfig settingsConfig;
    private TomlConfig databaseConfig;
    private TomlConfig scoreboardConfig;
    // Creators
    private CageCreator cageCreator;
    // Bungee
    private ServerMode serverMode;

    private static String getVersion(Server server) {
        final String packageName = server.getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

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
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoading WhaleSkyWars v" + getDescription().getVersion() + " by JustReddy");
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aFinding NMS version...");
        try {
            nms = (INms) Class.forName("ga.justreddy.wiki.whaleskywars.nms." + VERSION + "." + VERSION).newInstance();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aNMS version found: " + VERSION);
        } catch (Exception e) {
            TextUtil.error(e, "Failed to find NMS version: " + VERSION + ", not supported!", true);
            return;
        }

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoading schematic handler...");
        try {
            schematic = (ISchematic)
                    Class.forName("ga.justreddy.wiki.whaleskywars.nms."
                            + VERSION + "."
                            + "Schematic").newInstance();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aSchematic version found: " + VERSION);
        } catch (Exception e) {
            TextUtil.error(e, "Schematic version: " + VERSION + " is not supported!", true);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aFinding WorldEdit version...");
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") == null) {
            TextUtil.error(null, "WorldEdit is not installed, disabling plugin!", true);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        try {
            worldEdit = (IWorldEdit) Class.forName("ga.justreddy.wiki.whaleskywars.nms." + VERSION + ".WorldEdit").newInstance();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aWorldEdit version found: " + VERSION);
        } catch (Exception e) {
            TextUtil.error(e, "Failed to find WorldEdit version: " + VERSION + ", not supported!", true);
            return;
        }

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aFinding API...");
        SkyWarsProvider.setSkyWarsAPI(new ApiProvider());
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aAPI found!");

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aSetting server mode...");
        try {
            serverMode = ServerMode.valueOf(settingsConfig.getString("modules.mode").toUpperCase());
        } catch (Exception e) {
            TextUtil.error(e, "Failed to set server mode! " + settingsConfig.getString("modules.mode") + " not supported!", true);
            return;
        }

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aFinding GameMap...");
        if (settingsConfig.getBoolean("modules.slimeworldmanager")) {
            slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
            if (slimePlugin == null) {
                TextUtil.error(null, "SlimeWorldManager is not installed, disabling plugin!", true);
                return;
            }
            slimeLoader = slimePlugin.getLoader("file");
            gameMap = new SlimeGameMap();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aGameMap found: SlimeGameMap");
        } else {
            gameMap = new BukkitGameMap();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aGameMap found: BukkitGameMap");
        }

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoading managers...");
        loadManagers();
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aManagers loaded!");

        skyWarsBoard = new SkyWarsBoard();
        loadCreators();

        // TODO load db etc

        Bukkit.getServer().getPluginManager().registerEvents(new MainListener(), this);

        Bukkit.getScheduler().runTaskTimer(this, new SyncTask(), 0, 20);

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aWhaleSkyWars v" + getDescription().getVersion() + " by JustReddy loaded!");

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
            if (settingsConfig.isOutdated(SETTINGS_VERSION)) {
                TextUtil.error(null, "Settings config is outdated! Please update!", true);
                return false;
            }
            loading = "database.toml";
            databaseConfig = new TomlConfig("database.toml");
            if (databaseConfig.isOutdated(DATABASE_VERSION)) {
                TextUtil.error(null, "Database config is outdated! Please update!", true);
                return false;
            }
            loading = "scoreboards.toml";
            scoreboardConfig = new TomlConfig("scoreboards.toml");
            if (scoreboardConfig.isOutdated(SCOREBOARD_VERSION)) {
                TextUtil.error(null, "Scoreboard config is outdated! Please update!", true);
                return false;
            }
        } catch (IOException e) {
            TextUtil.error(e, "Failed to load config " + loading, true);
            return false;
        }
        return true;
    }

    private void loadCreators() {
        cageCreator = new CageCreator();
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
