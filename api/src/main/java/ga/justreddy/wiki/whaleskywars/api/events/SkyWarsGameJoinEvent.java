package ga.justreddy.wiki.whaleskywars.api.events;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.bukkit.event.Cancellable;

/**
 * @author JustReddy
 */
public class SkyWarsGameJoinEvent extends SkyWarsEvent implements Cancellable {

    private final IGamePlayer player;
    private final IGame game;

    private boolean cancelled = false;

    public SkyWarsGameJoinEvent(IGamePlayer player, IGame game) {
        this.player = player;
        this.game = game;
    }

    public IGamePlayer getPlayer() {
        return player;
    }

    public IGame getGame() {
        return game;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
