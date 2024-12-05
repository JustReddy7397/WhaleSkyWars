package ga.justreddy.wiki.whaleskywars.model.game.team;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.ITeamAssigner;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import ga.justreddy.wiki.whaleskywars.util.ShuffleUtil;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author JustReddy
 */
public class TeamAssigner implements ITeamAssigner {

    private final Set<UUID> skip = new HashSet<>();

    @Override
    public void assign(IGame game) {

        if (game.getPlayerCount() > game.getTeamSize() && game.getTeamSize() > 1) {
            // Parties support eventually
            LinkedList<List<IGamePlayer>> teams = new LinkedList<>();
            if (!teams.isEmpty()) {

                for (IGameTeam team : game.getTeams()) {
                    teams.sort(Comparator.comparing(List::size));
                    if (teams.get(0).isEmpty()) break;
                    for (int i = 0; i < game.getTeamSize() && team.getPlayers().size() < game.getTeamSize(); i++) {
                        if (teams.get(0).size() > i) {
                            IGamePlayer toAdd = teams.get(0).remove(0);
                            toAdd.getPlayer().ifPresent(Player::closeInventory);
                            team.addPlayer(toAdd);
                            skip.add(toAdd.getUniqueId());
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (IGamePlayer remaining : game.getPlayers()) {
            if (skip.contains(remaining.getUniqueId())) continue;
            if (remaining.getGameTeam() != null) continue;
            for (IGameTeam team : game.getRandomTeams()) {
                if (team.getPlayers().size() < game.getTeamSize()) {
                    remaining.getPlayer().ifPresent(Player::closeInventory);
                    team.addPlayer(remaining);
                    remaining.setGameTeam(team);
                    break;
                }
            }
        }

    }

    @Override
    public void assign(IGame game, IGamePlayer player) {
        // TODO change :)
        if (game.getPlayerCount() > game.getTeamSize() && game.getTeamSize() > 1) {
            // Parties support eventually
            LinkedList<List<IGamePlayer>> teams = new LinkedList<>();
            if (!teams.isEmpty()) {
                for (IGameTeam team : new ArrayList<>(game.getTeams())) {
                    teams.sort(Comparator.comparing(List::size));
                    if (teams.get(0).isEmpty()) break;
                    for (int i = 0; i < game.getTeamSize() && team.getPlayers().size() < game.getTeamSize(); i++) {
                        if (teams.get(0).size() > i) {
                            IGamePlayer toAdd = teams.get(0).remove(0);
                            toAdd.getPlayer().ifPresent(Player::closeInventory);
                            team.addPlayer(toAdd);
                            skip.add(toAdd.getUniqueId());
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        List<IGameTeam> randomTeams = new ArrayList<>(game.getRandomTeams());
        if (((Game)game).isTeamGame()) {
            ShuffleUtil.shuffleTeams(randomTeams);
            for (IGameTeam team : randomTeams) {
                if (team.getPlayers().size() < game.getTeamSize()) {
                    player.getPlayer().ifPresent(Player::closeInventory);
                    team.addPlayer(player);
                    player.setGameTeam(team);
                    break;
                }
            }
        } else {
            Collections.shuffle(randomTeams);
            for (IGameTeam team : randomTeams) {
                if (team.getPlayers().size() < game.getTeamSize()) {
                    player.getPlayer().ifPresent(Player::closeInventory);
                    team.addPlayer(player);
                    player.setGameTeam(team);
                    break;
                }
            }
        }
    }
}
