import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class FastScanner {
    private BufferedReader in;

    FastScanner(InputStream stream) {
        in = new BufferedReader(new InputStreamReader(stream));
    }

    String nextLine() throws IOException {
        return in.readLine();
    }

    boolean hasNextLine() throws IOException {
        return in.ready();
    }

    void close() throws IOException {
        in.close();
    }
}
