package ga.justreddy.wiki.whaleskywars.util.iridium.patterns;

import ga.justreddy.wiki.whaleskywars.util.iridium.IridiumColorAPI;

import java.util.regex.Matcher;

/**
 * @author JustReddy
 */
public class SolidPattern implements Pattern {

    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<SOLID:([0-9A-Fa-f]{6})>|#\\{([0-9A-Fa-f]{6})}");

    /**
     * Applies a rainbow pattern to the provided String.
     * Output might be the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with an applied pattern
     */
    @Override
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String color = matcher.group(1);
            if (color == null) color = matcher.group(2);

            string = string.replace(matcher.group(), IridiumColorAPI.getColor(color) + "");
        }
        return string;
    }
}
