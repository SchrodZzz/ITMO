package implementation.exceptions;

public class MissingClosingParenthesisException extends ParsingException {

    public MissingClosingParenthesisException(final String message, final int exceptionStartIndex) {
        super("Missing closing parenthesis for opening one at position: " + exceptionStartIndex
                + "\n" + message + "\n" + exceptionUnderline(exceptionStartIndex, 1));
    }

}
