package ga.justreddy.wiki.whaleskywars.model.config.toml;


import java.util.*;

/**
 * @author JustReddy
 */
public class TomlConfigurationSection implements ConfigurationSection {

    private final ConfigurationSection baseSection;

    protected Map<String, Object> data;

    private final String rootPath;

    public TomlConfigurationSection(Map<String, Object> base) {
        this.data = base;
        this.baseSection = this;
        this.rootPath = null;
    }

    public TomlConfigurationSection(Map<String, Object> base, String path) {
        this.baseSection = new TomlConfigurationSection(base);
        this.rootPath = path;
        this.data = base;
        this.data = getSection(path).data();
    }

    public TomlConfigurationSection(Map<String, Object> base, String path, Map<String, Object> data) {
        this.baseSection = new TomlConfigurationSection(base);
        this.rootPath = path;
        this.data = data;
    }

    @Override
    public Map<String, Object> data() {
        return data;
    }

    @Override
    public String getBasePath() {
        return rootPath;
    }

    @Override
    public String getBasePath(String path) {
        if (this.rootPath == null) return path;
        if (path == null) return this.rootPath;
        return this.rootPath + "." + path;
    }

    @Override
    public void set(Object value) {
        baseSection.setInSection(getBasePath(), value);
    }

    @Override
    public void set(String key, Object value) {
        baseSection.setInSection(getBasePath(key), value);
    }

    @Override
    public void setInSection(String key, Object value) {
        if (key != null && key.contains(".")) {
            String base = key.split("\\.")[0];

            String remainingPath = key.substring(base.length() + 1);
            if (getSection(base) == null) {
                data.put(base, new HashMap<>());
            }

            ConfigurationSection section = new TomlConfigurationSection(
                    baseSection.data(),
                    getBasePath(base),
                    getSection(base).data()
            );

            section.setInSection(remainingPath, value);
            if (data.containsKey(base)) {
                data.replace(base, section.data());
            } else {
                data.put(base, section.data());
            }
            return;
        }
        if (value == null) {
            // If the path location is in this section
            if (key == null) {
                data = new HashMap<>();
                return;
            }

            this.data.remove(key);
            return;
        }
        if (data.containsKey(key)) {
            data.replace(key, value);
        } else {
            data.put(key, value);
        }
    }

    @Override
    public boolean isSet(String s) {
        return get(s) != null;
    }

    @Override
    public Object get(String key, Object alternative) {
        if (key.contains(".")) {

            String base = key.split("\\.")[0];


            String remainingPath = key.substring(base.length() + 1);

            if (this.getSection(base) == null) {
                this.data.put(base, new HashMap<>());
            }

            return getSection(base).get(remainingPath);

        }
        return data.getOrDefault(key, alternative);
    }

    @Override
    public Object get(String key) {
        return get(key, null);
    }

    @Override
    public ConfigurationSection getSection(String key) {
        if (key == null) return null;

        if (!(get(key) instanceof Map<?, ?>)) {
            return new TomlConfigurationSection(this.baseSection.data(), this.getBasePath(key), new HashMap<>());
        }

        Map<?, ?> unknownMap = (Map<?, ?>) get(key);

        Map<String, Object> knownMap = new LinkedHashMap<>();

        for (Map.Entry<?, ?> entry : unknownMap.entrySet()) {
            knownMap.put(entry.getKey().toString(), entry.getValue());
        }

        return new TomlConfigurationSection(baseSection.data(), this.getBasePath(key), knownMap);
    }

    @Override
    public Set<String> keys() {
        if (data == null) return null;
        return new HashSet<>(data.keySet());
    }

    @Override
    public Set<String> keys(String path) {
        return getSection(path).keys();
    }

    @Override
    public List<?> getList(String key, List<?> alternative) {
        Object object = get(key);
        return object instanceof List<?> ? (List<?>) object : alternative;
    }

    @Override
    public List<?> getList(String key) {
        return getList(key, new ArrayList<>());
    }

