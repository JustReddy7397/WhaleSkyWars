package ga.justreddy.wiki.whaleskywars;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import ga.justreddy.wiki.whaleskywars.api.ApiProvider;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import ga.justreddy.wiki.whaleskywars.api.model.game.map.IGameMap;
import ga.justreddy.wiki.whaleskywars.commands.BaseCommand;
import ga.justreddy.wiki.whaleskywars.listeners.GameListener;
import ga.justreddy.wiki.whaleskywars.listeners.LobbyListener;
import ga.justreddy.wiki.whaleskywars.listeners.MainListener;
import ga.justreddy.wiki.whaleskywars.manager.*;
import ga.justreddy.wiki.whaleskywars.manager.cache.CacheManager;
import ga.justreddy.wiki.whaleskywars.manager.cosmetic.*;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.board.SkyWarsBoard;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.creator.CageCreator;
import ga.justreddy.wiki.whaleskywars.model.creator.GameCreator;
import ga.justreddy.wiki.whaleskywars.model.entity.data.CustomPlayerDataExample;
import ga.justreddy.wiki.whaleskywars.model.entity.data.PlayerRanked;
import ga.justreddy.wiki.whaleskywars.model.game.map.BukkitGameMap;
import ga.justreddy.wiki.whaleskywars.model.game.map.SlimeGameMap;
import ga.justreddy.wiki.whaleskywars.storage.IStorage;
import ga.justreddy.wiki.whaleskywars.storage.flatfile.FlatStorage;
import ga.justreddy.wiki.whaleskywars.storage.remote.MongoStorage;
import ga.justreddy.wiki.whaleskywars.storage.remote.SequalStorage;
import ga.justreddy.wiki.whaleskywars.support.IMessenger;
import ga.justreddy.wiki.whaleskywars.tasks.CustomColumnCheck;
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
import redis.clients.jedis.BuilderFactory;

@Getter
public final class WhaleSkyWars extends JavaPlugin {

    // Constants
    private static final int SETTINGS_VERSION = 1;
    private static final int DATABASE_VERSION = 1;
    private static final int SCOREBOARD_VERSION = 1;
    private static final int CONFIG_VERSION = -1;
    private static final int BALLOONS_VERSION = 1;
    private static final int MESSAGES_VERSION = 1;
    private static final int HOTBAR_VERSION = 1;
    private static final String VERSION = getVersion(Bukkit.getServer());

    // Instance
    @Getter
    private static WhaleSkyWars instance;

    // Version-specific
    private INms nms;
    private IWorldEdit worldEdit;
    private ISchematic schematic;

    // Miscellaneous
    private SlimePlugin slimePlugin;
    private SlimeLoader slimeLoader;
    private IGameMap gameMap;
    private SkyWarsBoard skyWarsBoard;

    // Managers
    private ActionManager actionManager;
    private BalloonManager balloonManager;
    private CacheManager cacheManager;
    private CageManager cageManager;
    private ChestManager chestManager;
    private CustomPlayerDataManager customPlayerDataManager;
    private GameEventManager gameEventManager;
    private GameManager gameManager;
    private GameModeManager gameModeManager;
    private HookManager hookManager;
    private HotBarManager hotBarManager;
    private KillEffectManager killEffectManager;
    private KillMessageManager killMessageManager;
    private KitManager kitManager;
    private KitRequestManager kitRequestManager;
    private MenuManager menuManager;
    private PerkManager perkManager;
    private PlayerManager playerManager;
    private SignManager signManager;
    private TrailManager trailManager;
    private VictoryDanceManager victoryDanceManager;
    private WorldManager worldManager;

    // Configurations
    private TomlConfig settingsConfig;
    private TomlConfig databaseConfig;
    private TomlConfig scoreboardConfig;
    private TomlConfig defaultConfig;
    private TomlConfig balloonsConfig;
    private TomlConfig messagesConfig;
    private TomlConfig hotbarConfig;
    private TomlConfig signsConfig;
    private TomlConfig trailsConfig;

    // Creators
    private CageCreator cageCreator;
    private GameCreator gameCreator;

    // Bungee
    private ServerMode serverMode;
    private IMessenger<WhaleSkyWars> messenger;

