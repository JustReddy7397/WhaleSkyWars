package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.api.model.game.GameEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class GameEventManager {

    private final Map<String, GameEvent> events;

    public GameEventManager() {
        this.events = new HashMap<>();
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
