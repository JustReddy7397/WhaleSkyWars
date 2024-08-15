package ga.justreddy.wiki.whaleskywars.util;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * @author JustReddy
 */
public class PlayerUtil {

    public static void refresh(IGamePlayer gamePlayer) {
        if (gamePlayer == null) return;
        Player player = gamePlayer.getPlayer().orElse(null);
        if (player == null) return;
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setHealthScale(20.0D);
        player.setNoDamageTicks(0);
        player.setFireTicks(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFoodLevel(20);
        player.setExp(0.0F);
        player.setLevel(0);
        Bukkit.getScheduler().runTaskLater(WhaleSkyWars.getInstance(), () -> {
            player.setAllowFlight(false);
            player.setFlying(false);
        }, 3L);
        player.setFallDistance(0.0F);
        removeEffects(player);
    }

    public static void setPlayerVelocity(Player player, Vector velocity) {
        player.setVelocity(velocity);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.getVelocity().add(new Vector(0, -2.2F, 0));
            }
        }.runTaskLater(WhaleSkyWars.getInstance(), 20L);
    }

    public static boolean isPlayerWithinRadius(Player player, Location center, double radius) {
        Location playerLocation = player.getLocation();
        double distanceSquared = center.distanceSquared(playerLocation);
        return distanceSquared <= radius * radius;
    }

    public static void removeEffects(Player player) {
        player.getActivePotionEffects()
                .forEach(effect -> player.removePotionEffect(effect.getType()));
    }

    public static void clearInventory(Player player) {
        if (player == null) return;
        player.closeInventory();
        PlayerInventory inventory = player.getInventory();
        inventory.setArmorContents(null);
        inventory.clear();
    }

    public static boolean hasPermissions(Player player, String... permissions) {
        for (val perm : permissions) {
            if (player.hasPermission(perm)) return true;
        }
        return false;
    }

}
