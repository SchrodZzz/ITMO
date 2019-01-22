package exceptions;

public class ClosingParenthesisException extends ParsingException {
    public ClosingParenthesisException(final String message, int index) {
        super("Excess closing parenthesis at position: " + index + "\n" + message + "\n" + getPlace(index, 1));
    }
}
