package ga.justreddy.wiki.whaleskywars.model.faketeams;

import lombok.experimental.FieldDefaults;

/**
 * Not my piece of code, I don't like packets ):
 */
@FieldDefaults(makeFinal = true)
public enum TeamData {


    v1_8("g", "c", "d", "a", "h", "i", "b"),
    v1_9("h", "c", "d", "a", "i", "j", "b"),
    v1_10("h", "c", "d", "a", "i", "j", "b"),
    v1_11("h", "c", "d", "a", "i", "j", "b"),
    v1_12("h", "c", "d", "a", "i", "j", "b");

    private String members;
    private String prefix;
    private String suffix;
    private String teamName;
    private String paramInt;
    private String packOption;
    private String displayName;

    TeamData(String members, String prefix, String suffix, String teamName, String paramInt, String packOption, String displayName) {
        this.members = members;
        this.prefix = prefix;
        this.suffix = suffix;
        this.teamName = teamName;
        this.paramInt = paramInt;
        this.packOption = packOption;
        this.displayName = displayName;
    }

    public String getMembers() {
        return members;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getParamInt() {
        return paramInt;
    }

    public String getPackOption() {
        return packOption;
    }

    public String getDisplayName() {
        return displayName;
    }

}
