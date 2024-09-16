package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameMode;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class GameModeManager {

    private final Map<String, GameMode> gameModes;
    private final File folder;
    public GameModeManager() {
        this.gameModes = new HashMap<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "modes");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void start() {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jar")) continue;
            register(file);
        }
    }

    private void register(File file) {
        try {
            List<Class<? extends GameMode>> modes = ClassUtil.findClasses(file, GameMode.class);
            for (Class<? extends GameMode> mode : modes) {
                GameMode gameMode = mode.getConstructor().newInstance();
                register(gameMode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void register(GameMode mode) {
        gameModes.put(mode.getIdentifier(), mode);
    }

    public void die() {
        gameModes.clear();
    }

    public GameMode of(String identifier) {
        return gameModes.getOrDefault(identifier, null);
    }



}
