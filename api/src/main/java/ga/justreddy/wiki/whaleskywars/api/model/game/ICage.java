package ga.justreddy.wiki.whaleskywars.api.model.game;

import org.bukkit.Location;

/**
 * Represents a Cage in the WhaleSkyWars game.
 * A Cage is a type of Cosmetic and implements the ICage interface.
 * Each Cage has a name, an id, a cost, and a file associated with it.
 *
 * @author JustReddy
 */
public interface ICage {

    /**
     * Creates the Cage at the given location.
     *
     * @param location the location to create the Cage at
     */
    void create(Location location);

    /**
     * Removes the Cage from the given location.
     *
     * @param location the location to remove the Cage from
     */
    void remove(Location location);

}
