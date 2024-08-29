package ga.justreddy.wiki.whaleskywars.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

/**
 * @author JustReddy
 */
public class Test<K, V> {

    private final Map<K, V> kv = new HashMap<>();

    public V get(K key) {
        return kv.get(key);
    }

    public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
        if (get(key) != null) {
            V value = function.apply(key);
            if (value != null) {
                kv.put(key, value);
                return value;
            }
        }
        return null;
    }

}
