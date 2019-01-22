package exceptions;

public class MissingClosingParenthesisException extends ParsingException {
    public MissingClosingParenthesisException(final String message, final int index){
        super("Missing closing parenthesis for opening one at position: "
                + index + "\n" + message + "\n" + getPlace(index, 1));
    }
}
