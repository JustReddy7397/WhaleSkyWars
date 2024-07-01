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
    private ICage cage;

    public GameSpawn(Location location, boolean used, ICage cage) {
        this.location = location.clone().add(0.5D, 0.0D, 0.5D);
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

    @Override
    public void setCage(ICage cage) {
        this.cage = cage;
    }
}
