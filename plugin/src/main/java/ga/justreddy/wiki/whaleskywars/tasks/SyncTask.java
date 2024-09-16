package ga.justreddy.wiki.whaleskywars.tasks;

import com.grinderwolf.swm.internal.com.zaxxer.hikari.HikariJNDIFactory;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.manager.GameManager;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;

/**
 * @author JustReddy
 */
public class SyncTask implements Runnable {

    private final GameManager gameManager;

    public SyncTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (WhaleSkyWars.getInstance().getServerMode() == ServerMode.LOBBY) return;
        gameManager.getGames().forEach(IGame::onCountDown);
    }
}
