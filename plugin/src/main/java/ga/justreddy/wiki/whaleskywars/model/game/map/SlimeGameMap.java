package ga.justreddy.wiki.whaleskywars.model.game.map;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.CorruptedWorldException;
import com.grinderwolf.swm.api.exceptions.NewerFormatException;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import com.grinderwolf.swm.api.exceptions.WorldInUseException;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.map.IGameMap;
import ga.justreddy.wiki.whaleskywars.util.FileUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

/**
 * @author JustReddy
 */
public class SlimeGameMap implements IGameMap {

    private final SlimePlugin slimePlugin;
    private final SlimeLoader slimeLoader;

    private final File folder;

    public SlimeGameMap() {
        this.slimePlugin = WhaleSkyWars.getInstance().getSlimePlugin();
        this.slimeLoader = WhaleSkyWars.getInstance().getSlimeLoader();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder().getAbsolutePath() + "/data/slime");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    @Override
    public void onEnable(IGame game) {
        Bukkit.getScheduler().runTask(WhaleSkyWars.getInstance(), () -> {
            File originalWorldFolder = new File(
                    "slime_worlds/" + game.getName() + ".slime"
            );
            File newWorldFolder = new File(
                    folder.getAbsolutePath()
                            + "/" + game.getName() + ".slime"
            );

            try {
                FileUtil.copy(newWorldFolder, originalWorldFolder);
            } catch (IOException e) {
                TextUtil.error(e, "Failed to copy world " + game.getName(), false);
                return;
            }

            String name = game.getName();
            World world = Bukkit.getWorld(name);
            if (world != null) {
                game.init(world);
                return;
            }

            Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
                try {
                    SlimeWorld slimeWorld = slimePlugin
                            .loadWorld(slimeLoader, name, true, getPropertyMap());


                    Bukkit.getScheduler().runTask(WhaleSkyWars.getInstance(), () -> {
                        slimePlugin.generateWorld(slimeWorld);
                        World generatedWorld = Bukkit.getWorld(name);
                        // TODO set rules
                        if (generatedWorld == null) {
                            onDisable(game);
                            return;
                        }

                        Bukkit.getScheduler().runTask(WhaleSkyWars.getInstance(), () -> {
                            game.init(generatedWorld);
                        });
                    });

                } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                         WorldInUseException ex) {
                    onDisable(game);
                    TextUtil.error(ex, "Failed to load world " + name, false);
                }
            });

        });
    }

    @Override
    public void onRestart(IGame game) {
        Bukkit.getScheduler().runTask(WhaleSkyWars.getInstance(), () -> {
            Bukkit.unloadWorld(game.getName(), false);
            Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
                File originalWorldFolder = new File(
                        "slime_worlds/" + game.getName() + ".slime"
                );
                FileUtil.delete(originalWorldFolder);
                try {
                    if (slimeLoader.worldExists(game.getName())) {
                        slimeLoader.deleteWorld(game.getName());
                    }
                } catch (UnknownWorldException | IOException e) {
                    TextUtil.error(e, "Failed to delete world " + game.getName(), false);
                    return;
                }
                onEnable(game);
            });
        });
    }

    @Override
    public void onDisable(IGame game) {
        Bukkit.getScheduler().runTask(WhaleSkyWars.getInstance(), () -> {
            Bukkit.unloadWorld(game.getName(), false);
            Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
                File originalWorldFolder = new File(
                        "slime_worlds/" + game.getName() + ".slime"
                );
                FileUtil.delete(originalWorldFolder);
                try {
                    if (slimeLoader.worldExists(game.getName())) {
                        slimeLoader.deleteWorld(game.getName());
                    }
                } catch (UnknownWorldException | IOException e) {
                    TextUtil.error(e, "Failed to delete world " + game.getName(), false);
                }
            });
        });
    }

    private SlimePropertyMap getPropertyMap() {
        SlimePropertyMap map = new SlimePropertyMap();
        map.setInt(SlimeProperties.SPAWN_X, 0);
        map.setInt(SlimeProperties.SPAWN_Y, 30);
        map.setInt(SlimeProperties.SPAWN_Z, 0);
        map.setBoolean(SlimeProperties.PVP, true);
        map.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
        map.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
        map.setString(SlimeProperties.DIFFICULTY, "easy");
        return map;
    }

}
