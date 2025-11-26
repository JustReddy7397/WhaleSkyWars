package ga.justreddy.wiki.whaleskywars.util;

import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IGameTeam;
import ga.justreddy.wiki.whaleskywars.shared.packet.packets.game.BungeeGame;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Wonkglorg <3
 * It sorts the list from high to low
 * then it shuffles all the values with the same integer value
 * and sorts it again
 * I'm pretty sure that's how it works!
 */
public class ShuffleUtil {

    public static void shuffleBungee(List<BungeeGame> games) {
        games.sort(Comparator.comparing(value -> value.getPlayers().size()));
        Collections.reverse(games);
        int startIndex = 0;
        int endIndex = 0;
        int currentValue = games.get(0).getPlayers().size();
        Random random = ThreadLocalRandom.current();
        for(int i = 1; i < games.size(); i++){
            BungeeGame currentObject = games.get(i);
            int value = currentObject.getPlayers().size();
            if(value == currentValue){
                endIndex = i;
            } else {
                shuffleEqualValuesBungee(games, startIndex, endIndex, random);
                startIndex = i;
                endIndex = i;
                currentValue = value;
            }
        }
        shuffleEqualValuesBungee(games, startIndex, endIndex, random);
    }

    private static void shuffleEqualValuesBungee(List<BungeeGame> list, int start, int end, Random random) {
        int size = end - start + 1;
        while(size > 1){
            int i = start + random.nextInt(size);
            int j = start + --size;
            Collections.swap(list, i, j);
        }
    }

    public static void shuffle(List<IGame> games) {

        // Sorts games by their player counts ( high to low )
        games.sort(Comparator.comparingInt(IGame::getPlayerCount).reversed());

        // Start index
        int startIndex = 0;
        // End index
        int endIndex = 0;
        // Current player count value of the first game in the list
        int currentValue = games.get(0).getPlayerCount();
        // Create a new random object
        Random random = ThreadLocalRandom.current();
        // Looping over all the games except the first one!
        for (int i = 1; i < games.size(); i++) {
            // Getting the game by the index aka "i"
            IGame currentObject = games.get(i);
            // Getting the player count of the current game
            int value = currentObject.getPlayerCount();
            // If the value is equal to the current value,
            // then we set the end index to the current index
            if (value == currentValue) {
                endIndex = i;
            } else {
                // Else we shuffle the values between the start and end index
                shuffleEqualValues(games, startIndex, endIndex, random);
                // And we set the start index to the current index
                startIndex = i;
                // And the end index to the current index
                endIndex = i;
                // And the current value to the games value we declared in the for loop
                currentValue = value;
            }
        }
        // Shuffling the last values
        shuffleEqualValues(games, startIndex, endIndex, random);
    }

    private static void shuffleEqualValues(List<IGame> list, int start, int end, Random random) {
        // Getting the size of the list
        // The size is the end index - the start index + 1
        int size = end - start + 1;
        // Looping over the list
        while (size > 1) {
            // Getting a random integer between the start and the size
            int i = start + random.nextInt(size);
            // Decreasing the size by 1
            int j = start + --size;
            // Swapping the values
            Collections.swap(list, i, j);
        }
    }

    public static void shuffleTeams(List<IGameTeam> teams) {

        // Sorts games by their player counts ( high to low )
        teams.sort(Comparator.comparingInt(IGameTeam::getSize).reversed());

        // Start index
        int startIndex = 0;
        // End index
        int endIndex = 0;
        // Current player count value of the first team in the list
        int currentValue = teams.get(0).getSize();
        // Create a new random object
        Random random = ThreadLocalRandom.current();
        // Looping over all the teams except the first one!
        for (int i = 1; i < teams.size(); i++) {
            // Getting the game by the index aka "i"
            IGameTeam currentObject = teams.get(i);
            // Getting the player count of the current team
            int value = currentObject.getSize();
            // If the value is equal to the current value,
            // then we set the end index to the current index
            if (value == currentValue) {
                endIndex = i;
            } else {
                // Else we shuffle the values between the start and end index
                shuffleEqualTeams(teams, startIndex, endIndex, random);
                // And we set the start index to the current index
                startIndex = i;
                // And the end index to the current index
                endIndex = i;
                // And the current value to the team value we declared in the for loop
                currentValue = value;
            }
        }
        // Shuffling the last values
        shuffleEqualTeams(teams, startIndex, endIndex, random);
    }

    private static void shuffleEqualTeams(List<IGameTeam> list, int start, int end, Random random) {
        // Getting the size of the list
        // The size is the end index - the start index + 1
        int size = end - start + 1;
        // Looping over the list
        while (size > 1) {
            // Getting a random integer between the start and the size
            int i = start + random.nextInt(size);
            // Decreasing the size by 1
            int j = start + --size;
            // Swapping the values
            Collections.swap(list, i, j);
        }
    }

}
