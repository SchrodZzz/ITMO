package implementation.exceptions;

public class IllegalConstantException extends ParsingException {

    public IllegalConstantException(final String reason, final String message, final int exceptionStartIndex) {
        super("Constant '" + reason + "' is unsuitable for int at position: " + exceptionStartIndex
                + "\n" + message + "\n" + exceptionUnderline(exceptionStartIndex, reason.length()));
    }

}
