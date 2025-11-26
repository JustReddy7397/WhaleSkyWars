package ga.justreddy.wiki.whaleskywars.bungeecord.support;

import ga.justreddy.wiki.whaleskywars.bungeecord.Bungeecord;
import ga.justreddy.wiki.whaleskywars.shared.PacketListener;
import ga.justreddy.wiki.whaleskywars.shared.SkyWarsListener;

/**
 * @author JustReddy
 */
public abstract class BungeePacketListener extends PacketListener {

    private final Bungeecord bungeecord;
    private final SkyWarsListener listener;

    public BungeePacketListener(SkyWarsListener listener) {
        this.bungeecord = Bungeecord.getInstance();
        this.listener = listener;
    }

    public Bungeecord getBungeecord() {
        return bungeecord;
    }

    public SkyWarsListener getListener() {
        return listener;
    }
}
