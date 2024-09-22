package ga.justreddy.wiki.whaleskywars.model.action.actions;

import com.cryptomorin.xseries.XSound;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Sound;

/**
 * @author JustReddy
 */
public class SoundAction implements IAction {
    @Override
    public String getIdentifier() {
        return "SOUND";
    }

    @Override
    public void onAction(WhaleSkyWars plugin, IGamePlayer player, String data) {
        String[] split = data.split(";");
        XSound.matchXSound(split[0])
                .ifPresentOrElse(sound -> {
                    sound.play(player.getPlayer().get(), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
                }, () -> {
                    TextUtil.error(null, "Sound " + split[0] + " not found!", false);
                });
    }
}
