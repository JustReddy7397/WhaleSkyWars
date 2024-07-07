package ga.justreddy.wiki.whaleskywars.listeners;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author JustReddy
 */
public class MainListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        IGamePlayer player = WhaleSkyWars.getInstance().getPlayerManager().add(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        player.sendMessage("&bWelcome to WhaleSkyWars!");
        WhaleSkyWars.getInstance().getSkyWarsBoard().setLobbyBoard(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        IGamePlayer player = WhaleSkyWars.getInstance().getPlayerManager().get(event.getPlayer().getUniqueId());
        WhaleSkyWars.getInstance().getSkyWarsBoard().removeScoreboard(player);
        WhaleSkyWars.getInstance().getPlayerManager().remove(event.getPlayer().getUniqueId());

    }

}
