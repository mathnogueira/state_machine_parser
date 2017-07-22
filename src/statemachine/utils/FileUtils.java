package statemachine.utils;

import java.io.*;

public class FileUtils {

    public static String getContent(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        byte[] buffer = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(buffer);
        return new String(buffer);
    }
}
