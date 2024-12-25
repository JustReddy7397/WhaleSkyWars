package ga.justreddy.wiki.whaleskywars.commands;

import lombok.Setter;

import java.util.Optional;

/**
 * @author JustReddy
 */
@Setter
public class SkyWarsCommandHolder {

    private String name;
    private String description;
    private String usage;
    private String permission;

    public SkyWarsCommandHolder() {
        this(null, null, null, null);
    }

    public SkyWarsCommandHolder(String name, String description, String usage, String permission) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getUsage() {
        return Optional.ofNullable(usage);
    }

}
