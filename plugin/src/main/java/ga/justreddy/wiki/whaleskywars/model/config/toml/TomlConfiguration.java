package ga.justreddy.wiki.whaleskywars.model.config.toml;

import com.google.gson.GsonBuilder;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
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
            Toml toml = new Toml();
            data = toml.read(inputStream).toMap();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean save() {
        TomlWriter writer = new TomlWriter();
        try (Writer fileWriter = new FileWriter(new File(folder, file.getName()))) {
            writer.write(data, fileWriter);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
