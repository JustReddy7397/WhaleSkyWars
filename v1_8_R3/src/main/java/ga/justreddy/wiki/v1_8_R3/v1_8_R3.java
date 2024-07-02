package ga.justreddy.wiki.v1_8_R3;

import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class v1_8_R3 implements INms {

    @Override
    public boolean isLegacy() {
        return true;
    }

    @Override
    public ChunkGenerator getChunkGenerator() {
        return null;
    }

    @Override
    public void setUnbreakable(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(meta);
        }
    }

    @Override
    public void removeAi(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity)entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        tag.setBoolean("Silent", true);
        nmsEntity.f(tag);
    }
}
