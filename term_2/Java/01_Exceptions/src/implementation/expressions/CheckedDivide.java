package implementation.expressions;

import implementation.exceptions.EvaluatingException;
import implementation.exceptions.IllegalOperationException;
import implementation.exceptions.OverflowException;
import implementation.expressions.types.Binary;

import kgeorgiy.expression.TripleExpression;

public class CheckedDivide extends Binary {

    public CheckedDivide(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected void check(int x, int y) throws EvaluatingException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    protected int apply(int x, int y) throws EvaluatingException {
        check(x, y);
        return x / y;
    }
}
