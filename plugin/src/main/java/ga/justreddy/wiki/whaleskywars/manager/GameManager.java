package ga.justreddy.wiki.whaleskywars.manager;

import com.google.common.collect.ImmutableList;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.model.game.Game;
import ga.justreddy.wiki.whaleskywars.util.ShuffleUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author JustReddy
 */
public class GameManager {

    private final Map<String, IGame> games;
    private final File gamesFolder;

    public GameManager() {
        this.games = new HashMap<>();
        this.gamesFolder = new File(WhaleSkyWars.getInstance().getDataFolder(), "data/games/");
        if (!gamesFolder.exists()) {
            gamesFolder.mkdirs();
        }
    }

    public void start() {
        File[] files = gamesFolder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (!file.getName().endsWith(".toml")) {
                continue;
            }

            String name = file.getName().replace(".toml", "");
            if (games.containsKey(name)) continue;
            TempConfig config = new TempConfig(gamesFolder, name + ".toml");
            register(name, config);
        }
    }

    public void die() {
        games.clear();
    }

    public void register(String name, TempConfig tempConfig) {
        IGame game = new Game(name, tempConfig);
        games.put(name, game);
        if (WhaleSkyWars.getInstance().getServerMode() != ServerMode.LOBBY) {
            WhaleSkyWars.getInstance().getGameMap().onEnable(game);
        }
    }

    public ImmutableList<IGame> getGames() {
        return ImmutableList.copyOf(games.values());
    }

    public IGame getRandomGame() {
        List<IGame> gameList = new ArrayList<>(games.values());
        gameList.removeIf(game -> game.isGameState(GameState.DISABLED));
        gameList.removeIf(game -> !game.isGameState(GameState.WAITING) || !game.isGameState(GameState.STARTING));
        gameList.removeIf(game -> game.getPlayers().size() >= game.getMaximumPlayers());
        ShuffleUtil.shuffle(gameList);
        if (gameList.isEmpty()) return null;
        return gameList.get(0);
    }

    public IGame getGameByName(String name) {
        return games.values().stream()
                .filter(game -> game.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public List<IGame> getGamesBySimilarName(String name) {
        try (Stream<IGame> gameStream = games.values().stream()) {
            try (Stream<IGame> filteredStream = gameStream.filter(game -> game.getName().contains(name))) {
                return filteredStream.collect(Collectors.toList());
            }
        }
    }

    public List<BungeeGame> getBungeeGamesBySimilarNames(String name) {
        try (Stream<IGame> gameStream = games.values().stream()) {
            try (Stream<IGame> filteredStream = gameStream.filter(game -> game.getName().contains(name))) {
                return filteredStream.map(game -> ((Game)game).getBungeeGame()).collect(Collectors.toList());
            }
        }
    }

    public BungeeGame getByGame(IGame game) {
        return ((Game)game).getBungeeGame();
    }

    public BungeeGame getByGameName(String name) {
        IGame game = getGameByName(name);
        if (game == null) return null;
        return ((Game)game).getBungeeGame();
    }

    public Game getByBungeeGame(BungeeGame bungeeGame) {
        Set<IGame> games = new HashSet<>(this.games.values());
        return games.stream()
                .filter(igame -> igame instanceof Game)
                .map(igame -> (Game) igame)
                .filter(game -> game.getBungeeGame() != null)
                .filter(game -> game.getBungeeGame().equals(bungeeGame))
                .findFirst()
                .orElse(null);
    }

    public File getGamesFolder() {
        return gamesFolder;
    }

}
