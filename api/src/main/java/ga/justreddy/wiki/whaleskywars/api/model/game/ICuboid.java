package ga.justreddy.wiki.whaleskywars.api.model.game;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Represents a Cuboid in the WhaleSkyWars game.
 * A Cuboid is a 3D region in the game world.
 * Each Cuboid has a high and low location, and methods to get the world it's in, its dimensions, and whether a location is within it.
 *
 * @author JustReddy
 */
public interface ICuboid {

    /**
     * Returns the world that the Cuboid is in.
     *
     * @return the world of the Cuboid
     */
    World getWorld();

    /**
     * Returns the highest X coordinate of the Cuboid.
     *
     * @return the highest X coordinate
     */
    int getHighX();

    /**
     * Returns the lowest X coordinate of the Cuboid.
     *
     * @return the lowest X coordinate
     */
    int getLowX();

    /**
     * Returns the highest Y coordinate of the Cuboid.
     *
     * @return the highest Y coordinate
     */
    int getHighY();

    /**
     * Returns the lowest Y coordinate of the Cuboid.
     *
     * @return the lowest Y coordinate
     */
    int getLowY();

    /**
     * Returns the highest Z coordinate of the Cuboid.
     *
     * @return the highest Z coordinate
     */
    int getHighZ();

    /**
     * Returns the lowest Z coordinate of the Cuboid.
     *
     * @return the lowest Z coordinate
     */
    int getLowZ();

    /**
     * Returns the high location of the Cuboid.
     *
     * @return the high location
     */
    Location getHighLocation();

    /**
     * Returns the low location of the Cuboid.
     *
     * @return the low location
     */
    Location getLowLocation();

    /**
     * Checks if the given location is within the Cuboid.
     *
     * @param location the location to check
     * @return true if the location is within the Cuboid, false otherwise
     */
    boolean contains(Location location);

    /**
     * Clears the Cuboid.
     */
    void clear();

}
