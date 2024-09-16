package ga.justreddy.wiki.whaleskywars.model.action.actions;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import org.bukkit.entity.Player;

/**
 * @author JustReddy
 */
public class CloseInventoryAction implements IAction {
    @Override
    public String getIdentifier() {
        return "[CLOSE]";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        player.getPlayer().ifPresent(Player::closeInventory);
    }
}
