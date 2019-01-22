package expressions;

import exceptions.EvaluatingException;
import exceptions.OverflowException;

public class CheckedMax extends Binary {

    public CheckedMax (TripleExpression firstOperator, TripleExpression secondOperator) {
        super(firstOperator, secondOperator);
    }

    protected void check (int x, int y) throws EvaluatingException {
        //Empty
    }

    protected int apply (int x, int y) throws EvaluatingException {
        check(x,y);
        if (x < y) {
            return y;
        } else {
            return x;
        }
    }
}
