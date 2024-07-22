package ga.justreddy.wiki.whaleskywars.version.nms;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

/**
 * @author JustReddy
 */
public interface INms {

    boolean isLegacy();

    ChunkGenerator getChunkGenerator();

    void setUnbreakable(ItemStack itemStack);

    void setCollideWithEntities(Entity entity, boolean collide);

    void removeAi(Entity entity);

    String nameOfEnchantment(Enchantment enchantment);

    Enchantment valueOfEnchantment(String enchantment);



}
