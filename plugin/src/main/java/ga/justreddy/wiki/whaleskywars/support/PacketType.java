package ga.justreddy.wiki.whaleskywars.support;

import lombok.Getter;

import java.lang.reflect.Type;

/**
 * @author JustReddy
 */
@Getter
public enum PacketType {

    // SELF -> CLIENT
    // CLIENT -> SERVER
    // SERVER -> CLIENT
    CLIENT_IDENTIFY("client:identify", ServerIdentity.class, true),
    CLIENT_CHECKUP("client:checkup", null, false);

/*
    CLIENT_BACK_TO_SERVER("client:back_to_server", BackToServerPacket.class),
    CLIENT_GAME_UPDATE("client:game_update", GameUpdatePacket.class),
    CLIENT_GAME_REMOVE("client:game_remove", GameRemovePacket.class),
    CLIENT_GAMES_REQUEST("client:games_request", GamesRequestPacket.class),
    CLIENT_GAME_JOIN("client:game_join", GameJoinPacket.class),
    CLIENT_MESSAGE("client:message", MessagePacket.class),
    CLIENT_PLAYER_KICK("client:player_kick", PlayerKickPacket.class),
    CLIENT_MAP_CREATE("client:map_create", MapCreatePacket.class),
    CLIENT_MAP_UPDATE("client:map_update", MapUpdatePacket.class),
    SERVER_GAMES_REMOVE("server:games_remove", StringPacket.class),
    SERVER_GAMES_ADD("server:games_add", GamesAddPacket.class),
    SERVER_GAMES_SEND("server:games_send", GamesSendPacket.class),
    CLIENT_IDENTIFY("client:identify", IdentifyPacket.class);
*/

    private final String name;
    private final Type type;
    private final boolean ack;

    PacketType(String name, Type type, boolean ack) {
        this.name = name;
        this.type = type;
        this.ack = ack;
    }

}
