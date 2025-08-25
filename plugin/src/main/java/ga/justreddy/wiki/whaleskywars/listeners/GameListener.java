package ga.justreddy.wiki.whaleskywars.listeners;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Trail;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.KillPath;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Trails;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author JustReddy
 */
public class GameListener implements Listener {

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        for (IGame game : WhaleSkyWars.getInstance().getGameManager().getGames()) {
            if (game.getWorld().equals(event.getBlock().getWorld()) && !game.isGameState(GameState.PLAYING)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        IGamePlayer player = GamePlayer.get(event.getPlayer().getUniqueId());
        if (player == null) return;
        if (!player.isPlaying()) return;
        IGame game = player.getGame();
        if (!game.isGameState(GameState.PLAYING)) {
            event.setCancelled(true);
            return;
        }

        player.getStats().addBlocksBroken(1);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        IGamePlayer player = GamePlayer.get(event.getPlayer().getUniqueId());
        if (player == null) return;
        if (!player.isPlaying()) return;
        IGame game = player.getGame();
        if (!game.isGameState(GameState.PLAYING)) {
            event.setCancelled(true);
            return;
        }

        player.getStats().addBlocksPlaced(1);

    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        IGamePlayer gamePlayer = GamePlayer.get(player.getUniqueId());
        if (gamePlayer == null) return;
        if (!gamePlayer.isPlaying()) return;
        IGame game = gamePlayer.getGame();
        boolean isFoodEnabled = WhaleSkyWars.getInstance().getSettingsConfig()
                .getBoolean("game-options.food");
        if (!game.isGameState(GameState.PLAYING) || (
                !isFoodEnabled && game.isGameState(GameState.PLAYING)
                )) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile instanceof Arrow || projectile instanceof Snowball || projectile instanceof Egg || projectile instanceof FishHook || projectile instanceof ThrownPotion || projectile instanceof ThrownExpBottle) {
            if (!(projectile.getShooter() instanceof Player)) return;
            Player player = (Player) projectile.getShooter();
            IGamePlayer gamePlayer = GamePlayer.get(player.getUniqueId());
            if (gamePlayer == null) return;
            if (!gamePlayer.isPlaying()) return;
            if (gamePlayer.isDead()) return;
            gamePlayer.getStats().addArrowsShot(1);
            // TODO
            Trail trails = gamePlayer.getCosmetics().getSelectedTrail();
            if (trails == null) return;
            trails.summon(projectile);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        IGamePlayer gamePlayer = GamePlayer.get(player.getUniqueId());
        if (gamePlayer == null) return;
        if (!gamePlayer.isPlaying()) return;
        IGame game = gamePlayer.getGame();
        if (game.isGameState(GameState.PLAYING)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        IGamePlayer gamePlayer = GamePlayer.get(player.getUniqueId());
        if (gamePlayer == null) return;
        if (!gamePlayer.isPlaying()) return;
        if (gamePlayer.isDead()) return;
        IGamePlayer target = null;
        Projectile projectile = null;

        if (event.getDamager() instanceof Player) {
            target = GamePlayer.get(event.getDamager().getUniqueId());
        } else if (event.getDamager() instanceof Projectile) {
            projectile = (Projectile) event.getDamager();
            if (!(projectile.getShooter() instanceof Player)) return;
            target = GamePlayer.get(((Player) projectile.getShooter()).getUniqueId());
        }

        if (target != null &&
                target.getGameTeam() != null &&
                target.getGameTeam().hasPlayer(gamePlayer)) {
            event.setCancelled(true);
            return;
        }

        if (projectile != null && target != null) {
            target.getStats().addArrowsHit(1);
        }

        if (target == null) return;
        if (!target.isPlaying()) return;
        if (target.isDead()) return;
        if (event.getCause() == null) return;
        gamePlayer.getCombatLog().setTarget(target);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDroppedExp(0);
        event.setDeathMessage(null);
        Player death = event.getEntity();
        if (death == null) return;
        IGamePlayer deathPlayer = GamePlayer.get(death.getUniqueId());
        if (deathPlayer == null) return;
        if (!deathPlayer.isPlaying()) return;
        IGame game = deathPlayer.getGame();

        Player killer = death.getKiller();
        IGamePlayer killerPlayer = null;
        if (killer != null) {
            killerPlayer = GamePlayer.get(killer.getUniqueId());
        } else {
            if (deathPlayer.getCombatLog().hasTarget() && deathPlayer.getCombatLog().isTimeCorrect()) {
                killerPlayer = deathPlayer.getCombatLog().getTarget();
            }
        }
        EntityDamageEvent.DamageCause damageCause = death.getPlayer().getLastDamageCause().getCause();
        KillPath path;
        switch (damageCause) {
            case VOID:
                path = KillPath.VOID;
                break;
            case FALL:
                path = KillPath.FALL;
                if (deathPlayer.getPlayer().get().getLocation().getBlockY() <= game.getGameCuboid().getLowY()) {
                    path = KillPath.VOID;
                }
                break;
            case FIRE_TICK:
            case LAVA:
                path = KillPath.FIRE;
                break;
            case ENTITY_ATTACK:
                path = KillPath.MELEE;
                break;
            case BLOCK_EXPLOSION:
                path = KillPath.EXPLOSION;
                break;
            case DROWNING:
                path = KillPath.DROWNING;
                break;
            case SUFFOCATION:
                path = KillPath.SUFFOCATION;
                break;
            case PROJECTILE:
                path = KillPath.PROJECTILE;
                if (deathPlayer.getPlayer().get().getLocation().getBlockY() <= game.getGameCuboid().getLowY()) {
                    path = KillPath.VOID;
                }
                break;
            default:
                path = KillPath.UNKNOWN;
                break;
        }
        if (killerPlayer == null) {
            game.onGamePlayerDeath(killerPlayer, deathPlayer, path);
        } else {
            game.getKills().put(killerPlayer, game.getKills().get(killerPlayer) + 1);
            killerPlayer.getStats().addKill(((Game)game).isTeamGame());
            game.onGamePlayerDeath(killerPlayer, deathPlayer, path);
        }

    }
    

}
