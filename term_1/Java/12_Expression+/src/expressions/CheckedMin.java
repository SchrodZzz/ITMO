package expressions;

import exceptions.EvaluatingException;
import exceptions.OverflowException;

public class CheckedMin extends Binary {

    public CheckedMin (TripleExpression firstOperator, TripleExpression secondOperator) {
        super(firstOperator, secondOperator);
    }

    protected void check (int x, int y) throws EvaluatingException {

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
