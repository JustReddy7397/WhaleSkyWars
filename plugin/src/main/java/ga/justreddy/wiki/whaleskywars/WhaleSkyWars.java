package ga.justreddy.wiki.whaleskywars;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import ga.justreddy.wiki.whaleskywars.api.ApiProvider;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import ga.justreddy.wiki.whaleskywars.api.model.game.map.IGameMap;
import ga.justreddy.wiki.whaleskywars.commands.BaseCommand;
import ga.justreddy.wiki.whaleskywars.listeners.GameListener;
import ga.justreddy.wiki.whaleskywars.listeners.MainListener;
import ga.justreddy.wiki.whaleskywars.manager.*;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.board.SkyWarsBoard;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.creator.CageCreator;
import ga.justreddy.wiki.whaleskywars.model.creator.GameCreator;
import ga.justreddy.wiki.whaleskywars.model.game.map.BukkitGameMap;
import ga.justreddy.wiki.whaleskywars.model.game.map.SlimeGameMap;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;
import ga.justreddy.wiki.whaleskywars.storage.flatfile.FlatStorage;
import ga.justreddy.wiki.whaleskywars.storage.remote.MongoStorage;
import ga.justreddy.wiki.whaleskywars.storage.remote.SequalStorage;
import ga.justreddy.wiki.whaleskywars.tasks.SyncTask;
import ga.justreddy.wiki.whaleskywars.util.LocationUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import ga.justreddy.wiki.whaleskywars.version.worldedit.ISchematic;
import ga.justreddy.wiki.whaleskywars.version.worldedit.IWorldEdit;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    private VictoryDanceManager victoryDanceManager;
    private PlayerManager playerManager;
    private KitRequestManager kitRequestManager;
    private KitManager kitManager;
    private BalloonManager balloonManager;
    private HookManager hookManager;
    // Configs
    private TomlConfig settingsConfig;
    private TomlConfig databaseConfig;
    private TomlConfig scoreboardConfig;
    private TomlConfig defaultConfig;
    private TomlConfig balloonsConfig;
    // Creators
    private CageCreator cageCreator;
    private GameCreator gameCreator;
    // Bungee
    private ServerMode serverMode;

    private IStorage storage;

    private Location spawn;

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
            TextUtil.color("&7[&dWhaleSkyWars&7] &aServer mode set to " + serverMode.name());
        } catch (Exception e) {
            TextUtil.error(e, "Failed to set server mode! " + settingsConfig.getString("modules.mode") + " not supported!", true);
            return;
        }

        TextUtil.color("&7[&dWhaleSkyWars&7] &aLoading storage...");
        switch (databaseConfig.getString("storage.type").toUpperCase()) {
            case "SQLITE":
                storage = new FlatStorage();
                break;
                case "MYSQL":
                    storage = new SequalStorage(
                            "mysql",
                            databaseConfig.getString("storage.mysql.host"),
                            databaseConfig.getString("storage.mysql.database"),
                            databaseConfig.getString("storage.mysql.username"),
                            databaseConfig.getString("storage.mysql.password"),
                            databaseConfig.getInteger("storage.mysql.port")
                    );
                    break;
            case "MONGODB":
                storage = new MongoStorage(databaseConfig.getString("storage.mongodb.uri"));
                break;
            default:
                TextUtil.error(null, "Invalid storage type: " + databaseConfig.getString("storage.type"), true);
                return;
        }
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aStorage loaded ("+databaseConfig.getString("storage.type").toUpperCase()+")!");
        storage.createData();

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
        Bukkit.getServer().getPluginManager().registerEvents(new GameListener(), this);
        getCommand("whaleskywars").setExecutor(new BaseCommand());

        Bukkit.getScheduler().runTaskTimer(this, new SyncTask(gameManager), 0, 20);

        if (defaultConfig.getString("spawn") != null && !defaultConfig.getString("spawn").equalsIgnoreCase("null")) {
            spawn = LocationUtil.getLocation(defaultConfig.getString("spawn"));
        }

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aWhaleSkyWars v" + getDescription().getVersion() + " by JustReddy loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        stopManagers();
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
            loading = "config.toml";
            defaultConfig = new TomlConfig("config.toml");
            loading = "balloons.toml";
            balloonsConfig = new TomlConfig("balloons.toml");
        } catch (Exception e) {
            TextUtil.error(e, "Failed to load config " + loading, true);
            return false;
        }
        return true;
    }

    private void loadCreators() {
        cageCreator = new CageCreator();
        gameCreator = new GameCreator();
    }

    private void loadManagers() {
        worldManager = new WorldManager();
        gameEventManager = new GameEventManager();
        playerManager = new PlayerManager();
        gameManager = new GameManager();
        cageManager = new CageManager();
        kitRequestManager = new KitRequestManager();
        kitManager = new KitManager();
        victoryDanceManager = new VictoryDanceManager();
        this.balloonManager = new BalloonManager();
        gameEventManager.start();
        gameManager.start();
        cageManager.start();
        kitManager.loadKits();
        victoryDanceManager.start();
        balloonManager.start();
        this.hookManager = new HookManager();

    }

    private void stopManagers() {
        gameManager.die();
        cageManager.die();
        playerManager.die();
        gameEventManager.die();
        victoryDanceManager.die();
    }


    private static String getVersion(Server server) {
        final String packageName = server.getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
        defaultConfig.set("spawn", LocationUtil.toLocation(spawn));
        defaultConfig.save();
    }

    public boolean isHooked(String hookId) {
        return hookManager.isHooked(hookId);
    }

}
