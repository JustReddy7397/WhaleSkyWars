package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;

/**
 * @author JustReddy
 */
public abstract class VictoryDance extends Cosmetic {

    public VictoryDance(String name, int id, int cost) {
        super(name, id, cost);
    }

    public abstract void start(IGamePlayer player);

    public abstract void stop(IGamePlayer player);

}
