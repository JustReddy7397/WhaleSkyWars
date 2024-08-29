package ga.justreddy.wiki.whaleskywars.model.hooks;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;

/**
 * @author JustReddy
 */
public interface IHook {

    String getHookId();

    boolean canHook();

    void hook(WhaleSkyWars plugin);

}
