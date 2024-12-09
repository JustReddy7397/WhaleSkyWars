package ga.justreddy.wiki.whaleskywars.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * @author JustReddy
 */
public class LocationUtil {

    public static String toLocation(Location location) {
        if (location == null) {
            return "N/A";
        }
        return location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch();
    }

    public static Location getLocation(String path) {
        String[] split = path.split(" ");
        if (split[0].equals("N/A")) {
            return null;
        }
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    public static Location getFixedSide(Location location, double rotate) {
        float yaw = location.getYaw() / 60.0F;
        return location.clone().subtract((new Vector(Math.cos((double) yaw + 5.1D), 0.0D, Math.sin((double) yaw + 5.1D))).normalize().multiply(rotate));
    }

    public static boolean equalsBlock(Location one, Location two) {
        return one.getBlockX()
                == two.getBlockX()
                && one.getBlockY()
                == two.getBlockY()
                && one.getBlockZ()
                == two.getBlockZ();
    }

}
