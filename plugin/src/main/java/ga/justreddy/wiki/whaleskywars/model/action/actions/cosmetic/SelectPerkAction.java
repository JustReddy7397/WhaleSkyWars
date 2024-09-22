package ga.justreddy.wiki.whaleskywars.model.action.actions.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk.Perk;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

/**
 * @author JustReddy
 */
public class SelectPerkAction implements IAction {
    @Override
    public String getIdentifier() {
        return "SELECT_PERK";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        int id = -1;
        try {
            id = Integer.parseInt(data);
        } catch (NumberFormatException ex) {
            TextUtil.error(null, "Invalid perk id: " + data, false);
            return;
        }
        if (id == -1) {
            TextUtil.error(null, "Invalid perk id: " + data, false);
            return;
        }

        Perk perk = plugin.getPerkManager().of(id);

        if (perk == null) {
            TextUtil.error(null, "Perk not found: " + id, false);
            return;
        }

        player.getCosmetics().setSelectedPerk(id);

    }
}
