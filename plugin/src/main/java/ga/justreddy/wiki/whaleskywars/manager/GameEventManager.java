package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class GameEventManager {

    private final Map<String, GameEvent> events;
    private final File folder;

    public GameEventManager() {
        this.events = new HashMap<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "events");
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
    }

    public void start() {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jar")) continue;
            String name = file.getName().replace(".jar", "");
            register(name, file);
        }
    }

    private void register(String name, File file) {
        try {
            List<Class<? extends GameEvent>> events = ClassUtil.findClasses(file, GameEvent.class);
            for (Class<? extends GameEvent> event : events) {
                GameEvent gameEvent = event.getConstructor().newInstance();
                register(gameEvent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void register(GameEvent event) {
        events.put(event.getName(), event);
    }

    public void die() {
        events.clear();
    }

    public GameEvent copyOf(String name) {
        GameEvent event = events.getOrDefault(name, null);
        if (event == null) return null;
        return event.clone();
    }

}
