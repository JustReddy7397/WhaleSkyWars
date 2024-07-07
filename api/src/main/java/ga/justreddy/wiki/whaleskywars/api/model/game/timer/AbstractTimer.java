package ga.justreddy.wiki.whaleskywars.api.model.game.timer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author JustReddy
 */
public abstract class AbstractTimer implements Runnable {

    private final int seconds;
    private final JavaPlugin plugin;
    protected int ticksExceed = 0;
    protected int task = -1;
    protected boolean started;

    public AbstractTimer(JavaPlugin plugin, int seconds) {
        this.plugin = plugin;
        this.seconds = seconds;
    }

    /**
     * The Proper method to use to start the timer is start()
     */

    @Override
    public void run() {
        if (task == -1) {
            throw new IllegalStateException("Timer should be started using the start() method");
        }
        if (ticksExceed == 0) {
            onEnd();
            stop();
            return;
        }
        --ticksExceed;
        onTick();
    }

    public void start() {
        ticksExceed = seconds;
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
        started = true;
    }

    public void stop() {
        if (task == -1) {
            throw new IllegalStateException("Timer is not active");
        }
        Bukkit.getScheduler().cancelTask(task);
        started = false;
    }

    protected abstract void onTick();

    protected abstract void onEnd();

    public int getTicksExceed() {
        return ticksExceed;
    }

    public int getSeconds() {
        return seconds;
    }
}
