package ga.justreddy.wiki.whaleskywars.manager;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.model.config.TempConfig;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.model.menu.Menu;
import ga.justreddy.wiki.whaleskywars.model.menu.menus.CosmeticMenu;
import ga.justreddy.wiki.whaleskywars.model.menu.menus.OptionsMenu;
import ga.justreddy.wiki.whaleskywars.model.menu.menus.OtherMenu;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
public class MenuManager {

    private final Map<String, TempConfig> menus;
    private final File folder;

    public MenuManager() {
        this.menus = new HashMap<>();
        this.folder = new File(WhaleSkyWars.getInstance().getDataFolder(), "menus");
        if (!folder.exists()) folder.mkdir();
        loadDefaults();
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
            if (menus.containsKey(name)) continue;
            TempConfig config = new TempConfig(folder, name + ".toml");
            this.menus.put(name, config);
        }

    }

    public Menu of(String identifier) {
        String menu = menus.keySet()
                .stream()
                .filter(name -> {
                    String[] split = name.split("_");
                    return split[0].equalsIgnoreCase(identifier);
                }).findFirst()
                .orElse(null);
        if (menu == null) return null;
        TempConfig config = menus.getOrDefault(menu, null);
        if (config == null) return null;
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

    private void loadDefaults() {
        new TomlConfig(folder, "shop_cosmetic.toml");
        menus.put("shop_cosmetic.toml", new TempConfig(folder, "shop_cosmetic.toml"));
    }

}
