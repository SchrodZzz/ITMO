package expressions;

import exceptions.*;

public class CheckedNegate extends Unary {

    public CheckedNegate(TripleExpression firstOperator) {
        super(firstOperator);
    }

    protected void check(int x) throws EvaluatingException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int apply(int x) throws EvaluatingException {
        check(x);
        return -x;
    }
}
