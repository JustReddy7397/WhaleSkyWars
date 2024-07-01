package ga.justreddy.wiki.whaleskywars.model.game;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.ICuboid;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameMode;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ITeamAssigner;
import ga.justreddy.wiki.whaleskywars.model.game.team.TeamAssigner;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class Game implements IGame {

    private final String name;
    private final String displayName;

    private GameState state;
    private GameMode mode;

    private List<IGamePlayer> players;
    private List<IGameTeam> teams;
    private List<IGamePlayer> spectators;

    private Map<UUID, Integer> kills;

    private ICuboid waitingCuboid;
    private ICuboid gameCuboid;

    private Location waitingSpawn;
    private Location spectatorSpawn;

    private int minimumPlayers;
    private int maximumPlayers;
    private int teamSize;

    private final ITeamAssigner assigner = new TeamAssigner();

    private final FileConfiguration config;

    private World world;

    public Game(String name, FileConfiguration config) {
        this.name = name;
        this.config = config;
        this.displayName = config.getString("settings.displayName", name);
        this.players = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.spectators = new ArrayList<>();
        this.kills = new HashMap<>();
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
        this.state = gameState;
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
    public void setGameMode(GameMode gameMode) {
        this.mode = gameMode;
    }

    @Override
    public boolean isGameMode(GameMode gameMode) {
        return mode == gameMode;
    }

    @Override
    public void init(World world) {

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

    }

    @Override
    public void onGamePlayerJoin(IGamePlayer player, IGameTeam team) {

    }

    @Override
    public void onGamePlayerSpectate(IGamePlayer player) {

    }

    @Override
    public void onGamePlayerLeave(IGamePlayer player, boolean isSilent) {

    }

    @Override
    public void onCountDown() {

    }

    @Override
    public void restart() {

    }
}
