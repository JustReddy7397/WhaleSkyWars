package ga.justreddy.wiki.whaleskywars.model.game.phases;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameStateChangeEvent;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IPhase;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.model.Messages;
import ga.justreddy.wiki.whaleskywars.model.entity.GamePlayer;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import ga.justreddy.wiki.whaleskywars.util.PlayerUtil;
import ga.justreddy.wiki.whaleskywars.util.Replaceable;
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
        GameState previousState = game.getGameState();
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
                if (((Game)game).isTeamGame()) {
                    winnerText.append(player.getName());
                } else {
                    winnerText.append(player.getName())
                            .append(++i == winnerSize ? "" : ", ");
                }
                player.getStats().addWin(((Game)game).isTeamGame());
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

        for (IGamePlayer player : game.getPlayers()) {
            if (player == null) continue;
            List<Map.Entry<String, Integer>> topThree = game.getTopThreeKillers();

            String firstKiller = "N/A";
            int firstKills = 0;
            String secondKiller = "N/A";
            int secondKills = 0;
            String thirdKiller = " N/A";
            int thirdKills = 0;

            if (!topThree.isEmpty()) {
                firstKiller = topThree.get(0).getKey();
                firstKills = topThree.get(0).getValue();
                if (topThree.size() > 1) {
                    secondKiller = topThree.get(1).getKey();
                    secondKills = topThree.get(1).getValue();
                }
                if (topThree.size() > 2) {
                    thirdKiller = topThree.get(2).getKey();
                    thirdKills = topThree.get(2).getValue();
                }
            }
            game.sendMessages(game.getPlayers(),
                    Messages.GAME_WINNERS.toList(
                            Replaceable.of("<winners>", winnerText.toString()),
                            Replaceable.of("<killer_1>", firstKiller),
                            Replaceable.of("<kills_1>", String.valueOf(firstKills)),
                            Replaceable.of("<killer_2>", secondKiller),
                            Replaceable.of("<kills_2>", String.valueOf(secondKills)),
                            Replaceable.of("<killer_3>", thirdKiller),
                            Replaceable.of("<kills_3>", String.valueOf(thirdKills))
                    ));
        }

    }

    @Override
    public void onTick(IGame game) {
        if (game.getEndingTimer().getTicksExceed() <= 0) {
            game.goToNextPhase();
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
