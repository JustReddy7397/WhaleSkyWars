package ga.justreddy.wiki.whaleskywars.model.action.actions.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

/**
 * @author JustReddy
 */
public class SelectKillMessageAction implements IAction {
    @Override
    public String getIdentifier() {
        return "SELECT_KILL_MESSAGE";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        int id;
        try {
            id = Integer.parseInt(data);
        } catch (NumberFormatException ex) {
            TextUtil.error(null, "Invalid kill message id: " + data, false);
            return;
        }

        boolean exists = plugin.getKillMessageManager().exists(id);

        if (!exists) {
            TextUtil.error(null, "Kill message not found: " + id, false);
            return;
        }

        if (!player.getCosmetics().hasCachedKillMessage(id)) {
            player.sendMessage(plugin
                    .getMessagesConfig()
                    .getString("error.cosmetics.not-unlocked"));
            return;
        }

        player.getCosmetics().setSelectedKillMessage(id);
        player.sendMessage(plugin
                .getMessagesConfig()
                .getString("general.cosmetics.selected")
                .replaceAll("<cosmetic>", plugin.getKillMessageManager().of(id).getName()));
    }
}
