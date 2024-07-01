package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameSpawn;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import ga.justreddy.wiki.whaleskywars.model.game.Game;

/**
 * @author JustReddy
 */
public class PreGamePhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.PREGAME);
        game.getPreGameTimer().start();

        game.assignTeams();

        if (game.getWaitingSpawn() != null) {
            game.getWaitingCuboid().clear();

            game.getTeams().forEach(team -> {
                IGameSpawn gameSpawn = team.getGameSpawn();
                if (gameSpawn.isUsed()) return;
                gameSpawn.setUsed(true);

                int cageId = 0; // TODO

            });

        }


    }

    @Override
    public void onTick(IGame game) {

        AbstractTimer timer = game.getPreGameTimer();

        if (game.getPlayerCount() <= 0) {
            timer.stop();
            ((Game) game).getPhaseHandler().setPhase(new RestartingPhase());
            return;
        }

        if (timer.getTicksExceed() <= 0) {
            timer.stop();
            game.goToNextPhase();
        }
    }

    @Override
    public IPhase getNextPhase() {
        return new PlayingPhase();
    }
}
