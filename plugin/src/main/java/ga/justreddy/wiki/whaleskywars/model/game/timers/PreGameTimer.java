package ga.justreddy.wiki.whaleskywars.model.game.timers;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import ga.justreddy.wiki.whaleskywars.model.Messages;

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
            game.sendMessage(game.getPlayers(), Messages.GAME_STARTING.toString()
                    .replace("<seconds>", String.valueOf(ticksExceed)));
        }
        game.getPlayers()
                .forEach(WhaleSkyWars.getInstance().getSkyWarsBoard()
                        ::updateGameScoreboard);
    }

    @Override
    protected void onEnd() {
    }
}
