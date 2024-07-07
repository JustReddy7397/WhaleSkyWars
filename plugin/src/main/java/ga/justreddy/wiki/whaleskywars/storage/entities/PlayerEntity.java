package ga.justreddy.wiki.whaleskywars.storage.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

import java.util.UUID;

/**
 * @author JustReddy
 */
@Data
@DatabaseTable(
        tableName = "whaleskywars_players"
)
public class PlayerEntity {

    @DatabaseField(columnName = "uuid", unique = true, dataType = DataType.UUID)
    private UUID uuid;

    @DatabaseField(columnName = "name", unique = true)
    private String name;

    @DatabaseField(columnName = "cosmetics", dataType = DataType.LONG_STRING)
    private String cosmetics;

}
