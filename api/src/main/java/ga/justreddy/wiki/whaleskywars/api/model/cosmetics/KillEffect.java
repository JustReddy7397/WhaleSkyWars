package ga.justreddy.wiki.whaleskywars.api.model.cosmetics;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * @author JustReddy
 */
public abstract class KillEffect extends Cosmetic {
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
    public abstract void onKill(@NotNull IGamePlayer killer, @NotNull IGamePlayer victim);

}

