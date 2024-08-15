package ga.justreddy.wiki.whaleskywars.model.game.timers;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;

/**
 * @author JustReddy
 */
public class EndingTimer extends AbstractTimer {

    public EndingTimer(int seconds, IGame game) {
        super(WhaleSkyWars.getInstance(), seconds, game);
    }

    @Override
    protected void onTick() {

    }

    @Override
    protected void onEnd() {

    }
}
