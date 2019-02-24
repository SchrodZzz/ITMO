package implementation.exceptions;

public class ClosingParenthesisException extends ParsingException {

    public ClosingParenthesisException(final String message, int exceptionStartIndex) {
        super("Excess closing parenthesis at position: " + exceptionStartIndex + "\n" + message + "\n"
                + exceptionUnderline(exceptionStartIndex, 1));
    }

}