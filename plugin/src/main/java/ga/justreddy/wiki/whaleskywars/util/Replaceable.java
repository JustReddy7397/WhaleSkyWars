package ga.justreddy.wiki.whaleskywars.util;

import lombok.Getter;

/**
 * @author JustReddy
 */
@Getter
public class Replaceable {

    private final String key;
    private final String value;

    private Replaceable(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Replaceable of(String key, String value) {
        return new Replaceable(key, value);
    }

}
