package exceptions;

public class MissingOperationException extends ParsingException {
    public MissingOperationException(final String message, final int index) {
        super("Missing operation before position: " + index + "\n" + message + "\n" + getPlace(index, 1));
    }
}
