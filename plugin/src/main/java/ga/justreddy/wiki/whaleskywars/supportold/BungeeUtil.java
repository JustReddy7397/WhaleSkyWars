package ga.justreddy.wiki.whaleskywars.supportold;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.supportold.packets.packets.BackToServerPacket;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

/**
 * @author JustReddy
 */
@UtilityClass
public class BungeeUtil {

    public void sendBackToServer(IGamePlayer player) {

        switch (WhaleSkyWars.getInstance().getSettingsConfig().getString("bungee.on-leave")) {
            case "JOIN_SERVER": {
                BackToServerPacket packet = new BackToServerPacket(player.getUniqueId(),
                        WhaleSkyWars.getInstance().getSettingsConfig().getString("bungee.lobby"),
                        BackToServerPacket.ServerPriority.PREVIOUS);
/*
                WhaleSkyWars.getInstance().getMessenger().getSender().sendPacket(packet);
*/
                break;
            }
            case "LOBBY_SERVER": {
                BackToServerPacket packet = new BackToServerPacket(player.getUniqueId(),
                        WhaleSkyWars.getInstance().getSettingsConfig().getString("bungee.lobby"),
                        BackToServerPacket.ServerPriority.LOBBY);
/*
                WhaleSkyWars.getInstance().getMessenger().getSender().sendPacket(packet);
*/

                break;
            }
            case "LEAVE_COMMAND": {
                for (String s : WhaleSkyWars.getInstance().getSettingsConfig().getStringList("bungee.leave-commands")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("<player>", player.getName()));
                }
                break;
            }
        }

    }


}
