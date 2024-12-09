package ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk;

import ga.justreddy.wiki.whaleskywars.api.SkyWarsAPI;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Cosmetic;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * Represents a perk in the game,
 * which can be activated under certain conditions.
 * Each perk has a level and a maximum level
 * This class is abstract and should be extended to create specific perks.
 * It also registers itself as an event listener.
 *
 * @see Cosmetic
 *
 * @author JustReddy
 */
public abstract class Perk extends Cosmetic implements Listener {

    private int level;
    private final int maxLevel;

    /**
     * Initializes a new instance of the Perk class with the specified name,
     * ID, cost, and maximum level.
     * It also registers the perk as an event listener.
     *
     * @param name The name of the perk.
     * @param id The unique identifier of the perk.
     * @param cost The cost of the perk.
     * @param maxLevel The maximum level the perk can reach.
     */
    public Perk(String name, int id, int cost, int maxLevel) {
        super(name, id, cost);
        this.level = 1;
        this.maxLevel = maxLevel;
        Bukkit.getPluginManager().registerEvents(this, SkyWarsProvider.get().getPlugin());
    }

    /**
     * Returns the current level of the perk.
     *
     * @return The current level of the perk.
     */
    public final int getLevel() {
        return level;
    }

    /**
     * Returns the maximum level of the perk.
     *
     * @return The maximum level of the perk.
     */
    public final int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Sets the current level of the perk.
     *
     * @param level The new level to set for the perk.
     */
    public final void setLevel(int level) {
        this.level = level;
    }

    /**
     * Checks if the perk can be activated for the specified player.
     * This method is protected and final,
     * meaning it cannot be overridden by subclasses.
     *
     * @param player The player for whom to check if the perk can be activated.
     * @return true if the perk can be activated for the player, false otherwise.
     */
    protected final boolean canActivate(IGamePlayer player) {
        return player != null && player.getCosmetics().getSelectedPerkId() == getId();
    }

}
