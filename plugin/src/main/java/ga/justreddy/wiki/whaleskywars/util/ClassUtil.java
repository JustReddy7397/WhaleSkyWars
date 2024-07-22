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
                } catch (ClassNotFoundException ignored) {
                }
            }
        } catch (NoClassDefFoundError ignored) {
        }
        return classes;
    }

}
