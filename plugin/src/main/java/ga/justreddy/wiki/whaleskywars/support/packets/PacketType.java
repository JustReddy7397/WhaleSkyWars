package ga.justreddy.wiki.whaleskywars.support.packets;

/**
 * @author JustReddy
 */
public enum PacketType {

    // SELF -> CLIENT
    // CLIENT -> SERVER
    // SERVER -> CLIENT

    CLIENT_BACK_TO_SERVER,
    CLIENT_GAME_UPDATE,
    CLIENT_GAME_REMOVE,
    CLIENT_GAMES_REQUEST,
    CLIENT_GAME_JOIN,
    CLIENT_MESSAGE,
    CLIENT_PLAYER_KICK,
    CLIENT_MAP_CREATE,
    CLIENT_MAP_UPDATE,
    SERVER_GAMES_REMOVE,
    SERVER_GAMES_ADD,
    SERVER_GAMES_SEND,
    SERVER_GAME_JOIN,


}
