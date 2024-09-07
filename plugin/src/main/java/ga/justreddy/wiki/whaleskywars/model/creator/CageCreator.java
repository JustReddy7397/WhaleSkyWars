package ga.justreddy.wiki.whaleskywars.model.creator;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import ga.justreddy.wiki.whaleskywars.version.worldedit.ISchematic;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class CageCreator {

    private final Map<UUID, String> data;

    public CageCreator() {
        this.data = new HashMap<>();
    }

    public void create(IGamePlayer player, String name) {
        final UUID uuid = player.getUniqueId();

        if (data.containsKey(uuid)) {
            player.sendMessage("&cYou are already creating a cage!");
            return;
        }

        File folder = getFile(name);
        File file = getFile(name, name + ".toml");
        if (!file.exists() && !folder.exists()) {
            try {
                folder.mkdir();
                file.createNewFile();
            } catch (IOException e) {
                TextUtil.error(e, "Failed to create cage file " + file.getName(), false);
                return;
            }
        } else {
            // TODO
            player.sendMessage("&cA cage with the name " + name + " already exists!");
            return;
        }



        TempConfig config = new TempConfig(folder, name + ".toml");

        config.set("name", name);
        config.set("id", data.size());
        config.set("cost", 0);
        config.save();

        player.sendMessage("&aSuccessfully created cage " + name);
        this.data.put(uuid, name);
    }

    public void setCost(IGamePlayer player, int cost) {
        final UUID uuid = player.getUniqueId();

        if (!data.containsKey(uuid)) {
            player.sendMessage("&cYou are not creating a cage!");
            return;
        }

        String name = Objects.requireNonNull(data.get(uuid));

        File file = getFile(name, name + ".toml");
        if (!file.exists()) {
            player.sendMessage("&cA cage with the name " + name + " does not exist!");
            return;
        }

        File folder = new File(WhaleSkyWars.getInstance().getDataFolder().getAbsolutePath() + "/cages/" + name);

        TempConfig config = new TempConfig(folder, name + ".toml");

        config.set("cost", cost);

        config.save();
        player.sendMessage("&aSuccessfully set cost of cage " + name + " to " + cost);
    }

    public void saveSmallStructure(IGamePlayer player) {
        final UUID uuid = player.getUniqueId();

        if (!data.containsKey(uuid)) {
            player.sendMessage("&cYou are not creating a cage!");
            return;
        }

        String name = Objects.requireNonNull(data.get(uuid));

        ISchematic schematic = WhaleSkyWars.getInstance().getSchematic();


        File file = getFile(name, "small" + schematic.getPrefix());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                TextUtil.error(e, "Failed to create cage file " + file.getName(), false);
                return;
            }
        }

        Player bukkitPlayer = player.getPlayer().orElse(null);

        if (bukkitPlayer == null) return;

        Location location = bukkitPlayer.getLocation();
        File smallSchematic = new File(WhaleSkyWars.getInstance().getDataFolder(), "cages/" + name + "/small" + schematic.getPrefix());

        schematic.save(smallSchematic, location.clone().add(-5.0, -1.0, -5.0)
                , location.clone().add(5.0, 5.0, 5.0));

        player.sendMessage("&aSuccessfully saved small structure of cage " + name);

    }

    public void saveBigStructure(IGamePlayer player) {
        final UUID uuid = player.getUniqueId();

        if (!data.containsKey(uuid)) {
            player.sendMessage("&cYou are not creating a cage!");
            return;
        }

        String name = Objects.requireNonNull(data.get(uuid));

        ISchematic schematic = WhaleSkyWars.getInstance().getSchematic();


        File file = getFile(name, "big" + schematic.getPrefix());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                TextUtil.error(e, "Failed to create cage file " + file.getName(), false);
                return;
            }
        }

        Player bukkitPlayer = player.getPlayer().orElse(null);

        if (bukkitPlayer == null) return;

        Location location = bukkitPlayer.getLocation();
        File bigSchematic = new File(WhaleSkyWars.getInstance().getDataFolder(), "cages/" + name + "/big" + schematic.getPrefix());

        schematic.save(bigSchematic, location.clone().add(-5.0, -1.0, -5.0)
                , location.clone().add(5.0, 5.0, 5.0));

        player.sendMessage("&aSuccessfully saved small structure of cage " + name);

    }

    public void finish(IGamePlayer player) {
        final UUID uuid = player.getUniqueId();

        if (!data.containsKey(uuid)) {
            player.sendMessage("&cYou are not creating a cage!");
            return;
        }

        String name = Objects.requireNonNull(data.get(uuid));

        File file = getFile(name, name + ".toml");
        if (!file.exists()) {
            player.sendMessage("&cA cage with the name " + name + " does not exist!");
            return;
        }

        File dataFile = getFile(name, name + ".toml");
        File smallSchematic = getFile(name, "small" + WhaleSkyWars.getInstance().getSchematic().getPrefix());
        File bigSchematic = getFile(name, "big" + WhaleSkyWars.getInstance().getSchematic().getPrefix());

        if (!dataFile.exists() || !smallSchematic.exists() || !bigSchematic.exists()) {
            player.sendMessage("&cCage " + name + " is not finished!");
            return;
        }

        player.sendMessage("&aSuccessfully finished cage " + name);
        this.data.remove(uuid);
    }

    public File getFile(String name) {
        return new File(WhaleSkyWars.getInstance().getDataFolder(), "cages/" + name);
    }

    public File getFile(String name, String fileName) {
        return new File(WhaleSkyWars.getInstance().getDataFolder().getAbsolutePath() + "/cages/" + name, fileName);
    }

}
