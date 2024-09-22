package ga.justreddy.wiki.whaleskywars.model.action.actions;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * @author JustReddy
 */
public class MessageAction implements IAction {
    @Override
    public String getIdentifier() {
        return "MESSAGE";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        Optional<Player> optionalBukkitPlayer = player.getPlayer();
       optionalBukkitPlayer.ifPresent(bukkitPlayer -> {
           if (data.startsWith("json:")) {
               WhaleSkyWars.getInstance().getNms()
                       .setJsonMessage(bukkitPlayer, data.replace("json:", ""));
           } else {
                player.sendMessage(data);
           }
       });
    }
}
