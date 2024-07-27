package ga.justreddy.wiki.whaleskywars.support;

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
