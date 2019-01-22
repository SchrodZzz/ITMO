package exceptions;

public class UnknownSymbolException extends ParsingException {
    public UnknownSymbolException(final String message, final int index) {
        super("Unknown symbol '" + message.charAt(index) + "' at position "
                + index + "\n" + message + "\n" + getPlace(index, 1));
    }
}
