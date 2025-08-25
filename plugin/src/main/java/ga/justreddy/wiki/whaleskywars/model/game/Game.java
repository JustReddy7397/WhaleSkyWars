package ga.justreddy.wiki.whaleskywars.model.game;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameJoinEvent;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameLeaveEvent;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameStateChangeEvent;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillEffect;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillMessage;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.*;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.ChestType;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameSpawn;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ITeamAssigner;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import ga.justreddy.wiki.whaleskywars.model.Messages;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.chests.CustomChest;
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
import ga.justreddy.wiki.whaleskywars.supportold.BungeeUtil;
import ga.justreddy.wiki.whaleskywars.util.LocationUtil;
import ga.justreddy.wiki.whaleskywars.util.PlayerUtil;
import ga.justreddy.wiki.whaleskywars.util.Replaceable;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
@Getter
public class Game implements IGame {

    private static final int SPECTATOR_INVISIBILITY_DURATION = 1000000;
    private static final Logger LOGGER = Bukkit.getLogger();

    private final String name;
    private final String displayName;
    private final ITeamAssigner assigner = new TeamAssigner();
    private final TempConfig config;
    private GameState state;
    private GameMode mode;
    private final List<IGamePlayer> players;
    private final List<IGameTeam> teams;
    private final List<IGamePlayer> spectators;
    private final Map<IGamePlayer, Integer> kills;
    private final Map<UUID, ChestType> votedChestTypes;
    private final Map<Location, String> chests;
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
    private ChestType defaultChestType;
    private boolean teamGame = false;
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
        this.events = new LinkedList<>();
        this.votedChestTypes = new HashMap<>();
        this.chests = new HashMap<>();
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
        return players.size();
    }

    @Override
    public List<IGamePlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public List<IGamePlayer> getAlivePlayers() {
        return players.stream()
                .filter(player -> !player.isDead() && !spectators.contains(player))
                .collect(Collectors.toList());
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
        return teams.stream()
                .filter(team -> !team.getAlivePlayers().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<IGameTeam> getSpectatorTeams() {
        return teams.stream()
                .filter(team -> !team.getSpectatorPlayers().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public Map<IGamePlayer, Integer> getKills() {
        return Collections.unmodifiableMap(kills);
    }

    @Override
    public int getKills(IGamePlayer player) {
        return kills.getOrDefault(player, 0);
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
        SkyWarsGameStateChangeEvent event = new SkyWarsGameStateChangeEvent(this, state, gameState);
        event.call();
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
        return events.stream().filter(e -> e.isEnabled() && e.getTimer() > 0).findFirst().orElse(null);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void setDefaultChestType(ChestType chestType) {
        this.defaultChestType = chestType;
    }

    @Override
    public ChestType getGameChestType() {
        if (votedChestTypes.isEmpty()) return ChestType.NORMAL;
        Map<ChestType, Long> freq = votedChestTypes.values().stream()
                .collect(Collectors.groupingBy(ct -> ct, Collectors.counting()));
        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(ChestType.NORMAL);
    }

    @Override
    public List<Map.Entry<String, Integer>> getTopThreeKillers() {
        return kills.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<UUID, ChestType> getVotedChestTypes() {
        return Collections.unmodifiableMap(votedChestTypes);
    }

    @Override
    public void voteChestType(IGamePlayer player, ChestType chestType) {
        votedChestTypes.put(player.getUniqueId(), chestType);
    }

    @Override
    public ChestType getVotedChestType(IGamePlayer player) {
        return votedChestTypes.get(player.getUniqueId());
    }

    @Override
    public void addChest(Location location, String type) {
        chests.put(location, type);
    }

    @Override
    public boolean isChest(Location location) {
        return chests.keySet().stream().anyMatch(chest ->
                chest.getBlockX() == location.getBlockX() &&
                        chest.getBlockY() == location.getBlockY() &&
                        chest.getBlockZ() == location.getBlockZ());
    }

    @Override
    public void removeChest(Location location) {
        chests.remove(location);
    }

    @Override
    public void clearVotes() {
        votedChestTypes.clear();
    }

    @Override
    public void init(World world) {
        this.world = world;
        initializeSettings();
        initializeSpawnsAndCuboids();
        initializeTeams();
        initializeChests();
        initializeTimersAndEvents();
        finalizeInitialization();
    }

    private void initializeSettings() {
        teamSize = config.getInteger("settings.teamSize");
        minimumPlayers = config.getInteger("settings.minimumPlayers");

        String gameModeStr = config.getString("settings.gameMode", null);
        teamGame = teamSize > 1;

        if (gameModeStr == null || gameModeStr.equalsIgnoreCase("null")) {
            mode = teamGame ? new TeamGameMode() : new SoloGameMode();
        } else {
            GameMode foundMode = WhaleSkyWars.getInstance().getGameModeManager().of(gameModeStr);
            if (foundMode == null) {
                TextUtil.error(null, "Game " + name + " has an invalid game mode " + gameModeStr + "!", true);
                return;
            }
            this.mode = foundMode;
        }
        this.mode.setGame(this);
    }

    private void initializeSpawnsAndCuboids() {
        Location boundHigh, boundLow;

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
    }

    private void initializeTeams() {
        ConfigurationSection section = config.getSection("islands");
        if (section != null) {
            for (String key : section.keys()) {
                ConfigurationSection island = section.getSection(key);
                if (island == null) continue;
                Location location = LocationUtil.getLocation(island.getString("spawn"));
                if (location == null) continue;
                Location balloon = LocationUtil.getLocation(island.getString("balloon"));
                IGameTeam team = new GameTeam(key, this, location, balloon);
                ConfigurationSection chestsSection = island.getSection("chests");
                if (chestsSection != null) {
                    for (String str : chestsSection.keys()) {
                        ConfigurationSection chest = chestsSection.getSection(str);
                        if (chest == null) continue;
                        Location chestLocation = LocationUtil.getLocation(chest.getString("location"));
                        if (chestLocation == null) continue;
                        team.addChest(chestLocation, chest.getString("type"));
                    }
                }
                teams.add(team);
            }
        }
    }

    private void initializeChests() {
        this.defaultChestType = ChestType.valueOf(config.getString("settings.defaultChestType", "NORMAL").toUpperCase());
        ConfigurationSection chestsSection = config.getSection("chests");
        if (chestsSection != null) {
            for (String key : chestsSection.keys()) {
                ConfigurationSection chest = chestsSection.getSection(key);
                if (chest == null) continue;
                Location location = LocationUtil.getLocation(chest.getString("location"));
                if (location == null) continue;
                addChest(location, chest.getString("type"));
            }
        }
        this.maximumPlayers = teams.size() * teamSize;
    }

    private void initializeTimersAndEvents() {
        startingTimer = new StartingTimer(
                WhaleSkyWars.getInstance().getSettingsConfig()
                        .getInteger("game-options.countdowns.start"), this);
        endingTimer = new EndingTimer(
                WhaleSkyWars.getInstance().getSettingsConfig()
                        .getInteger("game-options.countdowns.cages"), this);
        preGameTimer = new PreGameTimer(
                WhaleSkyWars.getInstance().getSettingsConfig()
                        .getInteger("game-options.countdowns.end"), this);

        List<String> eventList = WhaleSkyWars.getInstance().getSettingsConfig().getStringList("game-options.events");
        eventList.forEach(eventStr -> {
            String[] split = eventStr.split(";");
            String eventName = split[0];
            int timer = Integer.parseInt(split[1]);
            GameEvent gameEvent = WhaleSkyWars.getInstance().getGameEventManager().copyOf(eventName);
            if (gameEvent == null) {
                LOGGER.warning("Event " + eventName + " does not exist.");
                return;
            }
            gameEvent.setTimer(timer);
            gameEvent.setEnabled(true);
            events.add(gameEvent);
        });
    }

    private void finalizeInitialization() {
        phaseHandler = new PhaseHandler(this);

        if (!config.getBoolean("settings.enabled")) {
            setGameState(GameState.DISABLED);
        } else {
            phaseHandler.setPhase(new WaitingPhase());
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
        List<String> msgList = Arrays.asList(messages);
        players.forEach(player -> player.sendMessages(msgList));
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

        Optional<Player> bukkitPlayerOpt = player.getPlayer();
        if (bukkitPlayerOpt.isEmpty()) return;
        Player bukkitPlayer = bukkitPlayerOpt.get();

        bukkitPlayer.setAllowFlight(false);
        bukkitPlayer.setFlying(false);

        players.add(player);
        player.setGame(this);
        kills.put(player, 0);
        bukkitPlayer.getInventory().setHeldItemSlot(4);
        bukkitPlayer.getInventory().clear();
        bukkitPlayer.setGameMode(org.bukkit.GameMode.ADVENTURE);

        if (waitingSpawn != null && waitingCuboid != null) {
            bukkitPlayer.teleport(waitingSpawn);
            WhaleSkyWars.getInstance().getNms().setWaitingLobbyName(player);
        } else {
            assigner.assign(this, player);

            IGameTeam team = player.getGameTeam();
            IGameSpawn gameSpawn = team.getGameSpawn();
            if (gameSpawn.isUsed()) {
                bukkitPlayer.teleport(team.getSpawnLocation());
            }
            gameSpawn.setUsed(true);

            if (!team.getPlayers().isEmpty()) {
                Cage cage = WhaleSkyWars.getInstance().getCageManager().getById(player.getCosmetics().getSelectedCageId());
                gameSpawn.setCage(cage);
                if (teamGame) cage.createBig(team.getSpawnLocation());
                else cage.createSmall(team.getSpawnLocation());
            }
            bukkitPlayer.teleport(team.getSpawnLocation());
            WhaleSkyWars.getInstance().getNms().setTeamName(player);
        }
        WhaleSkyWars.getInstance().getSkyWarsBoard().removeScoreboard(player);
        WhaleSkyWars.getInstance().getSkyWarsBoard().setGameBoard(player);

        sendMessage(getPlayers(), Messages.GAME_JOINED.toString(bukkitPlayer, Replaceable.of("<player>", player.getName()),
                Replaceable.of("<players>", String.valueOf(getPlayerCount())),
                Replaceable.of("<max-players>", String.valueOf(getMaximumPlayers()))));
    }

    @Override
    public void onGamePlayerJoin(IGamePlayer player, IGameTeam team) {
        SkyWarsGameJoinEvent event = new SkyWarsGameJoinEvent(player, this);
        event.call();

        Optional<Player> bukkitPlayerOpt = player.getPlayer();
        if (!bukkitPlayerOpt.isPresent()) return;
        Player bukkitPlayer = bukkitPlayerOpt.get();

        bukkitPlayer.setAllowFlight(false);
        bukkitPlayer.setFlying(false);

        players.add(player);
        player.setGame(this);
        kills.put(player, 0);
        bukkitPlayer.getInventory().setHeldItemSlot(4);
        bukkitPlayer.getInventory().clear();
        bukkitPlayer.setGameMode(org.bukkit.GameMode.ADVENTURE);

        if (waitingSpawn != null && waitingCuboid != null) {
            bukkitPlayer.teleport(waitingSpawn);
            player.setGameTeam(team);
        } else {
            player.setGameTeam(team);
            IGameSpawn gameSpawn = team.getGameSpawn();
            if (gameSpawn.isUsed()) {
                bukkitPlayer.teleport(team.getSpawnLocation());
            }
            gameSpawn.setUsed(true);

            if (!team.getPlayers().isEmpty()) {
                Cage cage = WhaleSkyWars.getInstance().getCageManager().getById(player.getCosmetics().getSelectedCageId());
                gameSpawn.setCage(cage);
                if (teamGame) cage.createBig(team.getSpawnLocation());
                else cage.createSmall(team.getSpawnLocation());
            }
            bukkitPlayer.teleport(team.getSpawnLocation());
            WhaleSkyWars.getInstance().getSkyWarsBoard().removeScoreboard(player);
            WhaleSkyWars.getInstance().getSkyWarsBoard().setGameBoard(player);
        }
        sendMessage(getPlayers(), Messages.GAME_JOINED.toString(bukkitPlayer, Replaceable.of("<player>", player.getName()),
                Replaceable.of("<players>", String.valueOf(getPlayerCount())),
                Replaceable.of("<max-players>", String.valueOf(getMaximumPlayers()))));
    }

    @Override
    public void onGamePlayerSpectate(IGamePlayer player) {
        player.setGame(this);
        spectators.add(player);
        player.setGameTeam(null);
        player.setDead(false);
        player.getPlayer().ifPresent(bukkitPlayer -> {
            bukkitPlayer.setGameMode(org.bukkit.GameMode.SPECTATOR);
            bukkitPlayer.setAllowFlight(true);
            bukkitPlayer.setFlying(true);
            WhaleSkyWars.getInstance().getNms().setCollideWithEntities(bukkitPlayer, false);
            bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, false, false));
            bukkitPlayer.teleport(spectatorSpawn);
            for (IGamePlayer gamePlayer : getPlayers()) {
                gamePlayer.getPlayer().ifPresent(bukkitPlayer::hidePlayer);
            }
        });
        WhaleSkyWars.getInstance().getSkyWarsBoard().removeScoreboard(player);
        PlayerUtil.refresh(player);
    }

    @Override
    public void onGamePlayerLeave(IGamePlayer player, boolean isSilent, boolean kick) {
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

        for (IGamePlayer otherPlayer : getPlayers()) {
            otherPlayer.getPlayer().ifPresent(bukkitPlayer -> {
                player.getPlayer().ifPresent(bukkitPlayer::showPlayer);
                bukkitPlayer.showPlayer(bukkitPlayer);
            });
        }

        if (kick) {
            if (WhaleSkyWars.getInstance().getServerMode() == ServerMode.BUNGEE) {
                BungeeUtil.sendBackToServer(player);
            } else {
                player.getPlayer().ifPresent(bukkitPlayer -> {
                    bukkitPlayer.teleport(WhaleSkyWars.getInstance().getSpawn());
                });
            }
        }


        WhaleSkyWars.getInstance().getNms().removeTeamName(player);

        if (isSilent) return;
        sendMessage(getPlayers(), Messages
                .GAME_LEFT.toString(player.getPlayer().get(), Replaceable.of(
                                "<player>", player.getName()
                        ),
                        Replaceable.of(
                                "<players>", String.valueOf(getPlayerCount())
                        ),
                        Replaceable.of(
                                "<max-players>", String.valueOf(getMaximumPlayers())
                        )));

    }

    @Override
    public void onGamePlayerDeath(IGamePlayer killer, IGamePlayer victim, KillPath path) {
        Player bukkitVictim = victim.getPlayer().get();
        victim.setDead(true);
        Bukkit.getScheduler().runTaskLater(WhaleSkyWars.getInstance(), () -> {
            if (XMaterial.supports(13)) {
                WhaleSkyWars.getInstance().getNms().respawn(bukkitVictim);
            }
            bukkitVictim.teleport(spectatorSpawn);
            bukkitVictim.setGameMode(org.bukkit.GameMode.ADVENTURE);
            bukkitVictim.setAllowFlight(true);
            bukkitVictim.setFlying(true);
            WhaleSkyWars.getInstance().getNms().setCollideWithEntities(bukkitVictim, false);
            bukkitVictim.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, false, false));
        }, 1L);
        getPlayers().forEach(gamePlayers -> {
            gamePlayers.getPlayer().ifPresent(bukkitPlayer -> {
                bukkitPlayer.hidePlayer(bukkitVictim);
            });
        });

        if (killer != null) {

            KillEffect effect = killer.getCosmetics().getSelectedKillEffect();
            KillMessage message = killer.getCosmetics().getSelectedKillMessage();

            if (effect != null) {
                effect.onKill(killer, victim);
            }

            if (message != null) {
                switch (path) {
                    case VOID:
                        message.sendVoidMessage(this, killer, victim);
                        break;
                    case FALL:
                        message.sendFallMessage(this, killer, victim);
                        break;
                    case FIRE:
                        message.sendFireMessage(this, killer, victim);
                        break;
                    case EXPLOSION:
                        message.sendExplosionMessage(this, killer, victim);
                        break;
                    case DROWNING:
                        message.sendDrowningMessage(this, killer, victim);
                        break;
                    case SUFFOCATION:
                        message.sendSuffocationMessage(this, killer, victim);
                        break;
                    case MELEE:
                        message.sendMeleeMessage(this, killer, victim);
                        break;
                    case PROJECTILE:
                        message.sendProjectileMessage(this, killer, victim);
                        break;
                    default:
                        message.sendUnknownMessage(this, killer, victim);
                        break;
                }
            }

        } else {
            switch (path) {
                case VOID:
                    sendMessage(getPlayers(),
                            Messages.GAME_DEATH_VOID.toString(bukkitVictim, Replaceable.of(
                                    "<player>", victim.getName()
                            )));
                    break;
                case FALL:
                    sendMessage(getPlayers(),
                            Messages.GAME_DEATH_FALL.toString(bukkitVictim, Replaceable.of(
                                    "<player>", victim.getName()
                            )));
                    break;
                case FIRE:
                    sendMessage(getPlayers(),
                            Messages.GAME_DEATH_FIRE.toString(bukkitVictim, Replaceable.of(
                                    "<player>", victim.getName()
                            )));
                    break;
                case EXPLOSION:
                    sendMessage(getPlayers(),
                            Messages.GAME_DEATH_EXPLOSION.toString(bukkitVictim, Replaceable.of(
                                    "<player>", victim.getName()
                            )));
                    break;
                case DROWNING:
                    sendMessage(getPlayers(),
                            Messages.GAME_DEATH_DROWNED.toString(bukkitVictim, Replaceable.of(
                                    "<player>", victim.getName()
                            )));
                    break;
                case SUFFOCATION:
                    sendMessage(getPlayers(),
                            Messages.GAME_DEATH_SUFFOCATION.toString(bukkitVictim, Replaceable.of(
                                    "<player>", victim.getName()
                            )));
                    break;
                default:
                    sendMessage(getPlayers(),
                            Messages.GAME_DEATH_UNKNOWN.toString(bukkitVictim, Replaceable.of(
                                    "<player>", victim.getName()
                            )));
                    break;


            }
        }

    }

    @Override
    public void onCountDown() {
        if (phaseHandler == null) return;
        phaseHandler.onTick();
        if (isGameState(GameState.WAITING) || isGameState(GameState.PLAYING) || isGameState(GameState.RESTORING)) {
            getPlayers()
                    .forEach(WhaleSkyWars.getInstance().getSkyWarsBoard()
                            ::updateGameScoreboard);
        }
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

    public void fillChests(ChestType chestType) {
        chests.forEach((loc, id) -> {
            if (loc == null) return;
            Block block = loc.getBlock();
            if (block == null) return;
            if (!(block.getState() instanceof Chest)) return;
            Chest chest = (Chest) block.getState();
            CustomChest customChest = WhaleSkyWars.getInstance().getChestManager().getById(id);
            if (customChest == null) return;
            customChest.populateChest(chest.getBlockInventory(), chestType, getGameChestType());
        });
        teams.forEach(team -> team.fill(this, chestType));
    }

    public BungeeGame getBungeeGame() {
        return bungeeGame;
    }

    public void setBungeeGame(BungeeGame bungeeGame) {
        this.bungeeGame = bungeeGame;
    }


}
