package ga.justreddy.wiki.whaleskywars.model.action.actions;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;

/**
 * @author JustReddy
 */
public class ServerAction implements IAction {
    @Override
    public String getIdentifier() {
        return "SERVER";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(player.getName());
        out.writeUTF(data);
        player.getPlayer().ifPresent(bukkitPlayer ->
                bukkitPlayer.sendPluginMessage(plugin, "BungeeCord",
                        out.toByteArray()));
    }
}
