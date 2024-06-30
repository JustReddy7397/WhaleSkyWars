package ga.justreddy.wiki.whaleskywars.api.model.game.team;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSpawn;
import org.bukkit.Location;

import java.util.List;

/**
 * Represents a GameSpawn in the WhaleSkyWars game.
 * A GameSpawn is a location where players can spawn in the game.
 * Each GameSpawn has a location, a state of being used, and a cage.
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
     * Returns the list of game spawns for the GameTeam.
     *
     * @return the list of game spawns
     */
    List<IGameSpawn> getGameSpawns();

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

}
