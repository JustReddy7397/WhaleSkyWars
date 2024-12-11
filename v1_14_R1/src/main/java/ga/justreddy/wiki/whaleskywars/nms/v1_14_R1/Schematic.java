package ga.justreddy.wiki.whaleskywars.nms.v1_14_R1;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import ga.justreddy.wiki.whaleskywars.version.worldedit.ISchematic;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author JustReddy
 */
public class Schematic implements ISchematic {
    @Override
    public String getPrefix() {
        return ".schematic";
    }

    @Override
    public void save(File file, Location low, Location high) {
        com.sk89q.worldedit.world.World bukkitWorld = new BukkitWorld(low.getWorld());

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(bukkitWorld)) {
            BlockVector3 vector1 = BlockVector3.at(low.getBlockX(), low.getBlockY(), low.getBlockZ());
            BlockVector3 vector2 = BlockVector3.at(high.getBlockX(), high.getBlockY(), high.getBlockZ());
            CuboidRegion cuboidRegion = new CuboidRegion(bukkitWorld, vector1, vector2);
            Clipboard clipboard = new BlockArrayClipboard(cuboidRegion);
            editSession.setReorderMode(EditSession.ReorderMode.MULTI_STAGE);
            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, cuboidRegion, clipboard, clipboard.getMinimumPoint());
            forwardExtentCopy.setRemovingEntities(true);
            Operations.complete(forwardExtentCopy);
            ClipboardFormat format = ClipboardFormats.findByFile(file);
            try (ClipboardWriter writer = format.getWriter(Files.newOutputStream(file.toPath()))) {
                writer.write(clipboard);
            }
        } catch (WorldEditException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(File file, World world) {
        com.sk89q.worldedit.world.World bukkitWorld = new BukkitWorld(world);
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            ClipboardReader reader = format.getReader(inputStream);
            return reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void paste(Object schematic, Location location) {
        if (!(schematic instanceof Clipboard)) {
            throw new IllegalStateException("Schematic Object is not an instance of Clipboard");
        }
        Clipboard schem = (Clipboard) schematic;

        System.out.println("Attempting to paste schematic");


        com.sk89q.worldedit.world.World world = new BukkitWorld(location.getWorld());
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {

            Location pasteLocation = location.clone().subtract(5, 1, 5);

            Operation operation = new ClipboardHolder(schem)
                    .createPaste(editSession)
                    .to(BlockVector3.at(pasteLocation.getBlockX(), pasteLocation.getBlockY(), pasteLocation.getBlockZ()))
                    .ignoreAirBlocks(true)
                    .copyBiomes(true)
                    .copyEntities(false)
                    .build();

            Operations.complete(operation);

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
