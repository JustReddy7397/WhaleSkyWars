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
import java.util.stream.Collectors;

/**
 * @author JustReddy
 */
public class GameSign implements IGameSign {

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
        if (location.getBlock() == null) return;
        BlockState blockState = location.getBlock().getState();
        if (blockState == null) return;
        if (!(blockState instanceof Sign)) return;
        Sign sign = (Sign) blockState;
        TomlConfig config = WhaleSkyWars.getInstance().getSignsConfig();
        GameState state = game.getGameState();
        String gameStateLine = "";
        String materialName = "BLACK_TERRACOTTA";
        switch (state) {
            case WAITING:
                gameStateLine = config.getString("states.waiting");
                materialName = config.getString("blocks.waiting");
                break;
            case STARTING:
                gameStateLine = config.getString("states.starting");
                materialName = config.getString("blocks.starting");
                break;
            case PREGAME:
            case PLAYING:
                gameStateLine = config.getString("states.playing");
                materialName = config.getString("blocks.playing");
                break;
            case ENDING:
                gameStateLine = config.getString("states.ending");
                materialName = config.getString("blocks.ending");
                break;
            case RESTORING:
                gameStateLine = config.getString("states.restarting");
                materialName = config.getString("blocks.restarting");
                break;
            case DISABLED:
                gameStateLine = config.getString("states.disabled");
                materialName = config.getString("blocks.disabled");
                break;
        }
        String finalGameStateLine = gameStateLine;
        List<String> lines = config.getStringList("lines")
                .stream()
                .map(line -> TextUtil.color(line
                        .replaceAll("<map>", game.getDisplayName())
                        .replaceAll("<state>", finalGameStateLine)
                        .replaceAll("<players>", String.valueOf(game.getPlayers().size()))
                        .replaceAll("<max-players>", String.valueOf(game.getMaximumPlayers()))
                        .replaceAll("<mode>", game.getGameMode().getIdentifier()))).collect(Collectors.toList());

        for (int i = 0; i < lines.size(); i++) {
            sign.setLine(i, lines.get(i));
        }

        sign.update(true);

        String finalMaterialName = materialName;
        XMaterial.matchXMaterial(materialName)
                .ifPresentOrElse(mat -> {
                    XBlock.setType(getRelativeBlock(), mat);
                }, () -> {
                    WhaleSkyWars.getInstance().getLogger().warning("Invalid material name: " + finalMaterialName);
                });

    }

    @Override
    public void updateBungee(Object bungeeGame) {
        if (!(bungeeGame instanceof BungeeGame)) return;
        BungeeGame game = (BungeeGame) bungeeGame;
        if (location.getBlock() == null) return;
        BlockState blockState = location.getBlock().getState();
        if (blockState == null) return;
        if (!(blockState instanceof Sign)) return;
        Sign sign = (Sign) blockState;
        TomlConfig config = WhaleSkyWars.getInstance().getSignsConfig();
        GameState state = game.getGameState();
        String gameStateLine = "";
        String materialName = "BLACK_TERRACOTTA";
        switch (state) {
            case WAITING:
                gameStateLine = config.getString("states.waiting");
                materialName = config.getString("blocks.waiting");
                break;
            case STARTING:
                gameStateLine = config.getString("states.starting");
                materialName = config.getString("blocks.starting");
                break;
            case PREGAME:
            case PLAYING:
                gameStateLine = config.getString("states.playing");
                materialName = config.getString("blocks.playing");
                break;
            case ENDING:
                gameStateLine = config.getString("states.ending");
                materialName = config.getString("blocks.ending");
                break;
            case RESTORING:
                gameStateLine = config.getString("states.restarting");
                materialName = config.getString("blocks.restarting");
                break;
            case DISABLED:
                gameStateLine = config.getString("states.disabled");
                materialName = config.getString("blocks.disabled");
                break;
        }
        String finalGameStateLine = gameStateLine;
        List<String> lines = config.getStringList("lines")
                .stream()
                .map(line -> {
                    return line
                            .replaceAll("<map>", game.getName())
                            .replaceAll("<state>", finalGameStateLine)
                            .replaceAll("<players>", String.valueOf(game.getPlayers().size()))
                            .replaceAll("<max-players>", String.valueOf(game.getMaxPlayers()))
                            .replaceAll("<mode>", game.getGameMode().getIdentifier());
                }).collect(Collectors.toList());
        for (int i = 0; i < lines.size(); i++) {
            sign.setLine(i, lines.get(i));
        }

        sign.update(true);

        String finalMaterialName = materialName;
        XMaterial.matchXMaterial(materialName)
                .ifPresentOrElse(mat -> {
                    XBlock.setType(getRelativeBlock(), mat);
                }, () -> {
                    WhaleSkyWars.getInstance().getLogger().warning("Invalid material name: " + finalMaterialName);
                });
    }

    @Override
    public Block getRelativeBlock() {
        return WhaleSkyWars.getInstance().getNms().getRelativeBlock(getLocation());
    }
}
