package ga.justreddy.wiki.whaleskywars.model.action.actions.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

/**
 * @author JustReddy
 */
public class SelectDanceAction implements IAction {

    @Override
    public String getIdentifier() {
        return "SELECT_DANCE";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        int id;
        try {
            id = Integer.parseInt(data);
        }catch (NumberFormatException ex) {
            TextUtil.error(null, "Invalid dance id: " + data, false);
            return;
        }

        boolean exists = plugin.getVictoryDanceManager().exists(id);

        if (!exists) {
            TextUtil.error(null, "Dance not found: " + id, false);
            return;
        }

        if (!player.getCosmetics().hasCachedVictoryDance(id)) {
            player.sendMessage(plugin
                    .getMessagesConfig()
                    .getString("error.cosmetics.not-unlocked"));
            return;
        }

        player.getCosmetics().setSelectedVictoryDance(id);
        player.sendMessage(plugin
                .getMessagesConfig()
                .getString("general.cosmetics.selected")
                .replaceAll("<cosmetic>", plugin.getVictoryDanceManager().copyOf(id).getName()));

    }
}
