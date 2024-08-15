package ga.justreddy.wiki.whaleskywars.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Cosmetic;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ICage;
import ga.justreddy.wiki.whaleskywars.model.game.Cuboid;
import org.bukkit.Location;

import java.io.File;

/**
 * @author JustReddy
 */
public class Cage extends Cosmetic implements ICage {

    private final File small;
    private final File big;

    public Cage(String name, int id, int cost, File small, File big) {
        super(name, id, cost);
        this.small = small;
        this.big = big;
    }

    @Override
    public void createSmall(Location location) {
        WhaleSkyWars.getInstance()
                .getSchematic().paste(WhaleSkyWars.getInstance().getSchematic().get(small, location.getWorld()), location);
    }

    @Override
    public void createBig(Location location) {
        WhaleSkyWars.getInstance()
                .getSchematic().paste(WhaleSkyWars.getInstance().getSchematic().get(big, location.getWorld()), location);
    }

    @Override
    public void remove(Location location) {
        Cuboid cuboid = new Cuboid(location.clone().add(5.0, 6.0, 5.0), location.clone().add(-5.0, -1.0, -5.0));
        cuboid.clear();
    }
}
