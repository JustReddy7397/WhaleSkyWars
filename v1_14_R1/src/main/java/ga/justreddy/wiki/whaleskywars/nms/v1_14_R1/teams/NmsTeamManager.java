package ga.justreddy.wiki.whaleskywars.nms.v1_14_R1.teams;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author JustReddy
 */
public class NmsTeamManager {

    public static final Map<UUID, List<NmsTeam>> TEAMS = new HashMap<>();


    public static void sendTeam(Player player, NmsTeam team) {
        team.send(player);
    }

    public static void resetTeam(Player player, NmsTeam team) {
        team.reset(player);
    }

}
