package ga.justreddy.wiki.whaleskywars.manager;

import com.avaje.ebean.annotation.NamedUpdate;
import com.avaje.ebeaninternal.server.query.LimitOffsetList;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.entity.IGamePlayer;
import ga.justreddy.wiki.whaleskywars.model.action.IAction;
import ga.justreddy.wiki.whaleskywars.model.action.actions.*;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JustReddy
 */
public class ActionManager {

    private Map<String, IAction> actions;

    public ActionManager() {
        this.actions = new HashMap<>();
        load();
    }

    public void load() {
        registerActions(
                new CloseInventoryAction(),
                new ConsoleCommandAction(),
                new JoinAction(),
                new LobbyAction(),
                new MessageAction(),
                new OpenInventoryAction()
        );
    }

    public void registerActions(IAction... actions) {
        Arrays.stream(actions).forEach(action -> this.actions.put(action.getIdentifier(), action));
    }

    public IAction of(String identifier) {
        return actions.getOrDefault(identifier, null);
    }

    public void onAction(IGamePlayer player, List<String> actions) {
        actions.forEach(action -> {
            String actionName = StringUtils.substringBetween(action, "[", "]");
            IAction iAction = of(actionName);
            if (iAction == null) {
                TextUtil.error(null, "Action " + actionName + " not found!", false);
                return;
            }

            action = action.contains(" ") ? actionName.split(" ", 2)[1] : "";
            if (player.getPlayer().isPresent()) {
                Player bukkitPlayer = player.getPlayer().get();
                action = PlaceholderAPI.setPlaceholders(bukkitPlayer, action);
            }
            iAction.onAction(WhaleSkyWars.getInstance(), player, action);
        });
    }


}
