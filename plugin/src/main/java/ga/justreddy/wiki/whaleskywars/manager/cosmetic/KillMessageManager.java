package ga.justreddy.wiki.whaleskywars.manager.cosmetic;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import ga.justreddy.wiki.whaleskywars.api.model.cosmetics.KillMessage;
import ga.justreddy.wiki.whaleskywars.model.cosmetics.killmessages.DefaultKillMessage;
import ga.justreddy.wiki.whaleskywars.util.ClassUtil;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class KillMessageManager {

    private final Map<Integer, KillMessage> killMessages;
    private final File folder = WhaleSkyWars.ADDONS_FOLDER;

    public KillMessageManager() {
        this.killMessages = new HashMap<>();
    }

    public void start() {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) continue;
            if (!file.getName().endsWith(".jar")) continue;
            register(file);
        }
        register(new DefaultKillMessage());
    }

    public void register(KillMessage killMessage) {
        killMessages.put(killMessage.getId(), killMessage);
    }

    public void register(File file) {
        try {
            List<Class<? extends Addon>> killMessages = ClassUtil.findAddons(file);
            for (Class<? extends Addon> classKillMessages : killMessages) {
                Addon killMessage = classKillMessages.getConstructor().newInstance();
                if (!(killMessage instanceof KillMessage)) continue;
                register((KillMessage) killMessage);
            }
        } catch (Exception ex) {
            TextUtil.error(ex, "Failed to load kill messages from: " + file.getName(), false);
        }
    }

    public void die() {
        killMessages.clear();
    }

    public boolean exists(int id) {
        return killMessages.containsKey(id);
    }

    public KillMessage of(int id) {
        return killMessages.getOrDefault(id, null);
    }

    public Map<Integer, KillMessage> getKillMessages() {
        return killMessages;
    }
}
