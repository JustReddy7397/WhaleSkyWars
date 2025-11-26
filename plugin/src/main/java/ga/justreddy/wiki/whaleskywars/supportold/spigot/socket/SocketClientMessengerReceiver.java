package ga.justreddy.wiki.whaleskywars.supportold.spigot.socket;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import ga.justreddy.wiki.whaleskywars.supportold.IMessengerReceiver;
import ga.justreddy.wiki.whaleskywars.supportold.packets.Packet;
import ga.justreddy.wiki.whaleskywars.supportold.packets.PacketType;
import ga.justreddy.wiki.whaleskywars.supportold.packets.packets.GameRemovePacket;
import ga.justreddy.wiki.whaleskywars.supportold.packets.packets.GameUpdatePacket;
import ga.justreddy.wiki.whaleskywars.supportold.packets.packets.GamesAddPacket;
import ga.justreddy.wiki.whaleskywars.supportold.packets.packets.StringPacket;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.Socket;

/**
 * @author JustReddy
 */
public class SocketClientMessengerReceiver implements IMessengerReceiver {

    private final WhaleSkyWars PLUGIN = WhaleSkyWars.getInstance();

    public void onClientMessageRead(Socket socket, ObjectInputStream inputStream) throws IOException {

        while (!socket.isClosed()) {
            try {
                Object object = inputStream.readObject();

                if (!(object instanceof Packet)) return;

                Packet packet = (Packet) object;

                Bukkit.getScheduler().runTaskAsynchronously(PLUGIN, () -> handlePacket(packet));


            } catch (ClassNotFoundException | OptionalDataException
                    | StreamCorruptedException
                    | InvalidClassException ex) {
                TextUtil.error(ex, "Error while reading packet from server", false
                        );
            }
        }

    }

    @Override
    public void handlePacket(Packet packet) {
        // TODO
        if (packet.getServer().equalsIgnoreCase("b25") && packet.getPacketType() != PacketType.CLIENT_GAME_UPDATE) {
            return;
        }

        switch (packet.getPacketType()) {
            case CLIENT_GAME_UPDATE: {
                if (!(packet instanceof GameUpdatePacket)) return;
                GameUpdatePacket gameUpdatePacket = (GameUpdatePacket) packet;
                BungeeGame game = gameUpdatePacket.getGame();
                Game localGame = PLUGIN.getGameManager().getByBungeeGame(game);
                if (localGame == null) return;
                localGame.setBungeeGame(game);
                break;
            }
            case CLIENT_GAME_REMOVE: {
                if (!(packet instanceof GameRemovePacket)) return;
                GameRemovePacket gameRemovePacket = (GameRemovePacket) packet;
                BungeeGame game = gameRemovePacket.getGame();
                Game localGame = PLUGIN.getGameManager().getByBungeeGame(game);
                if (localGame == null) return;
                localGame.setBungeeGame(null);
                break;
            }
            case SERVER_GAMES_ADD: {
                if (!(packet instanceof GamesAddPacket)) return;
                GamesAddPacket gamesAddPacket = (GamesAddPacket) packet;
                for (BungeeGame bungeeGame : gamesAddPacket.getGames()) {
                    IGame gameByName = PLUGIN.getGameManager().getGameByName(bungeeGame.getName());
                    if (gameByName == null) continue;
                    ((Game) gameByName).setBungeeGame(bungeeGame);
                }
                break;
            }
            case SERVER_GAMES_REMOVE: {
                StringPacket stringPacket = (StringPacket) packet;
            }
        }

    }
}
