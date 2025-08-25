package ga.justreddy.wiki.whaleskywars.supportold;

import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;

/**
 * @author JustReddy
 */
public interface IMessengerReceiver {

    void handlePacket(Packet packet);

}
