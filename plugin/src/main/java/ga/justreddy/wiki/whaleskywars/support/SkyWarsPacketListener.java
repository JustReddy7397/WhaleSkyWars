package ga.justreddy.wiki.whaleskywars.support;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author JustReddy
 */
public abstract class SkyWarsPacketListener {

    public abstract void init();

    public abstract void registerListener(PacketListener listener);

    public abstract void processListener(PacketListener listener,
                                         Method method,
                                         PacketType packetType,
                                         Type returnType,
                                         Class<?> paramClass, Type type);

    public abstract void listen(PacketListener listener, Method method, PacketType type) throws IOException;

    public abstract PacketEmitter getEmitter();

    public abstract void close();

}
