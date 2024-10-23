package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Balloon;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Trails;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class TrailManager {

    private final Map<Integer, Trails> trails;
    private final TomlConfig config;

    public TrailManager() {
        this.trails = new HashMap<>();
        this.config = WhaleSkyWars.getInstance().getTrailsConfig();
    }

    public void start() {
        config.getSection("trails").data().forEach((name, value) -> {
            int id = config.getInteger("trails." + name + ".id");
            int cost = config.getInteger("trails." + name + ".cost");
            String type = config.getString("trails." + name + ".type");
            trails.put(id, new Trails(name, id, cost, type));
        });
    }

    public void reload() {
        die();
        start();
    }

    public void die() {
        trails.clear();
    }

    public Trails getById(int id) {
        return trails.getOrDefault(id, null);
    }

    public boolean exists(int id) {
        return trails.containsKey(id);
    }

    public Map<Integer, Trails> getTrails() {
        return new HashMap<>(trails);
    }



}
