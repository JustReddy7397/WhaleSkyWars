package ga.justreddy.wiki.whaleskywars.version.nms;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

/**
 * @author JustReddy
 */
public interface INms {

    boolean isLegacy();

    ChunkGenerator getChunkGenerator();

    void setJsonMessage(Player player, String message);

    void sendTitle(Player player, String title, String subTitle);

    void sendActionBar(Player player, String action);

    void spawnParticle(Location location, String particle, int count, float offSetX, float offSetY, float offSetZ, float data);

    boolean isParticleCorrect(String particle);

    void setUnbreakable(ItemStack itemStack);

    void setCollideWithEntities(Entity entity, boolean collide);

    void removeAi(Entity entity);

    String nameOfEnchantment(Enchantment enchantment);

    Enchantment valueOfEnchantment(String enchantment);

    void seWorldRule(World world, String rule, boolean value);

    void setNbtValue(ItemStack itemStack, String key, Object value);

    boolean hasNbtValue(ItemStack itemStack, String key);

    Object getNbtValue(ItemStack itemStack, String key);

    ItemStack getItemInHand(Player player);

}
