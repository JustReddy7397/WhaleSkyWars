package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import ga.justreddy.wiki.whaleskywars.model.game.Game;

/**
 * @author JustReddy
 */
public class StartingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.STARTING);
        game.getStartingTimer().start();
    }

    @Override
    public void onTick(IGame game) {

        AbstractTimer startingTimer = game.getStartingTimer();

        if (game.getPlayerCount() < game.getMinimumPlayers()) {
            ((Game) game).getPhaseHandler().setPhase(new WaitingPhase());
            // TODO bungeecord support
            startingTimer.stop();
            return;
        }

        if (startingTimer.getTicksExceed() <= 0) {
            ((Game) game).getPhaseHandler().nextPhase();
            startingTimer.stop();
        }

    }

    @Override
    public IPhase getNextPhase() {
        return new PreGamePhase();
    }
}
