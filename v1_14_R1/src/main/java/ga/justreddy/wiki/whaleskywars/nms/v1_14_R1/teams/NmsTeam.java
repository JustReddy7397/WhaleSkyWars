package ga.justreddy.wiki.whaleskywars.nms.v1_14_R1.teams;

import ga.justreddy.wiki.whaleskywars.model.faketeams.FakeTeam;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_14_R1.Scoreboard;
import net.minecraft.server.v1_14_R1.ScoreboardTeam;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JustReddy
 */
public class NmsTeam {

    private ScoreboardTeam scoreboardTeam;

    public NmsTeam(Player player, FakeTeam fakeTeam) {
        String name = fakeTeam.getName();
        String prefix = fakeTeam.getPrefix();
        String suffix = fakeTeam.getSuffix();
        Scoreboard scoreboard = ((CraftPlayer)player).getScoreboard().getHandle();
        this.scoreboardTeam = new ScoreboardTeam(scoreboard, name);
        this.scoreboardTeam.setPrefix(IChatBaseComponent.ChatSerializer.b(toJson(prefix)));
        if (!suffix.isEmpty()) {
            this.scoreboardTeam.setSuffix(IChatBaseComponent.ChatSerializer.b(toJson(suffix)));
        }
        this.scoreboardTeam.getPlayerNameSet().add(player.getName());
    }

    public void send(Player player) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(scoreboardTeam, 0);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public void reset(Player player) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(scoreboardTeam, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        scoreboardTeam = null;
    }

    public String toJson(String text) {
        return "{\"text\":\"" + text + "\"}";
    }

}
