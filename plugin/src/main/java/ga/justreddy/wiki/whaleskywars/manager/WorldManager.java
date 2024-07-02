package ga.justreddy.wiki.whaleskywars.manager;

import com.grinderwolf.swm.api.exceptions.*;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.util.FileUtil;
import ga.justreddy.wiki.whaleskywars.version.nms.INms;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

/**
 * @author JustReddy
 */
public class WorldManager {

    private final INms nms = WhaleSkyWars.getInstance().getNms();

    public World createNewWorld(String name) {
        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.generateStructures(false);
        worldCreator.generator(nms.getChunkGenerator());
        World world = worldCreator.createWorld();
        world.setDifficulty(Difficulty.EASY);
        world.setSpawnFlags(true, true);
        world.setPVP(true);
        world.setStorm(false);
        world.setThundering(false);
        world.setKeepSpawnInMemory(false);
        world.setAutoSave(false);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setSpawnLocation(0, 30, 0);
        return world;
    }

    @SneakyThrows
    public void copyWorld(World world) {
        File worldFolder = new File(Bukkit.getWorldContainer().getParent(), world.getName());
        File mapFolder = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/worlds/" + world.getName());
        if (mapFolder.exists()) {
            FileUtil.delete(mapFolder);
        }
        FileUtil.copy(worldFolder, mapFolder);
    }

    @SneakyThrows
    public void copyWorld(String name) {
        File worldFolder = new File(Bukkit.getWorldContainer().getParent(), name);
        File mapFolder = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/worlds/" + name);
        if (mapFolder.exists()) {
            FileUtil.delete(mapFolder);
        }
        FileUtil.copy(worldFolder, mapFolder);
    }

    @SneakyThrows
    public void copySlimeWorld(String name) {
        File worldFolder = new File("slime_worlds/", name + ".slime");
        File mapFolder = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/slime/" + name + ".slime");
        if (mapFolder.exists()) {
            FileUtil.delete(mapFolder);
        }
        FileUtil.copy(worldFolder, mapFolder);

    }

    @SneakyThrows
    public void copySlimeWorldButDontDelete(String name, String newName) {
        File mapToCopy = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/slime/" + name + ".slime");
        if (!mapToCopy.exists()) return;
        File newMap = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/slime/" + newName + ".slime");
        FileUtil.copy(mapToCopy, newMap);
        File worldToCreate = new File("slime_worlds/", newName + ".slime");
        FileUtil.copy(mapToCopy, worldToCreate);
    }

    @SneakyThrows
    public void copyWorldButDontDelete(String name, String newName) {
        File mapToCopy = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/worlds/" + name);
        if (!mapToCopy.exists()) return;
        File newMap = new File(WhaleSkyWars.getInstance().getDataFolder(), "/data/worlds/" + newName);
        FileUtil.copy(mapToCopy, newMap);

        File worldToCreate = new File(Bukkit.getWorldContainer().getParentFile(), newName);
        FileUtil.copy(mapToCopy, worldToCreate);
        createNewWorld(newName);
    }

    public void removeSlimeWorld(World world) {
        if (world == null) return;
        Bukkit.unloadWorld(world, false);
        try {
            WhaleSkyWars.getInstance().getSlimeLoader().deleteWorld(world.getName());
        } catch (UnknownWorldException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importWorldToSlime(String name) {
        File bukkitWorld = new File(WhaleSkyWars.getInstance().getDataFolder() + "/data/worlds/", name);
        if (bukkitWorld == null) {
            System.out.println("invalid world ?");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(WhaleSkyWars.getInstance(), () -> {
            try {
                WhaleSkyWars.getInstance().getSlimePlugin()
                        .importWorld(bukkitWorld, name, WhaleSkyWars.getInstance().getSlimeLoader());
            } catch (WorldAlreadyExistsException | InvalidWorldException |
                     WorldLoadedException | WorldTooBigException |
                     IOException e) {
                throw new RuntimeException(e);
            }
            copySlimeWorld(name);
        });
    }

    public void removeWorld(World world) {
        if (world == null) return;
        Bukkit.unloadWorld(world, false);
        File worldFolder = new File(Bukkit.getWorldContainer().getParent(), world.getName());
        if (worldFolder == null) return;
        FileUtil.delete(worldFolder);
    }

}
