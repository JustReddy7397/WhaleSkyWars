package ga.justreddy.wiki.whaleskywars.model.config;

import com.moandjiezana.toml.Toml;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author JustReddy
 */
@SuppressWarnings({"unchecked", "unused"})
public class CustomTomlReader {

    private final Map<String, Object> values;

    private CustomTomlReader(Toml toml) {
        this.values = toml.toMap();
    }

    private CustomTomlReader(Map<String, Object> values) {
        this.values = values;
    }

    public String getString(String key) {
        return (String) values.get(key);
    }

    public int getInt(String key) {
        return (int) values.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) values.get(key);
    }

    public double getDouble(String key) {
        return (double) values.get(key);
    }

    public long getLong(String key) {
        return (long) values.get(key);
    }

    public List<String> getList(String key) {
        return (List<String>) values.get(key);
    }

    public List<Integer> getIntList(String key) {
        return (List<Integer>) values.get(key);
    }

    public CustomTomlReader getTable(String key) {
        return of((Map<String, Object>) values.get(key));
    }

    public Map<String, Object> getTable() {
        return values;
    }

    public boolean isSet(String key) {
        String[] split = key.split("\\.");
        Map<String, Object> currentMap = new HashMap<>(values);
        for (String k : split) {
            if (currentMap.containsKey(k)) {
                Object value = currentMap.get(k);
                if (value instanceof Map) {
                    currentMap = (Map<String, Object>) value;
                } else {
                    // If it's the last key and it exists, return true
                    return true;
                }
            } else {
                return false;
            }
        }
        // If all keys exist and the last key points to a map, return true
        return true;
    }

    public Set<String> keys() {
        return values.keySet();
    }

    public Set<Map.Entry<String, Object>> entries() {
        return values.entrySet();
    }

    public static CustomTomlReader of(Toml toml) {
        return new CustomTomlReader(toml);
    }

    public static CustomTomlReader of(Map<String, Object> values) {
        return new CustomTomlReader(values);
    }

}
