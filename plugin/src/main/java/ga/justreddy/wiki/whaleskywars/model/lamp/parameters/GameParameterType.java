package ga.justreddy.wiki.whaleskywars.model.lamp.parameters;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class GameParameterType implements ParameterType<BukkitCommandActor, IGame> {

    @Override
    public IGame parse(@NotNull MutableStringStream stream,
                       @NotNull ExecutionContext<@NotNull BukkitCommandActor> context) {
        String gameName = stream.readString();
        IGame game = WhaleSkyWars.getInstance().getGameManager().getGameByName(gameName);
        if (game == null) {
            CommandErrorException ex = new CommandErrorException("No such game: " + gameName);
            TextUtil.error(ex, "No such game: " + gameName, false);
            return null;
        }
        return game;
    }

    @Override
    public @NotNull SuggestionProvider<@NotNull BukkitCommandActor> defaultSuggestions() {
        return (context) ->
                WhaleSkyWars.getInstance().getGameManager()
                        .getGames().stream()
                        .map(IGame::getName)
                        .collect(Collectors.toList());
    }
}
