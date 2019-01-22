package queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyScanner {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readNextString() throws IOException {
        return this.reader.readLine();
    }
}
