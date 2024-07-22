package ga.justreddy.wiki.whaleskywars.tasks;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.manager.GameManager;

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
        gameManager.getGames().forEach(IGame::onCountDown);
    }
}
