package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameSpawn;

/**
 * @author JustReddy
 */
public class PlayingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {
        game.setGameState(GameState.PLAYING);

        game.getTeams().forEach(team -> {
            IGameSpawn spawn = team.getGameSpawn();
            if (spawn.getCage() == null) return;
            spawn.getCage().remove(team.getSpawnLocation());
        });

    }

    @Override
    public void onTick(IGame game) {

    }

    @Override
    public IPhase getNextPhase() {
        return new EndingPhase();
    }
}
