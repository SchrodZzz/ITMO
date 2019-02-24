package implementation.exceptions;

public class ParsingException extends Exception {

    public ParsingException(final String message) {
        super(message);
    }

    static String exceptionUnderline(final int exceptionStartIndex, final int exceptionSize) {
        int exceptionEndIndex = exceptionStartIndex + exceptionSize - 2;
        return underlineCreation(' ', '^', 0, exceptionStartIndex) +
                underlineCreation('~', ' ', exceptionStartIndex, exceptionEndIndex);
    }

    static private String underlineCreation(char body, char spec, int l, int r) {
        StringBuilder underline = new StringBuilder();
        for (int i = l; i < r; i++) {
            underline.append(body);
        }
        underline.append(spec);
        return underline.toString();
    }

}