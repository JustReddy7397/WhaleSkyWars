package ga.justreddy.wiki.v1_8_R3;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import ga.justreddy.wiki.whaleskywars.version.worldedit.IWorldEdit;
import org.bukkit.Location;

/**
 * @author JustReddy
 */
public class WorldEdit implements IWorldEdit {

    @Override
    public void clear(Location high, Location low) {
        BlockVector blockVector = new BlockVector(low.getBlockX(), low.getBlockY(), low.getBlockZ());
        BlockVector blockVector1 = new BlockVector(high.getBlockX(), high.getBlockY(), high.getBlockZ());
        EditSession session = com.sk89q.worldedit.WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(high.getWorld()), -1);
        Region region = new CuboidRegion(blockVector1, blockVector);
        BlockType blockType = BlockType.AIR;
        try {
            session.setBlocks(region, new BaseBlock(blockType.getID()));
        } catch (MaxChangedBlocksException exception) {
            exception.printStackTrace();
        } finally {
            session.flushQueue();
        }
    }

}
