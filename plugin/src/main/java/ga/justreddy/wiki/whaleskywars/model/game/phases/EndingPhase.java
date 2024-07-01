package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;

/**
 * @author JustReddy
 */
public class EndingPhase implements IPhase {

    @Override
    public void onEnable(IGame game) {

    }

    @Override
    public void onTick(IGame game) {

    }

    @Override
    public IPhase getNextPhase() {
        return new RestartingPhase();
    }
}
