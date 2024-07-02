package ga.justreddy.wiki.whaleskywars.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JustReddy
 */
public class FileUtil {


    public static void copy(InputStream source, File destination)  {

        try (OutputStream out = Files.newOutputStream(destination.toPath())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = source.read(buffer)) > 0)
                out.write(buffer, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(File source, File destination) throws IOException {
        List<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
        if (!ignore.contains(source.getName())) {
            if (source.isDirectory()) {
                if (!destination.exists())
                    if (!destination.mkdirs())
                        throw new IOException("Couldn't create world directory!");
                String[] files = source.list();
                if (files == null) return;
                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(destination, file);
                    copy(srcFile, destFile);
                }
            } else {
                try (InputStream in = Files.newInputStream(source.toPath());
                ) {
                    copy(in, destination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void delete(File file) {
        if (file == null) return;
        if (file.isDirectory()) {
            File[] f = file.listFiles();
            if (f == null) return;
            for (File child : f) {
                delete(child);
            }
        }
        file.delete();
    }
}
