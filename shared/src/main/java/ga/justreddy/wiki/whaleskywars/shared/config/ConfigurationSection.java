package ga.justreddy.wiki.whaleskywars.shared.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author JustReddy
 */
public interface ConfigurationSection {

    Map<String, Object> data();

    String getBasePath();

    String getBasePath(String path);

    void set(Object value);

    void set(String key, Object value);

    void setInSection(String key, Object value);

    boolean isSet(String s);

    Object get(String key, Object alternative);

    Object get(String key);

    ConfigurationSection getSection(String key);

    Set<String> keys();

    Set<String> keys(String path);

    List<?> getList(String key, List<?> alternative);

    List<?> getList(String key);

    List<String> getStringList(String path, List<String> alternative);

    List<String> getStringList(String path);

    List<Integer> getIntegerList(String path, List<Integer> alternative);

    List<Integer> getIntegerList(String path);

    List<Boolean> getBooleanList(String path, List<Boolean> alternative);

    List<Boolean> getBooleanList(String path);

    List<Double> getDoubleList(String path, List<Double> alternative);

    List<Double> getDoubleList(String path);

    List<Float> getFloatList(String path, List<Float> alternative);

    List<Float> getFloatList(String path);

    List<Long> getLongList(String path, List<Long> alternative);

    List<Long> getLongList(String path);

    List<Short> getShortList(String path, List<Short> alternative);

    List<Short> getShortList(String path);

    List<Byte> getByteList(String path, List<Byte> alternative);

    List<Byte> getByteList(String path);

    String getString(String path, String alternative);

    String getString(String path);

    int getInteger(String path, int alternative);

    int getInteger(String path);

    boolean getBoolean(String path, boolean alternative);

    boolean getBoolean(String path);

    double getDouble(String path, double alternative);

    double getDouble(String path);

    float getFloat(String path, float alternative);

    float getFloat(String path);

    long getLong(String path, long alternative);

    long getLong(String path);

    short getShort(String path, short alternative);

    short getShort(String path);

    byte getByte(String path, byte alternative);

    byte getByte(String path);


}
