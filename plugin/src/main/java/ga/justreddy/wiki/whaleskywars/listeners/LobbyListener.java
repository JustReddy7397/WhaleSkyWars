package ga.justreddy.wiki.whaleskywars.listeners;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.manager.cache.CacheManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author JustReddy
 */
public class LobbyListener implements Listener {

    private final CacheManager cacheManager;

    public LobbyListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!inLobbyWorld(player)) return;
        if (cacheManager.isBuilding(player.getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!inLobbyWorld(player)) return;
        if (cacheManager.isBuilding(player.getUniqueId())) return;
        event.setCancelled(true);
    }


    private boolean inLobbyWorld(Player player) {
        Location lobby =  WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(player.getWorld());
    }

    private boolean inLobbyWorld(Entity player) {
        Location lobby =  WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(player.getWorld());
    }

    private boolean inLobbyWorld(World world) {
        Location lobby =  WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(world);
    }

    private boolean teleportBetweenWorlds(World to, World from) {
        Location lobby =  WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return to.equals(from);
    }

    private boolean teleportToLobby(World to) {
        Location lobby =  WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(to);
    }

}
