package ga.justreddy.wiki.whaleskywars;

import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import ga.justreddy.wiki.whaleskywars.version.worldedit.ISchematic;
import ga.justreddy.wiki.whaleskywars.version.worldedit.IWorldEdit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WhaleSkyWars extends JavaPlugin {

    private static WhaleSkyWars instance;

    public static WhaleSkyWars getInstance() {
        return instance;
    }

    // Version specific
    private INms nms;
    private IWorldEdit worldEdit;
    private ISchematic schematic;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public INms getNms() {
        return nms;
    }

    public IWorldEdit getWorldEdit() {
        return worldEdit;
    }

    public ISchematic getSchematic() {
        return schematic;
    }
}
