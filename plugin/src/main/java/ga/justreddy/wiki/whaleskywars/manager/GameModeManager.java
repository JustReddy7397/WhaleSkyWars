package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameMode;
import ga.justreddy.wiki.whaleskywars.model.game.modes.SwapGameMode;
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
    private final File folder = WhaleSkyWars.ADDONS_FOLDER;
    public GameModeManager() {
        this.gameModes = new HashMap<>();
    }

    public void start() {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jar")) continue;
            register(file);
        }
        register(new SwapGameMode());
    }

    private void register(File file) {
        try {
            List<Class<? extends Addon>> modes = ClassUtil.findAddons(file);
            for (Class<? extends Addon> mode : modes) {
                Addon addon = mode.getConstructor().newInstance();
                if (!(addon instanceof GameMode)) continue;
                register((GameMode) addon);
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
