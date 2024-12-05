package ga.justreddy.wiki.whaleskywars.model.faketeams;


import ga.justreddy.wiki.whaleskywars.util.TextUtil;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeTeam {

    private static int ID = 0;

    private final List<String> members = new ArrayList<>();
    private final String name;
    private String prefix = "";
    private String suffix = "";

    public FakeTeam(String prefix, String suffix, String name) {
        this.name = name == null ? "[TEAM:" + (++ID) + "]" : name + (++ID) + UUID.randomUUID().toString().substring(0,6 );
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public FakeTeam(String prefix, String suffix, int priority) {
        this.name = priority == -1 ? "[TEAM:" + (++ID) + "]" :
                getNameFromInput(priority) + "[" + UUID.randomUUID().toString()
                        .substring(0, 6) + "]";
        this.prefix = TextUtil.color(prefix);
        this.suffix = suffix;
    }

    private static String getNameFromInput(int input) {
        if (input < 0) {
            return "";
        }

        return  String.valueOf((char) ((input) + 65));
    }

    public void addMember(String player) {
        if (!members.contains(player)) {
            members.add(player);
        }
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public List<String> getMembers() {
        return members;
    }

    public boolean isSimilar(String prefix, String suffix) {
        return this.prefix.equals(prefix) && this.suffix.equals(suffix);
    }
}

