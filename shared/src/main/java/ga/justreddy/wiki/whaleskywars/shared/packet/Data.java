package ga.justreddy.wiki.whaleskywars.shared.packet;

import ga.justreddy.wiki.whaleskywars.shared.json.JsonSerializable;

/**
 * @author JustReddy
 */
public class Data extends JsonSerializable {

    private final Object data;

    public Data(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
