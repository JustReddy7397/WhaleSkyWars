package ga.justreddy.wiki.whaleskywars.api.model.game.team;

import org.bukkit.Location;

/**
 * Represents a GameSpawn in the WhaleSkyWars game.
 * A GameSpawn is a location where players can spawn in the game.
 * Each GameSpawn has a location, a state of being used, and a cage.
 *
 * @author JustReddy
 */
public interface IGameSpawn {

    /**
     * Returns the location of the GameSpawn.
     *
     * @return the location of the GameSpawn
     */
    Location getLocation();

    /**
     * Checks if the GameSpawn is used.
     *
     * @return true if the GameSpawn is used, false otherwise
     */
    boolean isUsed();

    /**
     * Sets the used state of the GameSpawn.
     *
     * @param used the used state to set
     */
    void setUsed(boolean used);

    /**
     * Returns the cage of the GameSpawn.
     *
     * @return the cage of the GameSpawn
     */
    ICage getCage();

    /**
     * Sets the cage of the GameSpawn.
     *
     * @param cage the cage to set
     */
    void setCage(ICage cage);

}
