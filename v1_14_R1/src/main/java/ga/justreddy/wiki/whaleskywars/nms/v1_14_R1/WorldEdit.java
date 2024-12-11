package ga.justreddy.wiki.whaleskywars.nms.v1_14_R1;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockType;
import ga.justreddy.wiki.whaleskywars.version.worldedit.IWorldEdit;
import org.bukkit.Location;

/**
 * @author JustReddy
 */
public class WorldEdit implements IWorldEdit {

    @Override
    public void clear(Location high, Location low) {
        BlockVector3 blockVector = BlockVector3.at(low.getBlockX(), low.getBlockY(), low.getBlockZ());
        BlockVector3 blockVector1 = BlockVector3.at(high.getBlockX(), high.getBlockY(), high.getBlockZ());
        try (EditSession session = com.sk89q.worldedit.WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(high.getWorld()), -1)) {
            Region region = new CuboidRegion(blockVector1, blockVector);
            BlockType blockType = BlockType.REGISTRY.get("air");
            session.setBlocks(region, blockType.getDefaultState());
        } catch (MaxChangedBlocksException exception) {
            exception.printStackTrace();
        }
    }
}
