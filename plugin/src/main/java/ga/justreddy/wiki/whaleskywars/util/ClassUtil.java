package ga.justreddy.wiki.whaleskywars.util;

import ga.justreddy.wiki.whaleskywars.api.model.Addon;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

/**
 * @author JustReddy
 */
public class ClassUtil {

    @SneakyThrows
    public static List<Class<? extends Addon>> findAddons(
            File file
    ) {
        final URL url = file.toURI().toURL();
        List<Class<? extends Addon>> classes = new ArrayList<>();
        try (JarInputStream stream = new JarInputStream(url.openStream());
             URLClassLoader loader = new URLClassLoader(new URL[]{url}, ClassUtil.class.getClassLoader());
             JarFile jarFile = new JarFile(file)) {
            boolean isAddonFound = false;
            InputStream inputStream = null;
            InputStreamReader reader = null;
            FileConfiguration config = null;

            // Check if addon.yml exists in the JAR file
            JarEntry addonYmlEntry = jarFile.getJarEntry("addon.yml");
            if (addonYmlEntry != null) {
                inputStream = jarFile.getInputStream(addonYmlEntry);
                reader = new InputStreamReader(inputStream);
                config = YamlConfiguration.loadConfiguration(reader);
                isAddonFound = true;
            }

            JarEntry entry;
            while ((entry = stream.getNextJarEntry()) != null) {
                if (!entry.getName().endsWith(".class")) continue;

                String className = entry.getName().replace('/',
                        '.').substring(0, entry.getName().length() - 6);

                try {
                    if (!isAddonFound) continue;
                    if (!className.startsWith(config.getString("main-class"))) continue;
                    Class<?> loadedClass = Class.forName(className, true, loader);
                    if (Addon.class.isAssignableFrom(loadedClass)) {
                        classes.add(loadedClass.asSubclass(Addon.class));
                    }
                } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
        } catch (NoClassDefFoundError ignored) {
        }
        return classes;
    }

    public static <T> List<Class<? extends T>> findClasses(File file, Class<T> clazz) throws Exception {
        final URL url = file.toURI().toURL();
        List<Class<? extends T>> classes = new ArrayList<>();
        try (JarInputStream stream = new JarInputStream(url.openStream());
             URLClassLoader loader = new URLClassLoader(new URL[]{url}, ClassUtil.class.getClassLoader())) {

            JarEntry entry;
            while ((entry = stream.getNextJarEntry()) != null) {
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }

                String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);

                try {
                    Class<?> loadedClass = Class.forName(className, true, loader);
                    if (clazz.isAssignableFrom(loadedClass)) {
                        classes.add(loadedClass.asSubclass(clazz));
                    }
                } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
                }
            }
        } catch (NoClassDefFoundError ignored) {
        }
        return classes;
    }

}
