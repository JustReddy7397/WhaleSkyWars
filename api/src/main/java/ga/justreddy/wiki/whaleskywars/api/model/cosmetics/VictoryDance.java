package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * @author JustReddy
 */
public abstract class VictoryDance extends Cosmetic implements Cloneable {

    /**
     * Initializes a new instance of the VictoryDance class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the victory dance.
     * @param id The unique identifier of the victory dance.
     * @param cost The cost of the victory dance.
     */
    public VictoryDance(String name, int id, int cost) {
        super(name, id, cost);
    }

    public abstract void start(IGamePlayer player);

    public abstract void stop(IGamePlayer player);

    public abstract VictoryDance clone();

}


