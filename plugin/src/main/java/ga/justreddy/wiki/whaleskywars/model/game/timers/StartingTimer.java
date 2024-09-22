package ga.justreddy.wiki.whaleskywars.model.game.timers;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import ga.justreddy.wiki.whaleskywars.util.NumberUtil;
import org.bukkit.Bukkit;

/**
 * @author JustReddy
 */
public class StartingTimer extends AbstractTimer {

    public StartingTimer(int seconds, IGame game) {
        super(WhaleSkyWars.getInstance(), seconds, game);
    }

    @Override
    protected void onTick() {
        game.getPlayers()
                .forEach(WhaleSkyWars.getInstance().getSkyWarsBoard()
                        ::updateGameScoreboard);
    }

    @Override
    protected void onEnd() {

    }
}
