package exceptions;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(final String message, final int index) {
        super("Missing operand before position: " + index + "\n" + message + "\n" + getPlace(index, 1));
    }
}
