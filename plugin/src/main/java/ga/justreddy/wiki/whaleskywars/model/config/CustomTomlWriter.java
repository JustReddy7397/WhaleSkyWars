package ga.justreddy.wiki.whaleskywars.model.config;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import org.apache.commons.lang.Validate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JustReddy
 */
@SuppressWarnings({"unchecked", "unused"})
public class CustomTomlWriter {

    private TomlWriter writer;
    private Map<String, Object> values;

    private File file = null;

    private CustomTomlWriter() {}

    public CustomTomlWriter(Toml toml, File file) {
        this.writer = new TomlWriter();
        this.values = toml.toMap() == null ? new HashMap<>() : new HashMap<>(toml.toMap());
        this.file = file;
    }

    public CustomTomlWriter(Toml toml) {
        this(toml, null);
    }

    public CustomTomlWriter(Map<String, Object> values) {
        this.writer = new TomlWriter();
        this.values = values;
    }

    public void set(String key, Object value) {
        Validate.notNull(key, "Key cannot be null");
        Validate.notNull(value, "Value cannot be null");
        String[] split = key.split("\\.");
        Map<String, Object> currentMap = values;

        for (int i = 0; i < split.length - 1; i++) {
            String section = split[i];
            if (!currentMap.containsKey(section) || !(currentMap.get(section) instanceof Map)) {
                currentMap.put(section, new HashMap<String, Object>());
            }
            currentMap = (Map<String, Object>) currentMap.get(section);
        }

        currentMap.put(split[split.length - 1], value);
    }

    public void set(String section, String key, Object value) {
        Validate.notNull(section, "Section cannot be null");
        Validate.notNull(key, "Key cannot be null");
        Validate.notNull(value, "Value cannot be null");
        Map<String, Object> sectionMap = new HashMap<>((Map<String, Object>) values.getOrDefault(section, new HashMap<>()));
        sectionMap.put(key, value);
        values.put(section, sectionMap);
    }

    public void remove(String section, String key) {
        Validate.notNull(section, "Section cannot be null");
        Validate.notNull(key, "Key cannot be null");
        Map<String, Object> sectionMap = new HashMap<>((Map<String, Object>) values.getOrDefault(section, new HashMap<>()));
        sectionMap.remove(key);
        values.put(section, sectionMap);
    }

    public void remove(String key) {
        Validate.notNull(key, "Key cannot be null");
        values.remove(key);
    }

    public void write() {
        Validate.notNull(file, "File cannot be null");
        write(file);
    }

    public void write(File file) {
        try {
            writer.write(values, file);
        } catch (IOException exception) {
            TextUtil.error(exception, "Failed to write to file " + file.getName(), false);
        }
    }

    public static CustomTomlWriter of(Toml toml, File file) {
        return new CustomTomlWriter(toml, file);
    }

    public static CustomTomlWriter of(Toml toml) {
        return new CustomTomlWriter(toml);
    }

    public static CustomTomlWriter of(Map<String, Object> values) {
        return new CustomTomlWriter(values);
    }

}
