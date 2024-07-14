package ga.justreddy.wiki.whaleskywars.model.kits.listener;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.KitRequest;
import ga.justreddy.wiki.whaleskywars.model.kits.requests.requests.KitLayoutRequest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author JustReddy
 */
public class KitRequestListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        IGamePlayer player = WhaleSkyWars.getInstance().getPlayerManager().get(event.getPlayer().getUniqueId());
        KitRequest request = WhaleSkyWars.getInstance().getKitRequestManager().getKitRequest(player);
        if (request == null) return;
        if (request instanceof KitLayoutRequest) event.setCancelled(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        IGamePlayer player = WhaleSkyWars.getInstance().getPlayerManager().get(event.getPlayer().getUniqueId());
        KitRequest request = WhaleSkyWars.getInstance().getKitRequestManager().getKitRequest(player);
        if (request == null) return;
        WhaleSkyWars.getInstance().getKitRequestManager().removeKitRequest(player);
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setArmorContents(null);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        IGamePlayer player = WhaleSkyWars.getInstance().getPlayerManager().get(event.getEntity().getUniqueId());
        KitRequest request = WhaleSkyWars.getInstance().getKitRequestManager().getKitRequest(player);
        if (request == null) return;
        WhaleSkyWars.getInstance().getKitRequestManager().removeKitRequest(player);
        event.getEntity().getInventory().clear();
        event.getEntity().getInventory().setArmorContents(null);
    }
}
