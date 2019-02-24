package implementation.exceptions;

public class MissingOperatorException extends ParsingException {

    public MissingOperatorException(final String message, final int exceptionStartIndex) {
        super("Missing operator before position: " + exceptionStartIndex
                + "\n" + message + "\n" + exceptionUnderline(exceptionStartIndex, 1));
    }

}
