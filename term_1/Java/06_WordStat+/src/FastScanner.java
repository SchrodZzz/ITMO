import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FastScanner implements AutoCloseable {

    private char currentChar;
    private final InputStreamReader stream;
    private static final byte END_OF_FILE = -1;
    private static final char NEW_LINE = '\n';
    private static final char APOSTROPHE = '\'';

    FastScanner(FileInputStream stream, String charsetName) throws IOException {
        this.stream = new InputStreamReader(stream, charsetName);
    }

    private void readNextChar() throws IOException {
        currentChar = (char) stream.read();
    }

    boolean hasNext() throws IOException {
        readNextChar();
        return (byte) currentChar != END_OF_FILE;
    }

    boolean onLine() {
        return currentChar != NEW_LINE
                && (byte) currentChar != END_OF_FILE;
    }

    String nextWord() throws IOException {
        StringBuilder currentWord = new StringBuilder();
        while (!charIsPartOfWord(currentChar)) {
            readNextChar();
            if (!onLine()) {
                break;
            }
        }
        while (charIsPartOfWord(currentChar)) {
            currentWord.append(currentChar);
            readNextChar();
        }
        return currentWord.toString().toLowerCase();
    }

    private boolean charIsPartOfWord(char symbol) {
        return Character.isLetter(symbol)
                || Character.getType(symbol) == Character.DASH_PUNCTUATION
                || symbol == APOSTROPHE;
    }

    public void close() {
    }
}