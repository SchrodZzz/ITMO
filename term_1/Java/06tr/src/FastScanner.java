import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FastScanner implements AutoCloseable {

    private final BufferedReader stream;

    FastScanner(FileInputStream stream, String charsetName) throws IOException {
        this.stream = new BufferedReader(
                new InputStreamReader(stream, charsetName));
    }

    boolean hasNext() throws IOException {
        return stream.ready();
    }

    String getLine() throws IOException {
        return stream.readLine();
    }

    public void close() {
    }
}
