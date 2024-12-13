package ga.justreddy.wiki.whaleskywars.model.game.modes;

import ga.justreddy.wiki.whaleskywars.api.SkyWarsAPI;
import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import ga.justreddy.wiki.whaleskywars.api.events.SkyWarsGameStateChangeEvent;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameMode;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class SwapGameMode extends GameMode {

    // Used to access the SkyWarsAPI
    private final SkyWarsAPI api = SkyWarsProvider.get();
    // Used for the task to swap the players
    private BukkitTask swapTask;

    @Override
    public String getIdentifier() {
        return "swap";
    }

    @Override
    public String getDisplayName() {
        return "Swap";
    }

    @EventHandler
    public void onGameStateChange(SkyWarsGameStateChangeEvent event) {
        // Get the previous and new state
        GameState previousState = event.getPreviousState();
        // Get the new state
        GameState newState = event.getNewState();
        // Check if the states are the same
        if (newState == previousState) return;
        // Check if the new state is playing
        if (newState == GameState.PLAYING) {
            // Run the task to swap the players
            swapTask = api.getPlugin().getServer()
                    .getScheduler().runTaskTimer(api.getPlugin(), () -> {
                        // Get all the teams with more than 1 player
                        List<IGameTeam> teams = event.getGame()
                                .getAliveTeams()
                                .stream().filter(team -> team.getSize() > 1)
                                .collect(Collectors.toList());
                        // Get all the teams with 1 player
                        List<IGameTeam> soloTeams = event.getGame().
                                getAliveTeams()
                                .stream().filter(team -> team.getSize() == 1)
                                .collect(Collectors.toList());
                        // Shuffle the teams
                        List<IGameTeam> firstTeams = new ArrayList<>(teams);
                        Collections.shuffle(firstTeams);
                        List<IGameTeam> secondTeams = new ArrayList<>(teams);
                        Collections.shuffle(secondTeams);
                        List<IGameTeam> soloFirstTeams = new ArrayList<>(soloTeams);
                        Collections.shuffle(soloFirstTeams);
                        List<IGameTeam> soloSecondTeams = new ArrayList<>(soloTeams);
                        Collections.shuffle(soloSecondTeams);
                        // Swap the teams
                        if (teams.size() > 1) {
                            swapTeams(teams, firstTeams, secondTeams);
                        }
                        // Swap the solo teams
                        if (soloTeams.size() > 1) {
                            swapTeams(soloTeams, soloFirstTeams, soloSecondTeams);
                        }

                    }, 20 * 20, 20 * 20);
        } else if (newState == GameState.ENDING) {
            // Cancel the task if the game is ending
            if (swapTask != null) swapTask.cancel();
        }
    }

    private void swapTeams(List<IGameTeam> teams, List<IGameTeam> firstTeams, List<IGameTeam> secondTeams) {
        // Loop through the teams
        for (int i = 0; i < teams.size(); i++) {
            // Get the first team
            IGameTeam firstTeam = firstTeams.get(i);
            // Get the living players of the first team
            List<IGamePlayer> firstTeamAlivePlayers = new ArrayList<>(firstTeam
                    .getAlivePlayers());
            // Get the second team
            IGameTeam secondTeam = secondTeams.get(i);
            // Get the living players of the second team
            List<IGamePlayer> secondTeamAlivePlayers = new ArrayList<>(secondTeam
                    .getAlivePlayers());
            // Check if the teams are the same, if so, skip
            if (firstTeam.getId().equals(secondTeam.getId())) continue;

            // Loop through the players of the first team
            firstTeamAlivePlayers.forEach(player -> {
                // Remove the player from the first team
                firstTeam.removePlayer(player);
                // Add the player to the second team
                secondTeam.addPlayer(player);
                // Get the bukkit player
                player.getPlayer().ifPresent(bukkitPlayer -> {
                    // Send a message to the player
                    bukkitPlayer.sendMessage("You have been swapped to" +
                            " another team!");
                    // Check if the first team has no living players
                    if (secondTeamAlivePlayers.isEmpty()) {
                        // Teleport the player to the first living player of the second team
                        firstTeamAlivePlayers.stream()
                                .filter(p -> !p.getUniqueId().equals(player.getUniqueId()))
                                .findFirst().ifPresent(found ->
                                        bukkitPlayer.teleport(found.getPlayer().get()
                                                .getLocation()));
                    } else {
                        // Teleport the player to the first living player of the second team
                        secondTeamAlivePlayers.stream().findFirst()
                                .ifPresent(found -> {
                                    bukkitPlayer.teleport(found.getPlayer().get().getLocation());
                                    // Remove the player from the list
                                    secondTeamAlivePlayers.removeIf(p -> p.getUniqueId().equals(found.getUniqueId()));
                                });
                    }
                });
            });
            // Loop through the players of the second team
            secondTeamAlivePlayers.forEach(player -> {
                // Remove the player from the second team
                secondTeam.removePlayer(player);
                // Add the player to the first team
                firstTeam.addPlayer(player);
                // Get the bukkit player
                player.getPlayer().ifPresent(bukkitPlayer -> {
                    // Send a message to the player
                    bukkitPlayer.sendMessage("You have been swapped " +
                            "to another team!");
                    // Check if the second team has no living players
                    if (firstTeamAlivePlayers.isEmpty()) {
                        // Teleport the player to the first living player of the first team
                        secondTeamAlivePlayers.stream()
                                .filter(p -> !p.getUniqueId().equals(player.getUniqueId()))
                                .findFirst().ifPresent(found ->
                                        bukkitPlayer.teleport(found.getPlayer()
                                                .get().getLocation())
                                );
                    } else {
                        // Teleport the player to the first living player of the first team
                        firstTeamAlivePlayers.stream().findFirst()
                                .ifPresent(found -> {
                                    bukkitPlayer.teleport(found.getPlayer().get().getLocation());
                                    // Remove the player from the list
                                    firstTeamAlivePlayers.removeIf(p -> p.getUniqueId().equals(found.getUniqueId()));
                                });
                    }
                });
            });
        }
    }

}
