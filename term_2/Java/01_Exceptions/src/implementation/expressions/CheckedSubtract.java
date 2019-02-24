package implementation.expressions;

import implementation.exceptions.OverflowException;
import implementation.expressions.types.Binary;

import kgeorgiy.expression.TripleExpression;

public class CheckedSubtract extends Binary {

    public CheckedSubtract(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected void check(int x, int y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException();
        }
    }

    protected int apply(int x, int y) throws OverflowException {
        check(x, y);
        return x - y;
    }
}
