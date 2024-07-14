package ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk;

import ga.justreddy.wiki.whaleskywars.api.SkyWarsAPI;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.Cosmetic;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * @author JustReddy
 */
public abstract class Perk extends Cosmetic implements Listener {

    private int level;
    private final int maxLevel;

    public Perk(String name, int id, int cost, int maxLevel) {
        super(name, id, cost);
        this.level = 0;
        this.maxLevel = maxLevel;
        Bukkit.getPluginManager().registerEvents(this, SkyWarsProvider.get().getPlugin());
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
