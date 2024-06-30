package ga.justreddy.wiki.whaleskywars.model.game;

import ga.justreddy.wiki.whaleskywars.api.model.game.ICage;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSpawn;
import org.bukkit.Location;

/**
 * @author JustReddy
 */
public class GameSpawn implements IGameSpawn {

    private final Location location;
    private final boolean used;
    private final ICage cage;

    public GameSpawn(Location location, boolean used, ICage cage) {
        this.location = location;
        this.used = used;
        this.cage = cage;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public ICage getCage() {
        return cage;
    }
}
