package ga.justreddy.wiki.whaleskywars.model.game;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameJoinEvent;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameLeaveEvent;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.*;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameSpawn;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ITeamAssigner;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Cage;
import ga.justreddy.wiki.whaleskywars.model.game.modes.SoloGameMode;
import ga.justreddy.wiki.whaleskywars.model.game.modes.TeamGameMode;
import ga.justreddy.wiki.whaleskywars.model.game.phases.WaitingPhase;
import ga.justreddy.wiki.whaleskywars.model.game.team.GameTeam;
import ga.justreddy.wiki.whaleskywars.model.game.team.TeamAssigner;
import ga.justreddy.wiki.whaleskywars.model.game.timers.EndingTimer;
import ga.justreddy.wiki.whaleskywars.model.game.timers.PreGameTimer;
import ga.justreddy.wiki.whaleskywars.model.game.timers.StartingTimer;
import ga.justreddy.wiki.whaleskywars.util.LocationUtil;
import ga.justreddy.wiki.whaleskywars.util.PlayerUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
@Getter
public class Game implements IGame {

    private final String name;
    private final String displayName;
    private final ITeamAssigner assigner = new TeamAssigner();
    private final TempConfig config;
    private GameState state;
    private GameMode mode;
    private final List<IGamePlayer> players;
    private final List<IGameTeam> teams;
    private final List<IGamePlayer> spectators;
    private final Map<UUID, Integer> kills;
    private final List<GameEvent> events;
    private ICuboid waitingCuboid;
    private ICuboid gameCuboid;
    private Location waitingSpawn;
    private Location spectatorSpawn;
    private int minimumPlayers;
    private int maximumPlayers;
    private int teamSize;
    private PhaseHandler phaseHandler;
    private World world;
    private String defaultChestType;

    private BungeeGame bungeeGame;

    private AbstractTimer startingTimer;
    private AbstractTimer endingTimer;
    private AbstractTimer preGameTimer;

