package ga.justreddy.wiki.whaleskywars.api.events;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;

/**
 * @author JustReddy
 */
public class SkyWarsGameStateChangeEvent extends SkyWarsEvent {

    private final IGame game;
    private final GameState previousState;
    private final GameState newState;

    public SkyWarsGameStateChangeEvent(IGame game, GameState previousState, GameState newState) {
        this.game = game;
        this.previousState = previousState;
        this.newState = newState;
    }

    public IGame getGame() {
        return game;
    }

    public GameState getPreviousState() {
        return previousState;
    }

    public GameState getNewState() {
        return newState;
    }

}
