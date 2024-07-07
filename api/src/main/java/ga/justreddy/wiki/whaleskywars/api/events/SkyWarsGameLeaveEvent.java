package ga.justreddy.wiki.whaleskywars.api.events;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;

/**
 * @author JustReddy
 */
public class SkyWarsGameLeaveEvent extends SkyWarsEvent {

    private final IGamePlayer player;
    private final IGame game;

    public SkyWarsGameLeaveEvent(IGamePlayer player, IGame game) {
        this.player = player;
        this.game = game;
    }

    public IGamePlayer getPlayer() {
        return player;
    }

    public IGame getGame() {
        return game;
    }

}
