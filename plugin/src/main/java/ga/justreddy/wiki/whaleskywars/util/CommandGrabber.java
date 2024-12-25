package ga.justreddy.wiki.whaleskywars.util;

import ga.justreddy.wiki.whaleskywars.commands.SkyWarsCommandHolder;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.Usage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author JustReddy
 */
public class CommandGrabber {

    public static List<SkyWarsCommandHolder> grabCommands() {
        String packageName = "ga.justreddy.wiki.whaleskywars.commands.lamp";
        String className = "SkyWarsCommandHolder";
        Set<Class<?>> clazzes = new Reflections(packageName)
                .getTypesAnnotatedWith(Command.class);
        List<SkyWarsCommandHolder> holders = new ArrayList<>();
        for (Class<?> clazz : clazzes) {
            Set<Method> methods = new HashSet<>(List.of(clazz.getDeclaredMethods()));
            methods.removeIf(method -> !method.isAnnotationPresent(Command.class));
            for (Method method : methods) {
                SkyWarsCommandHolder holder = getCommandHolder(method, false);
                holders.add(holder);
            }
            for (Class<?> innerClass : clazz.getDeclaredClasses()) {
                if (innerClass.isAnnotationPresent(Command.class)) {
                    SkyWarsCommandHolder holder = new SkyWarsCommandHolder();
                    Command command = innerClass.getAnnotation(Command.class);
                    holder.setName(command.value()[0]);
                    if (innerClass.isAnnotationPresent(Description.class)) {
                        Description description = innerClass.getAnnotation(Description.class);
                        holder.setDescription(description.value());
                    }
                    if (innerClass.isAnnotationPresent(Usage.class)) {
                        Usage usage = innerClass.getAnnotation(Usage.class);
                        holder.setUsage(usage.value());
                    }
                    holders.add(holder);
                }
            }


        }
        return holders;
    }

    public static List<SkyWarsCommandHolder> grabFromSpecificInnerClass(Class<?> innerClazz) {
        List<SkyWarsCommandHolder> holders = new ArrayList<>();
        Set<Method> methods = new HashSet<>(List.of(innerClazz.getDeclaredMethods()));
        methods.removeIf(method -> !method.isAnnotationPresent(Subcommand.class));
        for (Method method : methods) {
            SkyWarsCommandHolder holder = getCommandHolder(method, true);
            holders.add(holder);
        }
        return holders;
    }

    private static @NotNull SkyWarsCommandHolder getCommandHolder(Method method, boolean isSubcommand) {
        SkyWarsCommandHolder holder  = new SkyWarsCommandHolder();
        Annotation annotation = isSubcommand ? method.getAnnotation(Subcommand.class) : method.getAnnotation(Command.class);

        if (annotation == null) {
            throw new IllegalArgumentException("Method must be annotated with either @Command or @Subcommand");
        } else if (annotation instanceof Command) {
            Command command = (Command) annotation;
            holder.setName(command.value()[0]);
        } else {
            Subcommand subcommand = (Subcommand) annotation;
            holder.setName(subcommand.value()[0]);
        }
        if (method.isAnnotationPresent(Description.class)) {
            Description description = method.getAnnotation(Description.class);
            holder.setDescription(description.value());
        }
        if (method.isAnnotationPresent(Usage.class)) {
            Usage usage = method.getAnnotation(Usage.class);
            holder.setUsage(usage.value());
        }
        return holder;
    }

}
