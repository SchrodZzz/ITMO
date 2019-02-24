package implementation.exceptions;

public class UnknownSymbolException extends ParsingException {

    public UnknownSymbolException(final String message, final int exceptionStartIndex) {
        super("Unknown symbol '" + message.charAt(exceptionStartIndex) + "' at position " + exceptionStartIndex
                + "\n" + message + "\n" + exceptionUnderline(exceptionStartIndex, 1));
    }

}
