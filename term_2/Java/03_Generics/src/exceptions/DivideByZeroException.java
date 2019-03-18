package exceptions;

public class DivideByZeroException extends CustomArithmeticException {
    public DivideByZeroException() {
        super("divide by zero");
    }
}
