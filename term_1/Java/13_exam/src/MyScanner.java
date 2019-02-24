import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyScanner implements AutoCloseable {

    private BufferedReader reader;

    MyScanner(String fileName) throws FileNotFoundException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), StandardCharsets.UTF_8));
    }

    String readNextCommand() throws IOException {
        return reader.readLine();
    }

    boolean hasNextCommand() throws IOException {
        return reader.ready();
    }

    public void close() {
        // added for AutoCloseable implementation
    }
}