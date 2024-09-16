package ga.justreddy.wiki.whaleskywars.model.action.actions;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.model.menu.Menu;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

/**
 * @author JustReddy
 */
public class OpenInventoryAction implements IAction {
    @Override
    public String getIdentifier() {
        return "[INVENTORY]";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {

        Menu menu = plugin.getMenuManager().of(data);

        if (menu == null) {
            TextUtil.error(null, "Menu not found: " + data, false);
            return;
        }

        menu.open(player);


    }
}
