package expressions;

import exceptions.*;

public class CheckedAbs extends Unary{

    public CheckedAbs (TripleExpression firstOperator) {
        super(firstOperator);
    }

    protected void check (int x) throws EvaluatingException {
        if (x == Integer.MAX_VALUE || x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int apply (int x) throws EvaluatingException{
        check(x);
        return x > 0 ? x : -x;
    }
}
