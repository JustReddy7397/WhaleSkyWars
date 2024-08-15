package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.team.IBalloon;
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
        config.getTable("balloons").entrySet()
                .forEach(entry -> {
                    String name = entry.getKey();
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

    public Map<Integer, Balloon> getBalloons() {
        return new HashMap<>(balloons);
    }


}
