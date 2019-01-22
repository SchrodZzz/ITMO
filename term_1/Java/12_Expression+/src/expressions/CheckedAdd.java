package expressions;

import exceptions.*;

public class CheckedAdd extends Binary {

    public CheckedAdd(TripleExpression firstOperator, TripleExpression secondOperator) {
        super(firstOperator, secondOperator);
    }

    protected void check(int x, int y) throws OverflowException  {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    protected int apply(int x, int y) throws OverflowException  {
        check(x, y);
        return x + y;
    }

}