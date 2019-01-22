import java.io.IOException;
import java.io.InputStream;

class FastScanner {

    private InputStream stream;
    private byte curByte = -2;

    FastScanner(InputStream stream) {
        this.stream = stream;
    }



    boolean fastHasNext() throws IOException {
        return (curByte = (byte) stream.read()) != -1;
    }

    boolean fastLineEnd() {
        return (curByte < 32);
    }

    int fastNextInt() throws IOException {
        StringBuilder num = new StringBuilder();
        try {
            while (!(curByte >= '0' && curByte <= '9' || curByte == '-')) {
                curByte = (byte) stream.read();
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("There is no integers left or there \'-\' with out digits, so... ");
        }
        while (curByte >= '0' && curByte <= '9' || curByte == '-') {
            num.append((char) curByte);
            curByte = (byte) stream.read();
        }
        return Integer.parseInt(num.toString()); //NumberFormatException
    }

    void close() throws IOException {
        stream.close();
    }
}







/*
public String fastNextWord() throws IOException {
        StringBuilder str = new StringBuilder();
        try {
            while (curByte < 33) {
                curByte = (byte) stream.read();
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("There is no words left");
            System.exit(1);
        }
        while (curByte > 32) {
            str.append((char) curByte);
            curByte = (byte) stream.read();
        }
        return str.toString();
    } //Is it decreasing program speed?
 */








/*
import java.io.IOException;
import java.io.InputStream;

public class FastScanner {

    private byte[] buffer;
    private int pointer = 0;

    public FastScanner(InputStream stream) throws IOException {
        try (InputStream stream = stream) {
            buffer = new byte[stream.available() + 1];
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (byte) stream.read();
            }
        } catch (IOException ex) {
            System.out.println("Ops, it's seems that program can't fill the buffer\nCheck if file exists");
            System.exit(1);
        }
    }

    public boolean fastHasNext() {
        return buffer[pointer] != -1;
    }

    public boolean fastLineEnd() {
        return (buffer[pointer] > 0 && buffer[pointer] < 32);
    }

    public int fastNextInt() throws NumberFormatException {
        StringBuilder num = new StringBuilder();
        try {
            while (!(buffer[pointer] > 47 && buffer[pointer] < 58 || buffer[pointer] == 45)) {
                pointer += 1;
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("There is no integers left");
            System.exit(1);
        }
        while (buffer[pointer] > 47 && buffer[pointer] < 58 || buffer[pointer] == 45) {
            num.append((char) buffer[pointer]);
            pointer += 1;
        }
        return Integer.parseInt(num.toString());
    }

    public String fastNextWord() {
        StringBuilder str = new StringBuilder();
        try {
            while (buffer[pointer] < 33) {
                pointer += 1;
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("There is no words left");
            System.exit(1);
        }
        while (buffer[pointer] > 32) {
            str.append((char) buffer[pointer]);
            pointer += 1;
        }
        return str.toString();
    }
}
 */