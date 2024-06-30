package ga.justreddy.wiki.whaleskywars.api;

/**
 * @author JustReddy
 */

public class SkyWarsProvider {

    private static SkyWarsAPI skyWarsAPI;

    public static SkyWarsAPI get() {
        return skyWarsAPI;
    }

    public static void setSkyWarsAPI(SkyWarsAPI skyWarsAPI) {
        if (SkyWarsProvider.skyWarsAPI != null) {
            throw new IllegalStateException("SkyWarsAPI is already set!");
        }
        SkyWarsProvider.skyWarsAPI = skyWarsAPI;
    }


}
