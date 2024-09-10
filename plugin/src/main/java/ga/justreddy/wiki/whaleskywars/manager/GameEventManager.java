package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;
import ga.justreddy.wiki.whaleskywars.model.game.events.ChestRefillEvent;
import ga.justreddy.wiki.whaleskywars.model.game.events.DoomEvent;
import ga.justreddy.wiki.whaleskywars.model.game.events.EndingEvent;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;

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
        register(new ChestRefillEvent(true), new DoomEvent(true), new EndingEvent(true));
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jar")) continue;
            register(file);
        }
        TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &aLoaded " + events.size() + " events.");
        System.out.println(events);
        System.out.println(events.keySet());
    }

    private void register(File file) {
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

    public void register(GameEvent... event) {
        for (GameEvent gameEvent : event) {
            if (events.containsKey(gameEvent.getName())) {
                TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &cEvent with name " + gameEvent.getName() + " already exists.");
                continue;
            }
            events.put(gameEvent.getName(), gameEvent);
        }
    }

    public void die() {
        events.clear();
    }

    public GameEvent copyOf(String name) {

        GameEvent event = events.getOrDefault(name, null);
        if (event == null) {
            TextUtil.sendConsoleMessage("&7[&dWhaleSkyWars&7] &cEvent with name " + name + " does not exist.");
            return null;
        }
        return event.clone();
    }

}
