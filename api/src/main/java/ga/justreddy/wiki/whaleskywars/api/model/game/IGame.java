package ga.justreddy.wiki.whaleskywars.api.model.game;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameMode;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.api.model.game.timer.AbstractTimer;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author JustReddy
 */
public interface IGame {

    String getName();

    String getDisplayName();

    int getMinimumPlayers();

    int getMaximumPlayers();

    int getTeamSize();

    int getPlayerCount();

    List<IGamePlayer> getPlayers();

    List<IGamePlayer> getAlivePlayers();

    List<IGamePlayer> getSpectators();

    List<IGameTeam> getTeams();

    Set<IGameTeam> getRandomTeams();

    List<IGameTeam> getAliveTeams();

    List<IGameTeam> getSpectatorTeams();

    Map<UUID, Integer> getKills();

    int getKills(IGamePlayer player);

    Location getWaitingSpawn();

    Location getSpectatorSpawn();

    ICuboid getWaitingCuboid();

    ICuboid getGameCuboid();

    GameState getGameState();

    void setGameState(GameState gameState);

    boolean isGameState(GameState gameState);

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    boolean isGameMode(GameMode gameMode);

    AbstractTimer getStartingTimer();

    AbstractTimer getPreGameTimer();

    AbstractTimer getEndingTimer();

    World getWorld();

    void init(World world);

    void sendMessage(List<IGamePlayer> players, String message);

    void sendMessages(List<IGamePlayer> players, List<String> messages);

    void sendMessages(List<IGamePlayer> players, String... messages);

    void sendTitle(List<IGamePlayer> players, String title, String subtitle, int fadeIn, int stay, int fadeOut);

    void sendActionBar(List<IGamePlayer> players, String message);

    void sendSound(List<IGamePlayer> players, String sound, float volume, float pitch);

    void onGamePlayerJoin(IGamePlayer player);

    void onGamePlayerJoin(IGamePlayer player, IGameTeam team);

    void onGamePlayerSpectate(IGamePlayer player);

    void onGamePlayerLeave(IGamePlayer player, boolean isSilent);

    void onCountDown();

    void goToNextPhase();

    void assignTeams();

}
