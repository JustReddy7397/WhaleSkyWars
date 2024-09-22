package ga.justreddy.wiki.whaleskywars.model.action.actions;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;

/**
 * @author JustReddy
 */
public class ConsoleCommandAction implements IAction {
    @Override
    public String getIdentifier() {
        return "CONSOLE";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                data.replaceAll("<player>", player.getName()));
    }
}
