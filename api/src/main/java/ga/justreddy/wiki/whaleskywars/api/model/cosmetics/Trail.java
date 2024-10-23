package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

import org.bukkit.entity.Projectile;

/**
 * @author JustReddy
 */
public abstract class Trail extends Cosmetic {

    /**
     * Initializes a new instance of the Cosmetic class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the cosmetic.
     * @param id   The unique identifier of the cosmetic.
     * @param cost The cost of the cosmetic.
     */
    public Trail(String name, int id, int cost) {
        super(name, id, cost);
    }

    public abstract void summon(Projectile projectile);

}
