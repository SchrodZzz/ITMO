package exceptions;

public class ParsingException extends Exception {

    public ParsingException(final String message) {
        super(message);
    }

    static protected String getPlace(final int index, final int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < index; i++) {
            result.append(' ');
        }
        result.append('^');
        for (int i = 1; i < size; i++) {
            result.append('~');
        }
        return result.toString();
    }

}