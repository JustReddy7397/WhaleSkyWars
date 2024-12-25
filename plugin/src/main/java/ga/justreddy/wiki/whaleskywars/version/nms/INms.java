package ga.justreddy.wiki.whaleskywars.version.nms;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
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

    <V> V setNbtValue(V object, String key, Object value);

    boolean hasNbtValue(Object object, String key);

    Object getNbtValue(Object value, String key);

    ItemStack getItemInHand(Player player);

    Block getRelativeBlock(Location location);

    Block getTargetBlock(Player player, int range);

    void setTeamName(IGameTeam team);

    void setTeamName(IGamePlayer player);

    void removeTeamNames(IGame game);

    void removeTeamName(IGame game, IGamePlayer player);

    void removeTeamName(IGameTeam team);

    void removeTeamName(IGamePlayer player);

    void setWaitingLobbyName(IGamePlayer player);

    void removeWaitingLobbyName(IGame game);

    void removeWaitingLobbyName(IGame game, IGamePlayer player);

    void respawn(Player player);

    void sendComponent(Player player, TextComponent component);

    TextComponent setHoverText(TextComponent component, String text);

}
