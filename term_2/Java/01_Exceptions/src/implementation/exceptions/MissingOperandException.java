package implementation.exceptions;

public class MissingOperandException extends ParsingException {

    public MissingOperandException(final String message, final int exceptionStartIndex) {
        super("Missing operand before position: " + exceptionStartIndex
                + "\n" + message + "\n" + exceptionUnderline(exceptionStartIndex, 1));
    }

}
