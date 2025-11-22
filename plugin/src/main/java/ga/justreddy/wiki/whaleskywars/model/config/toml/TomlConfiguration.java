package ga.justreddy.wiki.whaleskywars.model.config.toml;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

    @Override
    public boolean reload() {
        file = new File(getAbsolutePath());
        // Create file if it doesn't exist
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) return false;
        }


        if (!file.exists()) {

            try (InputStream input = WhaleSkyWars.getInstance().getResource(file.getName())) {

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
