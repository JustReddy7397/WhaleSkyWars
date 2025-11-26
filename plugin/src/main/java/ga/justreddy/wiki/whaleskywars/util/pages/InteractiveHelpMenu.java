package ga.justreddy.wiki.whaleskywars.util.pages;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommandHolder;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Revxrsal <3
 */
public class InteractiveHelpMenu {

    private static final int DEFAULT_ENTRIES_PER_PAGE = 7;

    private final String slashCommandColor;

    private final String commandNameColor;

    private final String usageColor;

    private final String toolTipLabelsColor;

    private final int pageSize;

    private InteractiveHelpMenu(String slashCommandColor,
                                String commandNameColor,
                                String usageColor,
                                String toolTipLabelsColor,
                                int pageSize) {
        this.slashCommandColor = slashCommandColor;
        this.commandNameColor = commandNameColor;
        this.usageColor = usageColor;
        this.toolTipLabelsColor = toolTipLabelsColor;
        this.pageSize = pageSize;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static InteractiveHelpMenu create() {
        return new Builder().build();
    }

    public TextComponent generate(SkyWarsCommandHolder command) {

        List<String> tooltips = createTooltip(command);
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(slashCommandColor + "/" + commandNameColor + command.getName().get());
        TextComponent component = new TextComponent(TextUtil.color(joiner.toString()));
        component = WhaleSkyWars.getInstance().getNms().setHoverText(component, String.join("\n", TextUtil.color(tooltips)));
        component.setClickEvent(
                new net.md_5.bungee.api.chat.ClickEvent(
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/" + command.getName().get()
                )
        );
        return component;
    }

    private List<String> createTooltip(SkyWarsCommandHolder command) {
        List<String> tooltip = new ArrayList<>();
        if (command.getDescription().isPresent())
            tooltip.add(toolTipLabelsColor + "&lDescription&r&f: " + command.getDescription().get());
        if (command.getUsage().isPresent()) {
            tooltip.add(toolTipLabelsColor + "&lUsage&r&f: " + usageColor + command.getUsage().get());
        }
        if (!tooltip.isEmpty())
            tooltip.add("");
        tooltip.add("Click to add to your chat box!");
        return tooltip;
    }

    public void sendInteractiveMenu(Player player, List<SkyWarsCommandHolder> commands, int page, SkyWarsCommandHolder command) {
        Paginator<SkyWarsCommandHolder> paginator = new Paginator<>(player.getUniqueId(), commands, pageSize);
        List<SkyWarsCommandHolder> pageCommands = paginator.getFromPage(page);
        if (pageCommands.isEmpty()) {
            TextUtil.sendMessage(player, "&cNo commands found on page " + page + "!");
            return;
        }

        sendTopBar(player, page, paginator.getTotalPages(), command);
        for (SkyWarsCommandHolder pageCommand : pageCommands) {
            TextComponent component = generate(pageCommand);
            WhaleSkyWars.getInstance().getNms().sendComponent(player, component);
        }
        sendBottomBar(player, page, paginator.getTotalPages());
    }

    private static void sendTopBar(Player player, int currentPage, int pageSize, SkyWarsCommandHolder command) {
        String previous = "/" + command.getName() + " " + (currentPage - 1);
        String next = "/" + command.getName() + " " + (currentPage + 1);
        TextComponent component = new TextComponent(TextUtil.color("&7&m------"));
        StringBuilder builder = new StringBuilder();
        TextComponent component22 = new TextComponent();
        if (currentPage <= 1) {
            builder.append("&7&l<<&r");
            component22 = WhaleSkyWars.getInstance().getNms().setHoverText(component22, c("&fThis is the first page"));
        } else {
            builder.append("&a&l<<&r");
            component22 = WhaleSkyWars.getInstance().getNms().setHoverText(component22, c("&fGo to page " + (currentPage - 1)));
            component22.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, previous));
        }

        component22.setText(TextUtil.color(builder.toString()));
        component.addExtra(component22);
        TextComponent addedComponent = new TextComponent();
        addedComponent.setText(TextUtil.color("&r &8| &r"));
        addedComponent.setHoverEvent(null);
        component.addExtra(addedComponent);

        TextComponent component2 = new TextComponent();
        StringBuilder builder2 = new StringBuilder();

        if (currentPage >= pageSize) {
            builder2.append("&7&l>>&r");
            component2 = WhaleSkyWars.getInstance().getNms().setHoverText(component2, c("&fThis is the last page&r"));
        } else {
            builder2.append("&a&l>>");
            component2 = WhaleSkyWars.getInstance().getNms().setHoverText(component2, c("&fGo to page " + (currentPage + 1) + "&r"));
            component2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, next));
        }

        component2.setText(TextUtil.color(builder2.toString()));
        component.addExtra(component2);
        TextComponent addedComponent2 = new TextComponent(TextUtil.color("&7&m------"));
        component.addExtra(addedComponent2);
        WhaleSkyWars.getInstance().getNms().sendComponent(player, component);
    }

    private static void sendBottomBar(Player player, int index, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("&7&m-------");
        if (index == pageSize) {
            builder.append("&d").append(index).append("&7/&d").append(pageSize);
        } else {
            builder.append("&e").append(index).append("&7/&d").append(pageSize);
        }
        builder.append("&7&m--------");
        TextComponent component = new TextComponent(TextUtil.color(builder.toString()));
        WhaleSkyWars.getInstance().getNms().sendComponent(player, component);
    }

    private static int coerce(int value, int min, int max) {
        return value < min ? min : Math.min(value, max);
    }

    private static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static class Builder {
        private String slashCommandColor = "&d";
        private String commandNameColor = "&d";
        private String parametersColor = "&7";
        private String tooltipLabelsColor = "&d";
        private int pageSize = DEFAULT_ENTRIES_PER_PAGE;

        public Builder slashCommandColor(String slashCommandColor) {
            this.slashCommandColor = slashCommandColor;
            return this;
        }

        public Builder commandNameColor(String commandNameColor) {
            this.commandNameColor = commandNameColor;
            return this;
        }

        public Builder parametersColor(String parametersColor) {
            this.parametersColor = parametersColor;
            return this;
        }

        public Builder tooltipLabelsColor(String tooltipLabelsColor) {
            this.tooltipLabelsColor = tooltipLabelsColor;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public InteractiveHelpMenu build() {
            return new InteractiveHelpMenu(this.slashCommandColor, this.commandNameColor, this.parametersColor, this.tooltipLabelsColor, this.pageSize);
        }

        public String toString() {
            return "InteractiveHelpMenu.Builder(slashCommandColor=" + this.slashCommandColor + ", commandNameColor=" + this.commandNameColor + ", parametersColor=" + this.parametersColor + ", tooltipLabelsColor=" + this.tooltipLabelsColor + ", pageSize=" + this.pageSize + ")";
        }
    }

}
