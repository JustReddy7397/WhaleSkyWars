package ga.justreddy.wiki.whaleskywars.shared.packet.packets.game;

import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.shared.json.JsonSerializable;
import ga.justreddy.wiki.whaleskywars.shared.packet.Packet;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JustReddy
 */
@Getter
public class BungeeGame {

    private final String name;
    private final String server;
    private GameState gameState = GameState.WAITING;
    private final String gameMode;
    private List<UUID> players = new ArrayList<>();
    private final int maxPlayers;

    public BungeeGame(String name, String server, int maxPlayers, String gameMode) {
        super();
        this.name = name;
        this.server = server;
        this.maxPlayers = maxPlayers;
        this.gameMode = gameMode;
    }

    public BungeeGame(String name, String server, int maxPlayers, GameState state, String gameMode, List<UUID> players) {
        this.name = name;
        this.server = server;
        this.maxPlayers = maxPlayers;
        this.gameState = state;
        this.gameMode = gameMode;
        this.players = players;
    }

    public boolean isGameState(GameState gameState) {
        return this.gameState == gameState;
    }

    public boolean isGameMode(String gameMode) {
        return this.gameMode.equalsIgnoreCase(gameMode);
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }


}
