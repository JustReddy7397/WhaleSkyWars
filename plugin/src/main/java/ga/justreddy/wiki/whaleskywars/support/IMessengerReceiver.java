package ga.justreddy.wiki.whaleskywars.support;

import ga.justreddy.wiki.whaleskywars.support.packets.Packet;

/**
 * @author JustReddy
 */
public interface IMessengerReceiver {

    void handlePacket(Packet packet);

}
