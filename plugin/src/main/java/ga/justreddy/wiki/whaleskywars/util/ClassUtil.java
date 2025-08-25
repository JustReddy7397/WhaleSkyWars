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
    public static List<Class<? extends Addon>> findAddons(File file) {
        List<Class<? extends Addon>> classes = new ArrayList<>();
        URL jarUrl = file.toURI().toURL();

        // Use try-with-resources for safety
        try (JarFile jarFile = new JarFile(file);
             URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl}, ClassUtil.class.getClassLoader())) {

            // Read addon.yml
            JarEntry addonYmlEntry = jarFile.getJarEntry("addon.yml");
            if (addonYmlEntry == null) {
                System.err.println("No addon.yml found in " + file.getName());
                return classes; // No addon.yml, return empty list
            }

            String mainClassName;
            try (InputStream inputStream = jarFile.getInputStream(addonYmlEntry);
                 InputStreamReader reader = new InputStreamReader(inputStream)) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(reader);
                mainClassName = config.getString("main-class");
            }

            if (mainClassName == null || mainClassName.isEmpty()) {
                System.err.println("No main-class specified in addon.yml of " + file.getName());
                return classes;
            }

            // Load only the specified main-class
            try {
                Class<?> loadedClass = Class.forName(mainClassName, true, loader);
                if (Addon.class.isAssignableFrom(loadedClass)) {
                    classes.add(loadedClass.asSubclass(Addon.class));
                }
            } catch (ClassNotFoundException | NoClassDefFoundError e) {
                System.err.println("Could not load class: " + mainClassName + " -> " + e.getMessage());
            }
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
