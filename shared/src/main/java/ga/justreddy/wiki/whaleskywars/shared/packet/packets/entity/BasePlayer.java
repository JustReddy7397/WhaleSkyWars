package ga.justreddy.wiki.whaleskywars.shared.packet.packets.entity;

import ga.justreddy.wiki.whaleskywars.api.model.entity.IBasePlayer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * @author JustReddy
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BasePlayer implements IBasePlayer {

    private final UUID uniqueId;
    private final String name;

    public static BasePlayer of(UUID uniqueId, String name) {
        return new BasePlayer(uniqueId, name);
    }

}

