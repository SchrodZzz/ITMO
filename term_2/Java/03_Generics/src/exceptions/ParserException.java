package exceptions;

public class ParserException extends Exception {
    public ParserException(String line) {
        super("Parse exception : " + line);
    }
}
