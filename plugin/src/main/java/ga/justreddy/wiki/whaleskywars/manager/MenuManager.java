package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.model.menu.Menu;
import ga.justreddy.wiki.whaleskywars.model.menu.menus.CosmeticMenu;
import ga.justreddy.wiki.whaleskywars.model.menu.menus.OptionsMenu;
import ga.justreddy.wiki.whaleskywars.model.menu.menus.OtherMenu;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author JustReddy
 */
public class MenuManager {

    private final Set<String> menus;
    private final File folder;
    public MenuManager() {
        this.menus = new HashSet<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "menus");
        if (!folder.exists()) folder.mkdir();
        start();
    }

    public void start() {
        File[] files = this.folder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (!file.getName().endsWith(".toml")) continue;
            String name = file.getName().replaceAll(".toml", "");
            this.menus.add(name);
        }

    }

    public Menu of(String identifier) {
        String menu = menus.stream()
                .filter(name -> {
                    String[] split = name.split("_");
                    System.out.println(split[0]);
                    return split[0].equalsIgnoreCase(identifier);
                }).findFirst().orElse(null);
        if (menu == null) return null;
        TempConfig config = new TempConfig(folder, menu + ".toml");
        String[] split = menu.split("_");
        String type = split[1];
        switch (type) {
            case "cosmetic":
                return new CosmeticMenu(config);
                case "options":
                return new OptionsMenu(config);
            case "other":
                return new OtherMenu(config);
            default:
                return null;
        }
    }

}
