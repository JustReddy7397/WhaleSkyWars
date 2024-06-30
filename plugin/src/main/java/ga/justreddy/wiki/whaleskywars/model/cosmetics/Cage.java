package ga.justreddy.wiki.whaleskywars.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Cosmetic;
import ga.justreddy.wiki.whaleskywars.api.model.game.ICage;
import ga.justreddy.wiki.whaleskywars.model.game.Cuboid;
import org.bukkit.Location;

import java.io.File;

/**
 * @author JustReddy
 */
public class Cage extends Cosmetic implements ICage {

    private final File file;

    public Cage(String name, int id, int cost, File file) {
        super(name, id, cost);
        this.file = file;
    }

    @Override
    public void create(Location location) {
        WhaleSkyWars.getInstance()
                .getSchematic().paste(WhaleSkyWars.getInstance().getSchematic().get(file, location.getWorld()), location);
    }

    @Override
    public void remove(Location location) {
        Cuboid cuboid = new Cuboid(location.clone().add(5.0, 6.0, 5.0), location.clone().add(-5.0, -1.0, -5.0));
        cuboid.clear();
    }
}
