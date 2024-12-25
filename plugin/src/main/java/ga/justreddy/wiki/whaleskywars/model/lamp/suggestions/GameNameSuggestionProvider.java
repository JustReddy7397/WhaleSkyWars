package ga.justreddy.wiki.whaleskywars.model.lamp.suggestions;

import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class GameNameSuggestionProvider implements SuggestionProvider<BukkitCommandActor> {
    @Override
    public @NotNull Collection<String> getSuggestions(@NotNull ExecutionContext<BukkitCommandActor> executionContext) {
        return WhaleSkyWars.getInstance().getGameManager().getGames().stream()
                .map(IGame::getName)
                .collect(Collectors.toList());
    }
}
