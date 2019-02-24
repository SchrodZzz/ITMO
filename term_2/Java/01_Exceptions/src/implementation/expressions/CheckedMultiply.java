package implementation.expressions;

import implementation.exceptions.OverflowException;
import implementation.expressions.types.Binary;

import kgeorgiy.expression.TripleExpression;

import static implementation.expressions.check.OverflowCheck.overflowMultiplyCheck;

public class CheckedMultiply extends Binary {

    public CheckedMultiply(TripleExpression firstOperator, TripleExpression secondOperator) {
        super(firstOperator, secondOperator);
    }

    protected void check(int x, int y) throws OverflowException {
        overflowMultiplyCheck(x, y);
    }

    protected int apply(int x, int y) throws OverflowException  {
        check(x, y);
        return x * y;
    }
}
