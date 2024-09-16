package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.Balloon;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class BalloonManager {

    private final Map<Integer, Balloon> balloons;
    private final TomlConfig config;

    public BalloonManager() {
        this.balloons = new HashMap<>();
        this.config = WhaleSkyWars.getInstance().getBalloonsConfig();
    }

    public void start() {
        config.getSection("balloons").data().forEach((name, value) -> {
            int id = config.getInteger("balloons." + name + ".id");
            int cost = config.getInteger("balloons." + name + ".cost");
            String texture = config.getString("balloons." + name + ".texture");
            Balloon balloon = new Balloon(name, id, cost, texture);
            balloons.put(id, balloon);
        });
    }

    public void die() {
        balloons.clear();
    }

    public Balloon getById(int id) {
        return balloons.getOrDefault(id, null);
    }

    public boolean exists(int id) {
        return balloons.containsKey(id);
    }

    public Map<Integer, Balloon> getBalloons() {
        return new HashMap<>(balloons);
    }


}
