package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;

/**
 * @author JustReddy
 */
public class WaitingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.STARTING);
    }

    @Override
    public void onTick(IGame game) {

        if (game.getPlayerCount() >= game.getMinimumPlayers()) {
            game.goToNextPhase();
        }

    }

    @Override
    public IPhase getNextPhase() {
        return new StartingPhase();
    }
}
