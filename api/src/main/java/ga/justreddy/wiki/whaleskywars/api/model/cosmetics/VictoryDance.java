package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import org.jetbrains.annotations.NotNull;

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

    /**
     * Called when the game is starting the victory dance.
     * @param player The player of the victory dance.
     */
    public abstract void start(@NotNull IGamePlayer player);

    /**
     * Called when the game stops the victory dance.
     * @param player The player of the victory dance.
     */
    public abstract void stop(@NotNull IGamePlayer player);

    /**
     * Clones the instance of the victory dance.
     * @return The cloned instance of the victory dance.
     */
    public abstract VictoryDance clone();

}


