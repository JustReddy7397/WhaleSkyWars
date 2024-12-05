package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.hooks.IHook;
import ga.justreddy.wiki.whaleskywars.model.hooks.hooks.DecentHologramsHook;
import ga.justreddy.wiki.whaleskywars.model.hooks.hooks.PlaceholderHook;
import ga.justreddy.wiki.whaleskywars.model.hooks.hooks.SlimeWorldHook;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class HookManager {

    private final Map<String, IHook> hooks;

    public HookManager() {
        this.hooks = new HashMap<>();
        registerHook(new PlaceholderHook(WhaleSkyWars.getInstance().getPlayerManager()));
        registerHook(new SlimeWorldHook());
        registerHook(new DecentHologramsHook());
        hookAll();
    }

    public void registerHook(IHook hook) {
        if (hook.canHook()) {
            hooks.put(hook.getHookId(), hook);
        }
    }

    public void hookAll() {
        hooks.values().forEach(hook -> hook.hook(WhaleSkyWars.getInstance()));
    }

    public boolean isHooked(String hookId) {
        return hooks.containsKey(hookId);
    }

}
