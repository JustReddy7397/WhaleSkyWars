package ga.justreddy.wiki.whaleskywars.model.hooks.hooks;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.hooks.IHook;
import org.bukkit.Bukkit;

/**
 * @author JustReddy
 */
public class DecentHologramsHook implements IHook {
    @Override
    public String getHookId() {
        return "DecentHolograms";
    }

    @Override
    public boolean canHook() {
        return Bukkit.getPluginManager().getPlugin("DecentHolograms") != null;
    }

    @Override
    public void hook(WhaleSkyWars plugin) {

    }
}
