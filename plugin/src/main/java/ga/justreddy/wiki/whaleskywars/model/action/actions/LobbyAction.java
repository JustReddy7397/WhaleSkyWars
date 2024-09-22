package ga.justreddy.wiki.whaleskywars.model.action.actions;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.support.BungeeUtil;

/**
 * @author JustReddy
 */
public class LobbyAction implements IAction {
    @Override
    public String getIdentifier() {
        return "LOBBY";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        if (plugin.getServerMode() == ServerMode.BUNGEE) {
            BungeeUtil.sendBackToServer(player);
            return;
        }
        IGame game = player.getGame();
        if (game != null) {
            game.onGamePlayerLeave(player, !game.isGameState(GameState.STARTING) || !game.isGameState(GameState.WAITING), false);
        }
        player.getPlayer().ifPresent(bukkitPlayer -> bukkitPlayer.teleport(WhaleSkyWars.getInstance().getSpawn()));
    }
}
