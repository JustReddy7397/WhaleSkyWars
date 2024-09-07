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
        Library ormlite = create("com.j256.ormlite", "ormlite-jdbc", "6.1", "ORMLite");
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
                .relocate("ga.justreddy.wiki.whaleskywars.libs.nbtapi", "de.tr7zw")
                .build();
        /**
         * com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.17.2
         */
        Library jackson = Library.builder()
                .groupId("com.fasterxml.jackson.dataformat")
                .artifactId("jackson-dataformat-toml")
                .version("2.17.2")
                .loaderId("JacksonToml")
                .build();
        Library jackson_databind = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-databind")
                .version("2.17.2")
                .loaderId("JacksonDatabind")
                .build();
        Library jackson_core = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-core")
                .version("2.17.2")
                .loaderId("JacksonCore")
                .build();
        Library jackson_annotations = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-annotations")
                .version("2.17.2")
                .loaderId("JacksonAnnotations")
                .build();
        libraryManager.loadLibraries(sqlite, mongo_driver, mongo_core, mongo_bson, rabbit, xseries, redis, nbt_api, jackson, jackson_databind, jackson_core, jackson_annotations);
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
