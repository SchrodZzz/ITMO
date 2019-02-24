package implementation.expressions;

import implementation.exceptions.EvaluatingException;
import implementation.exceptions.IllegalOperationException;
import implementation.expressions.data.Const;
import implementation.expressions.types.Binary;

import kgeorgiy.expression.TripleExpression;

public class CheckedLog extends Binary {

    public CheckedLog(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected void check(int x, int y) throws EvaluatingException {
        if (x <= 0) {
            throw new IllegalOperationException("Log form not positive value");
        }
        if (y <= 0 || y == 1) {
            throw new IllegalOperationException("Incorrect log base");
        }
    }

    protected int apply(int x, int y) throws EvaluatingException {
        check(x, y);
        int l = 0;
        int r = 31;
        while (r - l > 1) {
            int m = (l + r) / 2;
            try {
                int res = new CheckedPow(new Const(y), new Const(m)).evaluate(0, 0, 0);
                if (res <= x) {
                    l = m;
                } else {
                    r = m;
                }
            } catch (EvaluatingException e) {
                r = m;
            }
        }
        return l;
    }

}

