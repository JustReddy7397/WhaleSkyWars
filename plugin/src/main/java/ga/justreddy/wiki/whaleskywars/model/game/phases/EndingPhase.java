package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;

/**
 * @author JustReddy
 */
public class EndingPhase implements IPhase {

    private final IGameTeam winnerTeam;

    public EndingPhase(IGameTeam winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

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
