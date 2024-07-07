package ga.justreddy.wiki.whaleskywars.model.creator;

import com.cryptomorin.xseries.XMaterial;
import fr.mrmicky.fastboard.FastBoard;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
public abstract class ScoreBoardCreator {

    private final FastBoard fastBoard;

    public ScoreBoardCreator(IGamePlayer gamePlayer) {
        fastBoard = new FastBoard(gamePlayer.getPlayer().get());;
    }

    public void setTitle(String title) {
        title = TextUtil.color(setPlaceHolders(title));
        if (XMaterial.supports(13)) {
            if (title.length()  > 256) {
                title = title.substring(0, 256);
            }
        } else {
            if (title.length() > 32) {
                title = title.substring(0, 32);
            }
        }
        fastBoard.updateTitle(title);
    }

    public void setLines(List<String> lines) {
        List<String> list = new ArrayList<>();
        for (String line : lines) {
            if (XMaterial.supports(13)) {
                if (line.length() > 256) {
                    line = line.substring(0, 256);
                }
            } else {
                if (line.length() > 32) {
                    line = line.substring(0, 32);
                }
            }
            list.add(TextUtil.color(setPlaceHolders(line)));
        }
        fastBoard.updateLines(list);
    }
    
    public abstract String setPlaceHolders(String line);

}