    @Override
    public List<String> getStringList(String path, List<String> alternative) {
        List<String> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((String) object);
        }
        return list;
    }

    @Override
    public List<String> getStringList(String path) {
        return getStringList(path, null);
    }

    @Override
    public List<Integer> getIntegerList(String path, List<Integer> alternative) {
        List<Integer> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((Integer) object);
        }
        return list;
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return getIntegerList(path, null);
    }

    @Override
    public List<Boolean> getBooleanList(String path, List<Boolean> alternative) {
        List<Boolean> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((Boolean) object);
        }
        return list;
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return getBooleanList(path, null);
    }

    @Override
    public List<Double> getDoubleList(String path, List<Double> alternative) {
        List<Double> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((Double) object);
        }
        return list;
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return getDoubleList(path, null);
    }

    @Override
    public List<Float> getFloatList(String path, List<Float> alternative) {
        List<Float> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((Float) object);
        }
        return list;
    }

    @Override
    public List<Float> getFloatList(String path) {
        return getFloatList(path, null);
    }

    @Override
    public List<Long> getLongList(String path, List<Long> alternative) {
        List<Long> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((Long) object);
        }
        return list;
    }

    @Override
    public List<Long> getLongList(String path) {
        return getLongList(path, null);
    }

    @Override
    public List<Short> getShortList(String path, List<Short> alternative) {
        List<Short> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((Short) object);
        }
        return list;
    }

    @Override
    public List<Short> getShortList(String path) {
        return getShortList(path, null);
    }

    @Override
    public List<Byte> getByteList(String path, List<Byte> alternative) {
        List<Byte> list = new ArrayList<>();
        List<?> unknownList = getList(path);
        for (Object object : unknownList) {
            list.add((Byte) object);
        }
        return list;
    }

    @Override
    public List<Byte> getByteList(String path) {
        return getByteList(path, null);
    }

    @Override
    public String getString(String path, String alternative) {
        Object object = get(path);
        return object instanceof String ? (String) object : alternative;
    }

    @Override
    public String getString(String path) {
        return getString(path, null);
    }

    @Override
    public int getInteger(String path, int alternative) {
        Object object = get(path);
        return object instanceof Long ? ((Long)object).intValue() : alternative;
    }

    @Override
    public int getInteger(String path) {
        return getInteger(path, 0);
    }

    @Override
    public boolean getBoolean(String path, boolean alternative) {
        Object object = get(path);
        return object instanceof Boolean ? (boolean) object : alternative;
    }

    @Override
    public boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    @Override
    public double getDouble(String path, double alternative) {
        Object object = get(path);
        if (object instanceof Double) {
            return (double) object;
        }
        if (object instanceof Float) {
            return ((Float)object).doubleValue();
        }
        return alternative;
    }

    @Override
    public double getDouble(String path) {
        return getDouble(path, 0.0);
    }

    @Override
    public float getFloat(String path, float alternative) {
        Object object = get(path);
        if (object instanceof Float) {
            return (float) object;
        }
        if (object instanceof Double) {
            return ((Double)object).floatValue();
        }
        return alternative;
    }

    @Override
    public float getFloat(String path) {
        return getFloat(path, 0.0f);
    }

    @Override
    public long getLong(String path, long alternative) {
        Object object = get(path);
        return object instanceof Long ? (long) object : alternative;
    }

    @Override
    public long getLong(String path) {
        return getLong(path, 0L);
    }

    @Override
    public short getShort(String path, short alternative) {
        Object object = get(path);
        if (object instanceof Short) {
            return (short) object;
        }
        if (object instanceof Long) {
            return ((Long)object).shortValue();
        }
        return alternative;
    }

    @Override
    public short getShort(String path) {
        return getShort(path, (short) 0);
    }

    @Override
    public byte getByte(String path, byte alternative) {
        Object object = get(path);
        if (object instanceof Byte) {
            return (byte) object;
        }
        if (object instanceof Long) {
            return ((Long)object).byteValue();
        }
        return alternative;
    }

    @Override
    public byte getByte(String path) {
        return getByte(path, (byte) 0);
    }
}
