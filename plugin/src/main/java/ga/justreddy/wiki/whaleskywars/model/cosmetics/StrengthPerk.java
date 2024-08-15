package ga.justreddy.wiki.whaleskywars.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.api.SkyWarsAPI;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameKillEvent;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk.Perk;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author JustReddy
 */
public class StrengthPerk extends Perk {

    /**
     * Initializes a new instance of the Perk class with the specified name,
     * ID, cost, and maximum level.
     * It also registers the perk as an event listener.
     *
     * @param name     The name of the perk.
     * @param id       The unique identifier of the perk.
     * @param cost     The cost of the perk.
     * @param maxLevel The maximum level the perk can reach.
     */
    public StrengthPerk(String name, int id, int cost, int maxLevel) {
        super(name, id, cost, maxLevel);
    }

    // Using WhaleSkyWars events
    @EventHandler
    public void onSkyWarsPlayerKill(SkyWarsGameKillEvent event) {
        IGamePlayer killer = event.getKiller();
        if (canActivate(killer)) {
             killer.getPlayer().ifPresent(player -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
                        20 * 10, 1));
             });
        }
    }

    // Using Bukkit events
    @EventHandler
    public void onSkyWarsPlayerKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        // Get the IGamePlayer object using the killers UUID
        IGamePlayer player = SkyWarsProvider
                .get().getPlayer(killer.getUniqueId());
        if (player == null) return;
        if (canActivate(player)) {
            killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
                    20 * 10, 1));
        }
    }

}
