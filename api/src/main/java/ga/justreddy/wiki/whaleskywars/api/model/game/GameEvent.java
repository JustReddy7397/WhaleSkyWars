package ga.justreddy.wiki.whaleskywars.api.model.game;

/**
 * @author JustReddy
 */
public abstract class GameEvent implements Cloneable {

    protected final String name;
    protected boolean enabled;
    protected int timer;

    public GameEvent(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public abstract void onEnable(IGame game);

    public abstract void onTick(IGame game);

    public abstract void onDisable(IGame game);

    public abstract GameEvent clone();

    public void update() {
        if (timer > 0) timer--;
    }

    public boolean isEnded() {
        return timer <= 0;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected Object cloneCommon() throws CloneNotSupportedException {
        return super.clone();
    }


}
