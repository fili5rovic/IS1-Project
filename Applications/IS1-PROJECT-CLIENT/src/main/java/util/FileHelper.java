package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
