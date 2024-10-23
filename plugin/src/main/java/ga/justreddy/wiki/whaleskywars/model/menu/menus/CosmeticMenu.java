package ga.justreddy.wiki.whaleskywars.model.menu.menus;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillEffect;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillMessage;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.perk.Perk;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.entity.data.IPlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.model.config.toml.ConfigurationSection;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Balloon;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Cage;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Trails;
import ga.justreddy.wiki.whaleskywars.model.entity.data.PlayerCosmetics;
import ga.justreddy.wiki.whaleskywars.model.menu.Menu;
import ga.justreddy.wiki.whaleskywars.util.ItemStackBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * @author JustReddy
 */
public class CosmeticMenu extends Menu {

    private final WhaleSkyWars plugin = WhaleSkyWars.getInstance();

    public CosmeticMenu(TempConfig config) {
        super(config);
    }

    @Override
    public String setPlaceholders(String text, IGamePlayer player) {

        IPlayerCosmetics cosmetics = player.getCosmetics();

        if (WhaleSkyWars.getInstance().isHooked("PlaceholderAPI") && player.getPlayer().isPresent()) {
            text = PlaceholderAPI.setPlaceholders(player.getPlayer().get(), text);
        }

        for (Cage cage : plugin.getCageManager().getData().values()) {
            int id = cage.getId();
            if (cosmetics.getSelectedCageId() == id) {
                text = text.replaceAll("<wsw-state-cage-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.selected"));
            } else if (cosmetics.hasCachedCage(id) && cosmetics.getSelectedCageId() != id) {
                text = text.replaceAll("<wsw-state-cage-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.not-selected"));
            } else {
                text = text.replaceAll("<wsw-state-cage-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.buy").replaceAll("<price>", String.valueOf(cage.getCost())));
            }
        }

        for (VictoryDance dance : plugin.getVictoryDanceManager().getDances().values()) {
            int id = dance.getId();
            if (cosmetics.getSelectedCageId() == id) {
                text = text.replaceAll("<wsw-state-dance-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.selected"));
            } else if (cosmetics.hasCachedCage(id) && cosmetics.getSelectedCageId() != id) {
                text = text.replaceAll("<wsw-state-dance-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.not-selected"));
            } else {
                text = text.replaceAll("<wsw-state-dance-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.buy").replaceAll("<price>", String.valueOf(dance.getCost())));
            }
        }

        for (KillMessage message : plugin.getKillMessageManager().getKillMessages().values()) {
            int id = message.getId();
            if (cosmetics.getSelectedCageId() == id) {
                text = text.replaceAll("<wsw-state-message-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.selected"));
            } else if (cosmetics.hasCachedCage(id) && cosmetics.getSelectedCageId() != id) {
                text = text.replaceAll("<wsw-state-message-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.not-selected"));
            } else {
                text = text.replaceAll("<wsw-state-message-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.buy").replaceAll("<price>", String.valueOf(message.getCost())));
            }
        }

        for (KillEffect effect : plugin.getKillEffectManager().getKillEffects().values()) {
            int id = effect.getId();
            if (cosmetics.getSelectedCageId() == id) {
                text = text.replaceAll("<wsw-state-effect-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.selected"));
            } else if (cosmetics.hasCachedCage(id) && cosmetics.getSelectedCageId() != id) {
                text = text.replaceAll("<wsw-state-effect-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.not-selected"));
            } else {
                text = text.replaceAll("<wsw-state-effect-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.buy").replaceAll("<price>",
                                String.valueOf(effect.getCost())));
            }
        }

        for (Perk perk : plugin.getPerkManager().getPerks().values()) {
            int id = perk.getId();
            if (cosmetics.getSelectedCageId() == id) {
                text = text.replaceAll("<wsw-state-perk-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.selected"));
            } else if (cosmetics.hasCachedCage(id) && cosmetics.getSelectedCageId() != id) {
                text = text.replaceAll("<wsw-state-perk-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.not-selected"));
            } else {
                text = text.replaceAll("<wsw-state-perk-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.buy").replaceAll("<price>",
                                String.valueOf(perk.getCost())));
            }
        }

        for (Balloon balloon : plugin.getBalloonManager().getBalloons().values()) {
            int id = balloon.getId();
            if (cosmetics.getSelectedCageId() == id) {
                text = text.replaceAll("<wsw-state-balloon-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.selected"));
            } else if (cosmetics.hasCachedCage(id) && cosmetics.getSelectedCageId() != id) {
                text = text.replaceAll("<wsw-state-balloon-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.not-selected"));
            } else {
                text = text.replaceAll("<wsw-state-balloon-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.buy").replaceAll("<price>",
                                String.valueOf(balloon.getCost())));
            }
        }

        for (Trails trail : plugin.getTrailManager().getTrails().values()) {
            int id = trail.getId();
            if (cosmetics.getSelectedCageId() == id) {
                text = text.replaceAll("<wsw-state-trail-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.selected"));
            } else if (cosmetics.hasCachedCage(id) && cosmetics.getSelectedCageId() != id) {
                text = text.replaceAll("<wsw-state-trail-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.not-selected"));
            } else {
                text = text.replaceAll("<wsw-state-trail-" + id + ">", plugin
                        .getMessagesConfig()
                        .getString("enums.cosmetics.buy").replaceAll("<price>",
                                String.valueOf(trail.getCost())));
            }
        }

        return text;
    }

    @Override
    public void setMenuItems(IGamePlayer player) {
        ConfigurationSection section = config.getSection("buttons");
        for (String path : section.keys()) {
            ConfigurationSection button = section.getSection(path);
            ItemStackBuilder builder = ItemStackBuilder.getItemStack(button, player)
                    .clearLore();
            List<String> lore = button.getStringList("lore");
            lore.stream().map(s -> setPlaceholders(s, player)).forEach(builder::addLore);
            inventory.setItem(button.getInteger("position"), builder.build());
        }
    }

    @Override
    public void onClick(IGamePlayer player, InventoryClickEvent event) {
        player.getPlayer().ifPresent(bukkitPlayer -> {
            ConfigurationSection section = config.getSection("buttons");
            for (String path : section.keys()) {
                ConfigurationSection button = section.getSection(path);
                if (button.getInteger("position") == event.getSlot()) {
                    if (button.isSet("permission")) {
                        if (!bukkitPlayer.hasPermission(button.getString("permission"))) {
                            return;
                        }
                    }
                    List<String> actions = button.getStringList("actions");
                    WhaleSkyWars.getInstance().getActionManager().onAction(player, actions);
                    break;
                }
            }
        });
    }

}
