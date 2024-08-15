package ga.justreddy.wiki.whaleskywars.model.game.timers;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;

import java.util.AbstractMap;

/**
 * @author JustReddy
 */
public class PreGameTimer extends AbstractTimer {

    public PreGameTimer(int seconds, IGame game) {
        super(WhaleSkyWars.getInstance(), seconds, game);
    }

    @Override
    protected void onTick() {
        if (ticksExceed <= 5) {
            game.sendMessage(game.getAlivePlayers(), "The game will start in " + ticksExceed + " seconds");
        }
    }

    @Override
    protected void onEnd() {}
}
