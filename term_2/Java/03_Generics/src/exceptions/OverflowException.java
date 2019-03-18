package exceptions;

public class OverflowException extends CustomArithmeticException {
    public OverflowException(String line) {
        super("Overflow : " + line);
    }
}