    // Storage
    private IStorage storage;

    // Spawn location
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
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoading WhaleSkyWars v" + getDescription().getVersion() + " by JustReddy");


        if (!loadConfigs()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        initializeNms();
        initializeSchematicHandler();
        initializeWorldEdit();
        initializeApi();
        initializeServerMode();
        initializeStorage();
        initializeGameMap();

        loadManagers();
        loadCreators();

        setupCommandsAndListeners();
        scheduleTasks();

        if (defaultConfig.getString("spawn") != null && !defaultConfig.getString("spawn").equalsIgnoreCase("null")) {
            spawn = LocationUtil.getLocation(defaultConfig.getString("spawn"));
        }

        customPlayerDataManager.addCustomPlayerData(new CustomPlayerDataExample());
        customPlayerDataManager.addCustomPlayerData(new PlayerRanked());

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new CustomColumnCheck(storage), 20, 20L);

        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aWhaleSkyWars v" + getDescription().getVersion() + " by JustReddy loaded!");
    }

    @Override
    public void onDisable() {
        stopManagers();
        gameCreator.kill();
    }

    // Initialization methods
    private void initializeNms() {
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aFinding NMS version...");
        try {
            nms = (INms) Class.forName("ga.justreddy.wiki.whaleskywars.nms." + VERSION + "." + VERSION).newInstance();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aNMS version found: " + VERSION);
        } catch (Exception e) {
            TextUtil.error(e, "Failed to find NMS version: " + VERSION + ", not supported!", true);
        }
    }

    private void initializeSchematicHandler() {
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoading schematic handler...");
        try {
            schematic = (ISchematic) Class.forName("ga.justreddy.wiki.whaleskywars.nms." + VERSION + ".Schematic").newInstance();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aSchematic version found: " + VERSION);
        } catch (Exception e) {
            TextUtil.error(e, "Schematic version: " + VERSION + " is not supported!", true);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void initializeWorldEdit() {
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aFinding WorldEdit version...");
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") == null) {
            TextUtil.error(null, "WorldEdit is not installed, disabling plugin!", true);
            getServer().getPluginManager().disablePlugin(this);
        } else {
            try {
                worldEdit = (IWorldEdit) Class.forName("ga.justreddy.wiki.whaleskywars.nms." + VERSION + ".WorldEdit").newInstance();
                TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aWorldEdit version found: " + VERSION);
            } catch (Exception e) {
                TextUtil.error(e, "Failed to find WorldEdit version: " + VERSION + ", not supported!", true);
            }
        }
    }

    private void initializeApi() {
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aFinding API...");
        SkyWarsProvider.setSkyWarsAPI(new ApiProvider());
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aAPI found!");
    }

    private void initializeServerMode() {
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aSetting server mode...");
        try {
            serverMode = ServerMode.valueOf(settingsConfig.getString("modules.mode").toUpperCase());
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aServer mode set to " + serverMode.name());
        } catch (Exception e) {
            TextUtil.error(e, "Failed to set server mode! " + settingsConfig.getString("modules.mode") + " not supported!", true);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void initializeStorage() {
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoading storage...");
        try {
            String storageType = databaseConfig.getString("storage.type");
            switch (storageType.toLowerCase()) {
                case "mongo":
                    storage = new MongoStorage(
                            databaseConfig.getString("storage.mongodb.uri")
                    );
                    break;
                case "mysql":
                    storage = new SequalStorage(
                            "mysql",
                            databaseConfig.getString("storage.mysql.host"),
                            databaseConfig.getString("storage.mysql.database"),
                            databaseConfig.getString("storage.mysql.username"),
                            databaseConfig.getString("storage.mysql.password"),
                            databaseConfig.getInteger("storage.mysql.port")
                    );
                    break;
                default:
                    storage = new FlatStorage();
                    break;
            }
            storage.createData();
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aStorage loaded!");
        } catch (Exception e) {
            TextUtil.error(e, "Failed to load storage!", true);
        }
    }

    private void initializeGameMap() {
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aSetting up game map...");
        gameMap = settingsConfig.getBoolean("modules.slimeworldmanager")
                && WhaleSkyWars.getInstance().isHooked("SlimeWorldManager") ?
                new SlimeGameMap() : new BukkitGameMap();
        if (gameMap instanceof SlimeGameMap) {
            slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
            slimeLoader = slimePlugin.getLoader("file");
        }
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aGame map set!");
    }

    // Managers and creators
    private void loadManagers() {
        skyWarsBoard = new SkyWarsBoard();
        actionManager = new ActionManager();
        balloonManager = new BalloonManager();
        cacheManager = new CacheManager();
        cageManager = new CageManager();
        customPlayerDataManager = new CustomPlayerDataManager();
        gameEventManager = new GameEventManager();
        gameManager = new GameManager();
        gameModeManager = new GameModeManager();
        hookManager = new HookManager();
        hotBarManager = new HotBarManager();
        killEffectManager = new KillEffectManager();
        killMessageManager = new KillMessageManager();
        kitManager = new KitManager();
        kitRequestManager = new KitRequestManager();
        menuManager = new MenuManager();
        perkManager = new PerkManager();
        playerManager = new PlayerManager();
        victoryDanceManager = new VictoryDanceManager();
        worldManager = new WorldManager();
        signManager = new SignManager();
        chestManager = new ChestManager();
        trailManager = new TrailManager();
        cageManager.start();
        victoryDanceManager.start();
        perkManager.start();
        gameEventManager.start();
        gameModeManager.start();
        gameManager.start();
        kitManager.loadKits();
        hookManager.hookAll();
        menuManager.start();
        balloonManager.start();
        signManager.start();
        killEffectManager.start();
        killMessageManager.start();
        chestManager.start();
        trailManager.start();
    }

    private void loadCreators() {
        cageCreator = new CageCreator();
        gameCreator = new GameCreator();
    }

    private void setupCommandsAndListeners() {
        getCommand("whaleskywars").setExecutor(new BaseCommand());
        Bukkit.getPluginManager().registerEvents(new LobbyListener(cacheManager), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
    }

    private void scheduleTasks() {
        Bukkit.getScheduler().runTaskTimer(this, new SyncTask(gameManager,  signManager), 0, 20L);
    }

    private boolean loadConfigs() {
        String current = "=";
        try {
            settingsConfig = new TomlConfig(current = "settings", SETTINGS_VERSION);
            databaseConfig = new TomlConfig(current = "database", DATABASE_VERSION);
            scoreboardConfig = new TomlConfig(current = "scoreboards", SCOREBOARD_VERSION);
            defaultConfig = new TomlConfig(current = "config", CONFIG_VERSION);
            balloonsConfig = new TomlConfig(current = "balloons", BALLOONS_VERSION);
            messagesConfig = new TomlConfig(current = "messages", MESSAGES_VERSION);
            hotbarConfig = new TomlConfig(current = "hotbar", HOTBAR_VERSION);
            signsConfig = new TomlConfig(current = "signs", 1);
            trailsConfig = new TomlConfig(current = "trails", 1);
        } catch (Exception e) {
            TextUtil.error(e, "Failed to load the config: " + current, true);
            return false;
        }
        return true;
    }

    private void stopManagers() {
        gameManager.die();
        cageManager.die();
        playerManager.die();
        gameEventManager.die();
        victoryDanceManager.die();
        perkManager.die();
        gameModeManager.die();
        balloonManager.die();
        signManager.die();
        trailManager.die();
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aWhaleSkyWars disabled successfully.");
    }

    public boolean isHooked(String hookId) {
        return hookManager.isHooked(hookId);
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
        defaultConfig.set("spawn", LocationUtil.toLocation(spawn));
        defaultConfig.save();
    }

    public void reload() {
        settingsConfig.reload();
        databaseConfig.reload();
        scoreboardConfig.reload();
        defaultConfig.reload();
        balloonsConfig.reload();
        messagesConfig.reload();
        hotbarConfig.reload();
        hotBarManager.reload();
        balloonManager.reload();
    }

    // Version fetch method
    private static String getVersion(Server server) {
        return server.getClass().getPackage().getName().split("\\.")[3];
    }
}
