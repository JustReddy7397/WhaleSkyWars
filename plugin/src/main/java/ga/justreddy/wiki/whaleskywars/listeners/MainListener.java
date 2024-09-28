package ga.justreddy.wiki.whaleskywars.listeners;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSign;
import ga.justreddy.wiki.whaleskywars.model.Messages;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.model.game.GameSign;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.conversations.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * @author JustReddy
 */
public class MainListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        IGamePlayer player = WhaleSkyWars.getInstance().getPlayerManager().add(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
            if (!WhaleSkyWars.getInstance().getStorage().doesPlayerExist(player)) {
                WhaleSkyWars.getInstance().getStorage().createPlayer(player);
            } else {
                WhaleSkyWars.getInstance().getStorage().loadPlayer(player.getUniqueId());
            }
        });
        Player bukkitPlayer = event.getPlayer();
        if (WhaleSkyWars.getInstance().getServerMode() != ServerMode.BUNGEE
                && WhaleSkyWars.getInstance().getSpawn() != null) {
            bukkitPlayer.teleport(WhaleSkyWars.getInstance().getSpawn());
        }
        if (inLobbyWorld(bukkitPlayer)) {
            WhaleSkyWars.getInstance().getSkyWarsBoard().setLobbyBoard(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        GamePlayer player = (GamePlayer) WhaleSkyWars.getInstance().getPlayerManager().get(event.getPlayer().getUniqueId());
        WhaleSkyWars.getInstance().getSkyWarsBoard().removeScoreboard(player);
        player.saveAndRemove();
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
        Location lobby = WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(player.getWorld());
    }

    private boolean inLobbyWorld(Entity player) {
        Location lobby = WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(player.getWorld());
    }

    private boolean inLobbyWorld(World world) {
        Location lobby = WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(world);
    }

    private boolean teleportBetweenWorlds(World to, World from) {
        Location lobby = WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return to.equals(from);
    }

    private boolean teleportToLobby(World to) {
        Location lobby = WhaleSkyWars.getInstance().getSpawn();
        if (lobby == null) return false;
        return lobby.getWorld().equals(to);
    }


}
