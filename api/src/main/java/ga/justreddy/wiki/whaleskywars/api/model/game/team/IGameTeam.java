package ga.justreddy.wiki.whaleskywars.api.model.game.team;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
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
     * Spawns a balloon for the GameTeam.
     */
    void spawnBalloon();

}
