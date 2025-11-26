package ga.justreddy.wiki.whaleskywars.bungeecord;

import ga.justreddy.wiki.whaleskywars.bungeecord.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.bungeecord.support.socket.BungeeSocketListener;
import ga.justreddy.wiki.whaleskywars.shared.ListenerType;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.UnknownHostException;

public class Bungeecord extends Plugin {

    private static Bungeecord instance;
    private ListenerType listenerType;

    @Override
    public void onEnable() {
        instance = this;
        final TomlConfig config = new TomlConfig("bungee-config.toml");
        try {
            new BungeeSocketListener(config).init();
            listenerType = ListenerType.SOCKET;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static Bungeecord getInstance() {
        return instance;
    }

    public boolean isListenerType(ListenerType type) {
        return listenerType == type;
    }

    public ListenerType getListenerType() {
        return listenerType;
    }
}