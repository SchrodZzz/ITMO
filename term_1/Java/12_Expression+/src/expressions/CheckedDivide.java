package expressions;

import exceptions.*;

public class CheckedDivide extends Binary {

    public CheckedDivide(TripleExpression firstOpetator, TripleExpression secondOperator) {
        super(firstOpetator, secondOperator);
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
