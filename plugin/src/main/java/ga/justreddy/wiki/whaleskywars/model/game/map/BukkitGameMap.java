package ga.justreddy.wiki.whaleskywars.model.game.map;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.map.IGameMap;
import ga.justreddy.wiki.whaleskywars.util.FileUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

/**
 * @author JustReddy
 */
public class BukkitGameMap implements IGameMap {

    private final File folder;

    public BukkitGameMap() {
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder()
                .getAbsolutePath() + "/data/worlds");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }


    @Override
    public void onEnable(IGame game) {

        File originalWorldFolder = new File(
                Bukkit.getWorldContainer(), game.getName()
        );

        File newWorldFolder = new File(
                folder, game.getName()
        );

        try {
            FileUtil.copy(newWorldFolder, originalWorldFolder);
        } catch (Exception e) {
            TextUtil.error(e, "Failed to copy world " + game.getName(), false);
            return;
        }

        String name = game.getName();

        World world = Bukkit.getWorld(name);
        if (world != null) {
            game.init(world);
            return;
        }

        world = WhaleSkyWars.getInstance().getWorldManager().createNewWorld(game.getName());

        if (world != null) {
            game.init(world);
        }


    }

    @Override
    public void onRestart(IGame game) {
        onDisable(game);
        Bukkit.getScheduler().runTaskLater(
                WhaleSkyWars.getInstance(),
                () -> onEnable(game),
                20L * 5);
    }

    @Override
    public void onDisable(IGame game) {
        World world = Bukkit.getWorld(game.getName());
        if (world == null) {
            return;
        }
        Bukkit.unloadWorld(world, false);
        File originalWorldFolder = new File(
                Bukkit.getWorldContainer(), game.getName()
        );

        FileUtil.delete(originalWorldFolder);
    }

}
