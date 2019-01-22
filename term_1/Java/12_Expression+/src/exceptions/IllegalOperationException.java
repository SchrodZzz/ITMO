package exceptions;

public class IllegalOperationException extends EvaluatingException {
    public IllegalOperationException(final String message) {
        super("Illegal operation: " + message);
    }
}