    public Game(String name, TempConfig config) {
        this.name = name;
        this.config = config;
        this.displayName = config.getString("settings.displayName", name);
        this.players = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.spectators = new ArrayList<>();
        this.kills = new HashMap<>();
        this.events = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    @Override
    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    @Override
    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public int getPlayerCount() {
        return getPlayers().size();
    }

    @Override
    public List<IGamePlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public List<IGamePlayer> getAlivePlayers() {
        return players.stream().filter(player -> !player.isDead() && !spectators.contains(player)).collect(Collectors.toList());
    }

    @Override
    public List<IGamePlayer> getSpectators() {
        return new ArrayList<>(spectators);
    }

    @Override
    public List<IGameTeam> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public Set<IGameTeam> getRandomTeams() {
        return new HashSet<>(teams);
    }

    @Override
    public List<IGameTeam> getAliveTeams() {
        return teams.stream().filter(team -> !team.getAlivePlayers().isEmpty()).collect(Collectors.toList());
    }

    @Override
    public List<IGameTeam> getSpectatorTeams() {
        return teams.stream().filter(team -> !team.getSpectatorPlayers().isEmpty()).collect(Collectors.toList());
    }

    @Override
    public Map<UUID, Integer> getKills() {
        return kills;
    }

    @Override
    public int getKills(IGamePlayer player) {
        return kills.getOrDefault(player.getUniqueId(), kills.put(player.getUniqueId(), 0));
    }

    @Override
    public Location getWaitingSpawn() {
        return waitingSpawn;
    }

    @Override
    public Location getSpectatorSpawn() {
        return spectatorSpawn;
    }

    @Override
    public ICuboid getWaitingCuboid() {
        return waitingCuboid;
    }

    @Override
    public ICuboid getGameCuboid() {
        return gameCuboid;
    }

    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public void setGameState(GameState gameState) {
        state = gameState;
    }

    @Override
    public boolean isGameState(GameState gameState) {
        return state == gameState;
    }

    @Override
    public GameMode getGameMode() {
        return mode;
    }

    @Override
    public boolean isGameMode(String identifier) {
        return mode.getIdentifier().equalsIgnoreCase(identifier);
    }

    @Override
    public AbstractTimer getStartingTimer() {
        return startingTimer;
    }

    @Override
    public AbstractTimer getPreGameTimer() {
        return preGameTimer;
    }

    @Override
    public AbstractTimer getEndingTimer() {
        return endingTimer;
    }

    @Override
    public GameEvent getCurrentEvent() {
        GameEvent currentEvent = null;
        for (GameEvent event : events) {
            if (event.isEnabled() && event.getTimer() > 0) {
                currentEvent = event;
                break;
            }
        }
        return currentEvent;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void init(World world) {
        this.world = world;

        teamSize = config.getInteger("settings.teamSize");
        this.minimumPlayers = config.getInteger("settings.minimumPlayers");

        String gameMode = config.getString("settings.gameMode", null);

        if (gameMode == null) {
            if (teamSize == 1) {
                mode = new SoloGameMode();
            } else {
                mode = new TeamGameMode();
            }
        } else {
          // TODO
        }


        Location boundHigh;
        Location boundLow;

        if (config.isSet("waiting-location")) {
            waitingSpawn = LocationUtil.getLocation(config.getString("waiting-location"));
            if (waitingSpawn != null) {
                boundHigh = LocationUtil.getLocation(config.getString("waiting-cuboid.high"));
                boundLow = LocationUtil.getLocation(config.getString("waiting-cuboid.low"));
                if (boundHigh != null && boundLow != null) {
                    waitingCuboid = new Cuboid(boundLow, boundHigh);
                }
            }
        }

        boundHigh = LocationUtil.getLocation(config.getString("game-cuboid.high"));
        boundLow = LocationUtil.getLocation(config.getString("game-cuboid.low"));
        if (boundHigh != null && boundLow != null) {
            gameCuboid = new Cuboid(boundLow, boundHigh);
        }

        spectatorSpawn = LocationUtil.getLocation(config.getString("spectator-location"));

        ConfigurationSection section = config.getSection("islands");
        if (section != null) {
            for (String key : section.keys()) {
                ConfigurationSection island = section.getSection(key);
                if (island == null) continue;
                Location location = LocationUtil.getLocation(island.getString("spawn"));
                if (location == null) continue;
                Location balloon = LocationUtil.getLocation(island.getString("balloon"));
                teams.add(new GameTeam(key, location, balloon));
            }
        }


        // TODO chests!!

        this.defaultChestType = config.getString("settings.defaultChestType", "normal");

        this.maximumPlayers = this.teams.size() * this.teamSize;


        // TODO Load all the data from the config
        startingTimer = new StartingTimer(10, this);
        endingTimer = new EndingTimer(10, this);
        preGameTimer = new PreGameTimer(10, this);

        // LAST
        phaseHandler = new PhaseHandler(this);

        if (!config.getBoolean("settings.enabled")) {
            setGameState(GameState.DISABLED);
        } else {
            phaseHandler.setPhase(new WaitingPhase());
            System.out.println("Game " + name + " has been initialized");
            // TODO?
        }


    }

    @Override
    public void sendMessage(List<IGamePlayer> players, String message) {
        players.forEach(player -> player.sendMessage(message));
    }

    @Override
    public void sendMessages(List<IGamePlayer> players, List<String> messages) {
        players.forEach(player -> player.sendMessages(messages));
    }

    @Override
    public void sendMessages(List<IGamePlayer> players, String... messages) {
        players.forEach(player -> player.sendMessages(Arrays.asList(messages)));
    }

    @Override
    public void sendTitle(List<IGamePlayer> players, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        players.forEach(player -> player.sendTitle(title, subtitle, fadeIn, stay, fadeOut));
    }

    @Override
    public void sendActionBar(List<IGamePlayer> players, String message) {
        players.forEach(player -> player.sendActionBar(message));
    }

    @Override
    public void sendSound(List<IGamePlayer> players, String sound, float volume, float pitch) {
        players.forEach(player -> player.sendSound(sound, volume, pitch));
    }

    @Override
    public void onGamePlayerJoin(IGamePlayer player) {
        SkyWarsGameJoinEvent event = new SkyWarsGameJoinEvent(player, this);
        event.call();

        Player bukkitPlayer = player.getPlayer().get();

        bukkitPlayer.setAllowFlight(false);
        bukkitPlayer.setFlying(false);

        players.add(player);
        player.setGame(this);
        kills.put(player.getUniqueId(), 0);
        bukkitPlayer.getInventory().setHeldItemSlot(4);
        bukkitPlayer.getInventory().clear();
        bukkitPlayer.setGameMode(org.bukkit.GameMode.ADVENTURE);

        if (waitingSpawn != null && waitingCuboid != null) {
            bukkitPlayer.teleport(waitingSpawn);
        } else {
            assigner.assign(this, player);

            IGameTeam team = player.getGameTeam();
            IGameSpawn gameSpawn = team.getGameSpawn();
            if (gameSpawn.isUsed()) {
                bukkitPlayer.teleport(team.getSpawnLocation());
                return;
            }
            gameSpawn.setUsed(true);

            if (!team.getPlayers().isEmpty()) {
                Cage cage = WhaleSkyWars.getInstance().getCageManager().getById(player.getCosmetics().getSelectedCage());
                gameSpawn.setCage(cage);
                if (getGameMode().isTeamGame()) {
                    cage.createBig(team.getSpawnLocation());
                } else {
                    cage.createSmall(team.getSpawnLocation());
                }
            }
            bukkitPlayer.teleport(team.getSpawnLocation());
            WhaleSkyWars.getInstance().getSkyWarsBoard()
                    .removeScoreboard(player);
            WhaleSkyWars.getInstance().getSkyWarsBoard()
                    .setGameBoard(player);
        }

        // TODO
    }

    @Override
    public void onGamePlayerJoin(IGamePlayer player, IGameTeam team) {
        // TODO
    }

    @Override
    public void onGamePlayerSpectate(IGamePlayer player) {

    }

    @Override
    public void onGamePlayerLeave(IGamePlayer player, boolean isSilent) {
        SkyWarsGameLeaveEvent leaveEvent = new SkyWarsGameLeaveEvent(player, this);
        leaveEvent.call();
        spectators.removeIf(gamePlayer -> gamePlayer.getUniqueId().equals(player.getUniqueId()));
        players.removeIf(gamePlayer -> gamePlayer.getUniqueId().equals(player.getUniqueId()));
        player.setGame(null);
        IGameTeam team = player.getGameTeam();
        if (team != null) {
            team.removePlayer(player);
        }
        player.setGameTeam(null);
        player.setDead(false);

        WhaleSkyWars.getInstance().getSkyWarsBoard().removeScoreboard(player);
        PlayerUtil.refresh(player);

        if (WhaleSkyWars.getInstance().getServerMode() == ServerMode.BUNGEE) {
            // TODO
        } else {
            player.getPlayer().ifPresent(bukkitPlayer -> {
                bukkitPlayer.teleport(Bukkit.getWorld("world").getSpawnLocation());
            });
        }


        if (isSilent) return;
        // TODO
    }

    @Override
    public void onGamePlayerDeath(IGamePlayer killer, IGamePlayer victim, KillPath path) {
        Player bukkitVictim = victim.getPlayer().get();
        victim.setDead(true);
        // TODO refresh
        Bukkit.getScheduler().runTaskLater(WhaleSkyWars.getInstance(), () -> {
            bukkitVictim.setGameMode(org.bukkit.GameMode.ADVENTURE);
            bukkitVictim.setAllowFlight(true);
            bukkitVictim.setFlying(true);
            WhaleSkyWars.getInstance().getNms().setCollideWithEntities(bukkitVictim, false);
            bukkitVictim.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, false, false));
        }, 1L);
        getPlayers().forEach(gamePlayers ->  {
            gamePlayers.getPlayer().ifPresent(bukkitPlayer -> {
                bukkitPlayer.hidePlayer(bukkitVictim);
            });
        });

        if (killer != null) {
            // TODO ?
        }

    }

    @Override
    public void onCountDown() {
        if (phaseHandler == null) return;
        phaseHandler.onTick();
        getPlayers()
                .forEach(WhaleSkyWars.getInstance().getSkyWarsBoard()
                        ::updateGameScoreboard);
    }

    @Override
    public void goToNextPhase() {
        phaseHandler.nextPhase();
    }

    @Override
    public void assignTeams() {
        assigner.assign(this);
    }

    @Override
    public void reset() {
        players.clear();
        spectators.clear();
        kills.clear();
        teams.clear();
        events.clear();
        WhaleSkyWars.getInstance().getGameMap().onRestart(this);
    }

    // No interface methods because I don't want people to use this
    public PhaseHandler getPhaseHandler() {
        return phaseHandler;
    }

    public BungeeGame getBungeeGame() {
        return bungeeGame;
    }

    public void setBungeeGame(BungeeGame bungeeGame) {
        this.bungeeGame = bungeeGame;
    }

}
