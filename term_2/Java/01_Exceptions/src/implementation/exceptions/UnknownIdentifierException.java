package implementation.exceptions;

public class UnknownIdentifierException extends ParsingException {

    public UnknownIdentifierException(final String identifier, final String message, final int exceptionStartIndex) {
        super("Unknown identifier '" + identifier + "' at position: " + exceptionStartIndex
                + "\n" + message + "\n" + exceptionUnderline(exceptionStartIndex, identifier.length()));
    }

}
