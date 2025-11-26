package ga.justreddy.wiki.whaleskywars.bungeecord.config;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import ga.justreddy.wiki.whaleskywars.bungeecord.Bungeecord;
import ga.justreddy.wiki.whaleskywars.shared.config.TomlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;

/**
 * @author JustReddy
 */
public abstract class BungeeTomlConfiguration extends TomlConfiguration {

    public BungeeTomlConfiguration(File folder, String path) {
        super(folder, path);
    }

    public BungeeTomlConfiguration(String name) {
        super(name);
    }

    public BungeeTomlConfiguration(File file) {
        super(file);
    }

    @Override
    public boolean reload() {
        file = new File(getAbsolutePath());
        // Create file if it doesn't exist
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) return false;
        }

        if (!file.exists()) {
            try (InputStream input = Bungeecord.getInstance().getResourceAsStream(file.getName())) {

                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }

            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
        }

        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            TomlMapper mapper = new TomlMapper();
            data = mapper.readValue(inputStream, LinkedHashMap.class);
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }
}
