package ga.justreddy.wiki.whaleskywars.model.game;

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGame;
import ga.justreddy.wiki.whaleskywars.api.model.game.IGameSign;
import ga.justreddy.wiki.whaleskywars.api.model.game.enums.GameState;
import ga.justreddy.wiki.whaleskywars.model.config.TomlConfig;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class GameSign implements IGameSign {

    private static final Logger LOGGER = WhaleSkyWars.getInstance().getLogger();

    private final String id;
    private final Location location;

    public GameSign(String id, Location location) {
        this.id = id;
        this.location = location;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void update(IGame game) {
        Block block = location.getBlock();
        if (block == null) return;
        BlockState blockState = block.getState();
        if (!(blockState instanceof Sign)) return;
        Sign sign = (Sign) blockState;

        TomlConfig config = WhaleSkyWars.getInstance().getSignsConfig();
        GameState state = game.getGameState();
        String gameStateLine = getGameStateLine(config, state);
        String materialName = getMaterialName(config, state);

        List<String> lines = config.getStringList("lines").stream()
                .map(line -> TextUtil.color(line
                        .replace("<map>", game.getDisplayName())
                        .replace("<state>", gameStateLine)
                        .replace("<players>", String.valueOf(game.getPlayers().size()))
                        .replace("<max-players>", String.valueOf(game.getMaximumPlayers()))
                        .replace("<mode>", game.getGameMode().getIdentifier())))
                .collect(Collectors.toList());

        int signLines = sign.getLines().length;
        for (int i = 0; i < lines.size() && i < signLines; i++) {
            sign.setLine(i, lines.get(i));
        }
        sign.update(true);

        XMaterial.matchXMaterial(materialName)
                .ifPresent(mat -> XBlock.setType(getRelativeBlock(), mat));
    }

    @Override
    public void updateBungee(Object bungeeGame) {
        if (!(bungeeGame instanceof BungeeGame)) return;
        BungeeGame game = (BungeeGame) bungeeGame;

        Block block = location.getBlock();
        if (block == null) return;
        BlockState blockState = block.getState();
        if (!(blockState instanceof Sign)) return;
        Sign sign = (Sign) blockState;

        TomlConfig config = WhaleSkyWars.getInstance().getSignsConfig();
        GameState state = game.getGameState();
        String gameStateLine = getGameStateLine(config, state);
        String materialName = getMaterialName(config, state);

        List<String> lines = config.getStringList("lines").stream()
                .map(line -> TextUtil.color(line
                        .replace("<map>", game.getName())
                        .replace("<state>", gameStateLine)
                        .replace("<players>", String.valueOf(game.getPlayers().size()))
                        .replace("<max-players>", String.valueOf(game.getMaxPlayers()))
                        .replace("<mode>", game.getGameMode())))
                .collect(Collectors.toList());

        int signLines = sign.getLines().length;
        for (int i = 0; i < lines.size() && i < signLines; i++) {
            sign.setLine(i, lines.get(i));
        }
        sign.update(true);

        XMaterial.matchXMaterial(materialName)
                .ifPresent(mat -> XBlock.setType(getRelativeBlock(), mat));
    }

    @Override
    public Block getRelativeBlock() {
        return WhaleSkyWars.getInstance().getNms().getRelativeBlock(location);
    }

    private String getGameStateLine(TomlConfig config, GameState state) {
        if (state == null) return "";
        switch (state) {
            case WAITING:
                return config.getString("states.waiting");
            case STARTING:
                return config.getString("states.starting");
            case PREGAME:
            case PLAYING:
                return config.getString("states.playing");
            case ENDING:
                return config.getString("states.ending");
            case RESTORING:
                return config.getString("states.restarting");
            case DISABLED:
                return config.getString("states.disabled");
            default:
                return "";
        }
    }

    private String getMaterialName(TomlConfig config, GameState state) {
        if (state == null) return "BLACK_TERRACOTTA";
        switch (state) {
            case WAITING:
                return config.getString("blocks.waiting");
            case STARTING:
                return config.getString("blocks.starting");
            case PREGAME:
            case PLAYING:
                return config.getString("blocks.playing");
            case ENDING:
                return config.getString("blocks.ending");
            case RESTORING:
                return config.getString("blocks.restarting");
            case DISABLED:
                return config.getString("blocks.disabled");
            default:
                return "BLACK_TERRACOTTA";
        }
    }
}
