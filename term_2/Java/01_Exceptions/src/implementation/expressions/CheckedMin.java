package implementation.expressions;

import implementation.exceptions.EvaluatingException;
import implementation.expressions.types.Binary;

import kgeorgiy.expression.TripleExpression;

public class CheckedMin extends Binary {

    public CheckedMin (TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected void check (int x, int y) throws EvaluatingException {
        //Empty
    }

    protected int apply (int x, int y) throws EvaluatingException {
        check(x,y);
        if (x < y) {
            return x;
        } else {
            return y;
        }
    }
}
