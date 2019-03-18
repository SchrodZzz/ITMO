package exceptions;

public class UnsupportedTypeException extends Exception {
    public UnsupportedTypeException(String message) {
        super("Unknown type : " + message);
    }
}
