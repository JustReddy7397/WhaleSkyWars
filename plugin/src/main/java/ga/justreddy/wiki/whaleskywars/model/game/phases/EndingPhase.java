package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;

/**
 * @author JustReddy
 */
public class EndingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.ENDING);
        game.getEndingTimer().start();
        // TODO
        game.sendMessage(game.getPlayers(), "Thanks for playing bitches");
    }

    @Override
    public void onTick(IGame game) {
        if (game.getEndingTimer().getTicksExceed() <= 0) {
            game.goToNextPhase();
            return;
        }
    }

    @Override
    public IPhase getNextPhase() {
        return new RestartingPhase();
    }
}
