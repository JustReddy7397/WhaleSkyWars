package ga.justreddy.wiki.whaleskywars.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author JustReddy
 */
public class ClassUtil {

    public static <T> Class<? extends T> findClass(File file, Class<T> clazz) throws Exception {
        final URL url = file.toURI().toURL();
        List<Class<? extends T>> classes = new ArrayList<>();
        List<String> matches = new ArrayList<>();
        try (JarInputStream stream = new JarInputStream(url.openStream());
             URLClassLoader loader = new URLClassLoader(new URL[]{url})) {
            JarEntry entry = stream.getNextJarEntry();

            String name = entry.getName();

            if (name.endsWith(".class")) {
                matches.add(name.substring(0, name.lastIndexOf(46)).replace('/', '.'));
            }

            for (String match : matches) {
                Class<?> loaded = Class.forName(match, true, loader);
                if (clazz.isAssignableFrom(loaded)) {
                    classes.add(loaded.asSubclass(clazz));
                }
            }

            if (!classes.isEmpty()) {
                return classes.get(0);
            }
            return null;
        }
    }

}
