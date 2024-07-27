package ga.justreddy.wiki.whaleskywars.support;

import ga.justreddy.wiki.whaleskywars.support.packets.Packet;

/**
 * @author JustReddy
 */
public interface IMessengerSender {

    void close();

    void sendPacket(Packet packet);

    void sendPacketToServer(Packet packet, String server);

    void sendPacketToAllExcept(Packet packet, String exceptServer);

}
