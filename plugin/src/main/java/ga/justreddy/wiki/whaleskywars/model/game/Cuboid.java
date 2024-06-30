package ga.justreddy.wiki.whaleskywars.model.game;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.ICuboid;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author JustReddy
 */
public class Cuboid implements ICuboid {

    private final Location highPoints;
    private final Location lowPoints;
    private final World world;
    private final int highX;
    private final int highY;
    private final int highZ;
    private final int lowX;
    private final int lowY;
    private final int lowZ;

    public Cuboid(Location highPoints, Location lowPoints) {
        this.highPoints = highPoints;
        this.lowPoints = lowPoints;
        this.world = highPoints.getWorld();
        this.highX = Math.min(highPoints.getBlockX(), lowPoints.getBlockX());
        this.highY = Math.min(highPoints.getBlockY(), lowPoints.getBlockY());
        this.highZ = Math.min(highPoints.getBlockZ(), lowPoints.getBlockZ());
        this.lowX = Math.max(highPoints.getBlockX(), lowPoints.getBlockX());
        this.lowY = Math.max(highPoints.getBlockY(), lowPoints.getBlockY());
        this.lowZ = Math.max(highPoints.getBlockZ(), lowPoints.getBlockZ());
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getHighX() {
        return highX;
    }

    @Override
    public int getLowX() {
        return lowX;
    }

    @Override
    public int getHighY() {
        return highY;
    }

    @Override
    public int getLowY() {
        return lowY;
    }

    @Override
    public int getHighZ() {
        return highZ;
    }

    @Override
    public int getLowZ() {
        return lowZ;
    }

    @Override
    public Location getHighLocation() {
        return highPoints;
    }

    @Override
    public Location getLowLocation() {
        return lowPoints;
    }

    @Override
    public boolean contains(Location location) {
        return location.getWorld() == world
                && location.getBlockX() >= highX
                && location.getBlockX() <= lowX
                && location.getBlockY() >= highY
                && location.getBlockY() <= lowY
                && location.getBlockZ() >= highZ
                && location.getBlockZ() <= lowZ;
    }

    @Override
    public void clear() {
        WhaleSkyWars.getInstance().getWorldEdit()
                .clear(highPoints, lowPoints);
    }
}
