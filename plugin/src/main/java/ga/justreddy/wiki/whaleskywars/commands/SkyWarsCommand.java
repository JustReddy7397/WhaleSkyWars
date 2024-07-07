package ga.justreddy.wiki.whaleskywars.commands;

import java.util.List;

/**
 * @author JustReddy
 */
public interface SkyWarsCommand {

    String getName();

    String getDescription();

    String getUsage();

    String getPermission();

    boolean isPlayerOnly();

    List<String> getAliases();

    void execute(CommandRunner runner, String[] args);

}
