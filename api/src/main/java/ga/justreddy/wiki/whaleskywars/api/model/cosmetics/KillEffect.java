package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * @author JustReddy
 */
public abstract class KillEffect extends Cosmetic implements Cloneable {
    /**
     * Initializes a new instance of the KillEffect class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the victory dance.
     * @param id   The unique identifier of the victory dance.
     * @param cost The cost of the victory dance.
     */
    public KillEffect(String name, int id, int cost) {
        super(name, id, cost);
    }

    /**
     * Called when a player kills another player.
     * @param killer The player who killed the other player.
     * @param victim The player who was killed.
     */
    public abstract void onKill(IGamePlayer killer, IGamePlayer victim);

    /**
     * Clones the instance of the kill effect.
     * @return The cloned instance of the kill effect.
     */
    public abstract KillEffect clone();


}

