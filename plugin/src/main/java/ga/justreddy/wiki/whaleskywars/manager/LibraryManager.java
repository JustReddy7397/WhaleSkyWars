package ga.justreddy.wiki.whaleskywars.manager;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import ga.justreddy.wiki.whaleskywars.WhaleSkyWars;

/**
 * @author JustReddy
 */
public class LibraryManager {

    public void loadDependencies() {
        BukkitLibraryManager libraryManager = new BukkitLibraryManager(WhaleSkyWars.getInstance());
        libraryManager.addMavenCentral();
        libraryManager.addJitPack();
        Library sqlite = create("org.xerial", "sqlite-jdbc", "3.34.0", "sqlite");
        Library mongo_driver = create("org.mongodb", "mongodb-driver", "3.12.8", "MongoDriver");
        Library mongo_core = create("org.mongodb", "mongodb-driver-core", "4.2.3", "MongoCore");
        Library mongo_bson = create("org.mongodb", "bson", "4.2.3", "MongoBson");
        Library rabbit = create("com.rabbitmq", "amqp-client", "5.12.0", "RabbitMQ");
        Library xseries = create("com.github.cryptomorin", "XSeries", "9.4.0", "XSeries");
        Library redis = create("redis.clients", "jedis", "5.0.0", "Jedis");
        Library nbt_api = Library.builder()
                .groupId("de.tr7zw")
                .artifactId("item-nbt-api")
                .version("2.12.4")
                .loaderId("NBTAPI")
                .repository("https://repo.codemc.io/repository/maven-public")
                .build();
        /**
         * com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.17.2
         */
        libraryManager.loadLibraries(sqlite, mongo_driver, mongo_core,
                mongo_bson, rabbit, xseries,
                redis, nbt_api);
    }

    private Library create(String groupId, String artifactId, String version, String loaderId) {
        return Library.builder()
                .groupId(groupId)
                .artifactId(artifactId)
                .version(version)
                .loaderId(loaderId)
                .build();
    }

}
