package ga.justreddy.wiki.whaleskywars.model.hooks.hooks;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.hooks.IHook;
import org.bukkit.Bukkit;

/**
 * @author JustReddy
 */
public class SlimeWorldHook implements IHook {
    @Override
    public String getHookId() {
        return "SlimeWorldManager";
    }

    @Override
    public boolean canHook() {
        return Bukkit.getPluginManager().getPlugin("SlimeWorldManager") != null
                && WhaleSkyWars.getInstance().getSettingsConfig().getBoolean("modules.slimeworldmanager");
    }

    @Override
    public void hook(WhaleSkyWars plugin) {

    }
}
