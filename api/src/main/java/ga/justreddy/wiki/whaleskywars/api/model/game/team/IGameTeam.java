package ga.justreddy.wiki.whaleskywars.api.model.game.team;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.ChestType;
import org.bukkit.Location;

import java.util.List;

/**
 * Represents a GameTeam in the WhaleSkyWars game.
 * A GameTeam is a group of players in the game.
 * Each GameTeam has an id, a list of players, a list of living players,
 * a list of spectator players, a {@link IGameSpawn} GameSpawn, a size
 * and methods to add, check and remove players.
 *
 * @author JustReddy
 */
public interface IGameTeam {

    /**
     * Returns the id of the GameTeam.
     *
     * @return the id of the GameTeam
     */
    String getId();

    /**
     * Returns the list of players in the GameTeam.
     *
     * @return the list of players
     */
    List<IGamePlayer> getPlayers();

    /**
     * Returns the list of alive players in the GameTeam.
     *
     * @return the list of alive players
     */
    List<IGamePlayer> getAlivePlayers();

    /**
     * Returns the list of spectator players in the GameTeam.
     *
     * @return the list of spectator players
     */
    List<IGamePlayer> getSpectatorPlayers();

    /**
     * Returns the GameSpawn of the GameTeam.
     *
     * @return the GameSpawn of the GameTeam
     */
    IGameSpawn getGameSpawn();

    /**
     * Returns the size of the GameTeam.
     *
     * @return the size of the GameTeam
     */
    int getSize();

    /**
     * Adds a player to the GameTeam.
     *
     * @param player the player to add
     */
    void addPlayer(IGamePlayer player);

    /**
     * Checks if a player is in the GameTeam.
     *
     * @param player the player to check
     * @return true if the player is in the GameTeam, false otherwise
     */
    boolean hasPlayer(IGamePlayer player);

    /**
     * Removes a player from the GameTeam.
     *
     * @param player the player to remove
     */
    void removePlayer(IGamePlayer player);

    /**
     * Returns the spawn location for the GameTeam.
     *
     * @return the spawn location
     */
    Location getSpawnLocation();

    /**
     * Returns the game of the GameTeam.
     * @return the game of the GameTeam
     */
    IGame getGame();

    /**
     * Returns the priority of the GameTeam.
     * Used for sorting in tablist
     *
     * @return the priority of the GameTeam
     */
    int getPriority();

    /**
     * Spawns a balloon for the GameTeam.
     */
    void spawnBalloon();

    /**
     * Adds a chest to the GameTeam.
     * @param location the location of the chest
     * @param type the type of the chest
     */
    void addChest(Location location, String type);

    /**
     * Checks if the location is a chest.
     * @param location the location
     * @return true if the location is a chest, false otherwise
     */
    boolean isChest(Location location);

    /**
     * Removes a chest from the GameTeam.
     * @param location the location of the chest
     */
    void removeChest(Location location);

    /**
     * Fills the chests for the GameTeam.
     * @param game the game
     * @param chestType the chest type
     */
    void fill(IGame game, ChestType chestType);
}
