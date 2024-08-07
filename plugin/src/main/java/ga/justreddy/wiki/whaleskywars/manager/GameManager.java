package ga.justreddy.wiki.whaleskywars.manager;

import com.google.common.collect.ImmutableList;
import com.google.gson.internal.bind.JsonTreeReader;
import com.moandjiezana.toml.Toml;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.model.ServerMode;
import ga.justreddy.wiki.whaleskywars.model.game.BungeeGame;
import ga.justreddy.wiki.whaleskywars.model.game.Game;

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
            Toml toml = new Toml().read(file);
            register(name, toml);

        }
    }

    public void die() {
        games.clear();
    }

    public void register(String name, Toml toml) {
        IGame game = new Game(name, toml);
        games.put(name, game);
        if (WhaleSkyWars.getInstance().getServerMode() != ServerMode.LOBBY) {
            WhaleSkyWars.getInstance().getGameMap().onEnable(game);
        }
    }

    public ImmutableList<IGame> getGames() {
        return ImmutableList.copyOf(games.values());
    }

    public IGame getGameByName(String name) {
        return games.getOrDefault(name, null);
    }

    public List<IGame> getGamesBySimilarName(String name) {
        try (Stream<IGame> gameStream = games.values().stream()) {
            try (Stream<IGame> filteredStream = gameStream.filter(game -> game.getName().contains(name))) {
                return filteredStream.collect(Collectors.toList());
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
