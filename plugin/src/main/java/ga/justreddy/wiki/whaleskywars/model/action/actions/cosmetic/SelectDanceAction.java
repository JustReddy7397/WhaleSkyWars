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
        return "[SELECT_DANCE]";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        int id = -1;
        try {
            id = Integer.parseInt(data);
        }catch (NumberFormatException ex) {
            TextUtil.error(null, "Invalid dance id: " + data, false);
            return;
        }
        if (id == -1) {
            TextUtil.error(null, "Invalid dance id: " + data, false);
            return;
        }

        boolean exists = plugin.getVictoryDanceManager().exists(id);

        if (!exists) {
            TextUtil.error(null, "Dance not found: " + id, false);
            return;
        }

        player.getCosmetics().setSelectedVictoryDance(id);

    }
}
