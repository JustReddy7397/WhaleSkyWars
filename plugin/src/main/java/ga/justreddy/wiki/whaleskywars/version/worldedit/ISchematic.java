package ga.justreddy.wiki.whaleskywars.version.worldedit;

import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;

/**
 * @author JustReddy
 */
public interface ISchematic {

    String getPrefix();

    void save(File file, Location low, Location high);

    Object get(File file, World world);

    void paste(Object schematic, Location location);

}
