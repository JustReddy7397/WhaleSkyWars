package ga.justreddy.wiki.whaleskywars.api.model.game;

import ga.justreddy.wiki.whaleskywars.api.SkyWarsProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * @author JustReddy
 */
public abstract class GameMode implements Listener {

    private IGame game;

    public GameMode() {
        Bukkit.getPluginManager().registerEvents(this, SkyWarsProvider.get().getPlugin());
    }

    public abstract String getIdentifier();

    public abstract String getDisplayName();

    public final void setGame(IGame game) {
        this.game = game;
    }

    public boolean isGameMode() {
        return getIdentifier().equals(game.getGameMode().getIdentifier());
    }

}
