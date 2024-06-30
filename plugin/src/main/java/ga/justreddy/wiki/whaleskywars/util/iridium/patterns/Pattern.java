package ga.justreddy.wiki.whaleskywars.util.iridium.patterns;

/**
 * @author JustReddy
 */
@FunctionalInterface
public interface Pattern {

    /**
     * Applies this pattern to the provided String.
     * Output might be the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with an applied pattern
     */
    String process(String string);

}
