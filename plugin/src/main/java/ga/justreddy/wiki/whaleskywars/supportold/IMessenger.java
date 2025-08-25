package ga.justreddy.wiki.whaleskywars.supportold;

/**
 * @author JustReddy
 */
public interface IMessenger<T> {

    void connect();

    void close();

    T getPlugin();

    IMessengerSender getSender();

    IMessengerReceiver getReceiver();

}
