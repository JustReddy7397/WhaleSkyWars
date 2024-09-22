package ga.justreddy.wiki.whaleskywars.api.events;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;

/**
 * @author JustReddy
 */
public class SkyWarsGameEndEvent extends SkyWarsEvent {

    private final IGame game;
    private final IGameTeam team;

    public SkyWarsGameEndEvent(IGame game, IGameTeam team) {
        this.game = game;
        this.team = team;
    }

    public IGame getGame() {
        return game;
    }

    public IGameTeam getTeam() {
        return team;
    }

}
