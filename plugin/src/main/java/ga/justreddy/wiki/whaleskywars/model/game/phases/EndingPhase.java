package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameStateChangeEvent;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import ga.justreddy.wiki.whaleskywars.util.PlayerUtil;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class EndingPhase implements IPhase {

    private final IGameTeam winnerTeam;

    public EndingPhase(IGameTeam winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    @Override
    public void onEnable(IGame game) {
        GameState previousState = GameState.valueOf(game.getGameState().name());
        game.setGameState(GameState.ENDING);
        game.getEndingTimer().start();
        ((Game)game).getEvents().forEach(event -> event.onDisable(game));

        SkyWarsGameStateChangeEvent event = new SkyWarsGameStateChangeEvent(game, previousState, game.getGameState());
        event.call();

        StringBuilder winnerText = new StringBuilder();

        if (winnerTeam != null) {
            int winnerSize = winnerTeam.getPlayers().size();
            int i = 0;
            for (IGamePlayer player : winnerTeam.getPlayers()) {
                if (player == null) continue;
                if (player.getGame().getName().equalsIgnoreCase(game.getName())) {
                    PlayerUtil.refresh(player);
                }
                if (game.getGameMode().isTeamGame()) {
                    winnerText.append(player.getName());
                } else {
                    winnerText.append(player.getName())
                            .append(++i == winnerSize ? "" : ", ");
                }
                player.getStats().addWin(game.getGameMode().isTeamGame());
                final VictoryDance dance = WhaleSkyWars.getInstance().getVictoryDanceManager().copyOf(
                        player.getCosmetics().getSelectedVictoryDance()
                );
                if (dance != null) {
                    dance.start(player);
                    Bukkit.getScheduler().runTaskLater(WhaleSkyWars.getInstance(), () -> {
                        dance.stop(player);
                    }, 100L);
                }

            }
        }

        game.sendMessage(game.getPlayers(), "Thanks for playing bitches");
    }

    @Override
    public void onTick(IGame game) {
        if (game.getEndingTimer().getTicksExceed() <= 0) {
            game.goToNextPhase();
            return;
        }
    }

    @Override
    public IPhase getNextPhase() {
        return new RestartingPhase();
    }

    private Map<String, Integer> getTopThreeKillers(IGame game) {
        List<IGamePlayer> players = game.getPlayers();
        Map<String, Integer> topThree = players.stream()
                .collect(Collectors.toMap(IGamePlayer::getName, game::getKills));
        return topThree.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
