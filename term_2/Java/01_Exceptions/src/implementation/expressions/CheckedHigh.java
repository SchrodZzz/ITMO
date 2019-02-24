package implementation.expressions;

import implementation.exceptions.EvaluatingException;
import implementation.expressions.types.Unary;
import kgeorgiy.expression.TripleExpression;

public class CheckedHigh extends Unary {

    public CheckedHigh (TripleExpression operand) {
        super(operand);
    }

    protected void check (int x) throws EvaluatingException {
        //Empty
    }

    protected int apply (int x) throws EvaluatingException{
        check(x);
        return Integer.highestOneBit(x);
    }
}