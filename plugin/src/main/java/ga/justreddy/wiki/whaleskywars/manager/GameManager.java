package ga.justreddy.wiki.whaleskywars.manager;

import com.google.common.collect.ImmutableList;
import com.moandjiezana.toml.Toml;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.model.game.Game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    }

    public ImmutableList<IGame> getGames() {
        return ImmutableList.copyOf(games.values());
    }

    public IGame getGameByName(String name) {
        return games.getOrDefault(name, null);
    }



}
