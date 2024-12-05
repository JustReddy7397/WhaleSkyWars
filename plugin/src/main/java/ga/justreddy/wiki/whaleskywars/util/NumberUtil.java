package ga.justreddy.wiki.whaleskywars.util;

import redis.clients.jedis.BuilderFactory;

import java.awt.*;

/**
 * @author JustReddy
 */
public class NumberUtil {

    public static String toFormat(int seconds) {
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private static String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }


}
