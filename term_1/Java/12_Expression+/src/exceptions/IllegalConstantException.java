package exceptions;

public class IllegalConstantException extends ParsingException {
    public IllegalConstantException(final String reason, final String message, final int index) {
        super("Constant '" + reason + "' is unsuitable for int at position: "
                + index + "\n" + message + "\n" + getPlace(index, reason.length()));
    }
}
