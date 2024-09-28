package ga.justreddy.wiki.whaleskywars.tasks;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.manager.GameManager;
import ga.justreddy.wiki.whaleskywars.manager.SignManager;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;

/**
 * @author JustReddy
 */
public class SyncTask implements Runnable {

    private final GameManager gameManager;
    private final SignManager signManager;

    public SyncTask(GameManager gameManager, SignManager signManager) {
        this.gameManager = gameManager;
        this.signManager = signManager;
    }

    @Override
    public void run() {
        ServerMode mode = WhaleSkyWars.getInstance().getServerMode();
        if (mode != ServerMode.BUNGEE) {
            signManager.copyOf().forEach(sign -> {
                if (mode == ServerMode.LOBBY) {
                    BungeeGame game = gameManager.getByGameName(sign.getId());
                    sign.updateBungee(game);
                } else {
                    IGame game = gameManager.getGameByName(sign.getId());
                    sign.update(game);
                }
            });
        }

        if (mode == ServerMode.LOBBY) return;
        gameManager.getGames().forEach(IGame::onCountDown);
    }
}
