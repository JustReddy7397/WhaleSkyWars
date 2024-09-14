package ga.justreddy.wiki.whaleskywars.model.cosmetics.dances;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.VictoryDance;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class FireworkDance extends VictoryDance {

    private final Map<UUID, Integer> tasks = new HashMap<>();

    /**
     * Initializes a new instance of the VictoryDance class with the specified name,
     * ID, and cost.
     *
     * @param name The name of the victory dance.
     * @param id The unique identifier of the victory dance.
     * @param cost The cost of the victory dance.
     */
    public FireworkDance() {
        super("Fireworks", 0, 0);
    }

    @Override
    public void start(@NotNull IGamePlayer player) {

        final IGame game = player.getGame();

        final int duration = 10;

        int bukkitTask = new BukkitRunnable() {
            int counter = duration * 20 / 30;

            @Override
            public void run() {
                if (player.getGame() != game) {
                    cancel();
                    tasks.remove(player.getUniqueId());
                    return;
                }

                if (!game.getPlayers().contains(player)) {
                    cancel();
                    tasks.remove(player.getUniqueId());
                    return;
                }

                if (counter <= 0) {
                    cancel();
                    tasks.remove(player.getUniqueId());
                    return;
                }

                player.getPlayer().ifPresent(player -> {
                    Location location = player.getLocation().clone().add(0, 2, 0);
                    launchFirework(location);
                });
                counter--;

            }
        }.runTaskTimer(WhaleSkyWars.getInstance(), 0, 20).getTaskId();

        tasks.put(player.getUniqueId(), bukkitTask);

    }

    @Override
    public void stop(@NotNull IGamePlayer player) {
        int taskId = tasks.get(player.getUniqueId());
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public VictoryDance clone() {
        return new FireworkDance();
    }

    private void launchFirework(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.RED, Color.WHITE, Color.BLUE, Color.ORANGE, Color.AQUA)
                .flicker(true)
                .trail(true)
                .build();
        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(1);
        firework.setFireworkMeta(fireworkMeta);
        Bukkit.getScheduler().runTaskLater(WhaleSkyWars.getInstance(), firework::detonate, 2); // Delayed explosion after 2 ticks
    }

}
