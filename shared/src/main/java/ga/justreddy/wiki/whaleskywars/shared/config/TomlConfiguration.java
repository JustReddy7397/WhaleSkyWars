package ga.justreddy.wiki.whaleskywars.shared.config;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * @author JustReddy
 */
public abstract class TomlConfiguration extends TomlConfigurationSection implements Configuration {

    protected File file;

    private final File folder;

    private final String path;

    public TomlConfiguration(File file) {
        super(new HashMap<>());
        this.file = file;
        this.folder = file.getParentFile();
        this.path = file.getName();
    }

    public TomlConfiguration(String name) {
        this(new File("src/main/resources/"), name);
    }

    public TomlConfiguration(File folder, String path) {
        super(new HashMap<>());
        this.folder = folder;
        this.path = path;
    }

    public String getAbsolutePath() {
        if (path == null || path.isEmpty()) {
            return folder.getAbsolutePath();
        }

        return folder.getAbsolutePath() + "/" + path;
    }

    public boolean save() {
        try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            TomlMapper mapper = new TomlMapper();
            mapper.writeValue(outputStream, data);
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

}
