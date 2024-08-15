package ga.justreddy.wiki.whaleskywars.listeners;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
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

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if (WhaleSkyWars.getInstance().getSpawn() == null) return;
        World to = event.getPlayer().getWorld();
        if (teleportToLobby(to)) {
            event.getPlayer().teleport(WhaleSkyWars.getInstance().getSpawn());
            IGamePlayer player = GamePlayer.get(event.getPlayer().getUniqueId());
            Bukkit.getScheduler().runTaskLater(WhaleSkyWars.getInstance(), () -> {
                WhaleSkyWars.getInstance().getSkyWarsBoard().removeScoreboard(player);
                WhaleSkyWars.getInstance().getSkyWarsBoard().setLobbyBoard(player);
            }, 1L);
        }
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
