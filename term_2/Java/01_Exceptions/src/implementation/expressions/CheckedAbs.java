package implementation.expressions;

import implementation.exceptions.EvaluatingException;
import implementation.exceptions.OverflowException;
import implementation.expressions.types.Unary;

import kgeorgiy.expression.TripleExpression;

public class CheckedAbs extends Unary {

    public CheckedAbs (TripleExpression operand) {
        super(operand);
    }

    protected void check (int x) throws EvaluatingException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int apply (int x) throws EvaluatingException{
        check(x);
        return x > 0 ? x : -x;
    }
}
