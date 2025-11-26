package ga.justreddy.wiki.whaleskywars.bungeecord.support.socket.listeners;

import ga.justreddy.wiki.whaleskywars.bungeecord.support.BungeePacketListener;
import ga.justreddy.wiki.whaleskywars.shared.SkyWarsListener;

/**
 * @author JustReddy
 */
public class GameSocketPacketListener extends BungeePacketListener {

    public GameSocketPacketListener(SkyWarsListener listener) {
        super(listener);
        getListener().registerListener(this);
    }


}
