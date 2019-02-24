package implementation.expressions;

import implementation.exceptions.EvaluatingException;
import implementation.exceptions.IllegalOperationException;
import implementation.exceptions.OverflowException;
import implementation.expressions.types.Binary;

import kgeorgiy.expression.TripleExpression;

import static implementation.expressions.check.OverflowCheck.overflowMultiplyCheck;

public class CheckedPow extends Binary {

    public CheckedPow(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected void check(int x, int y) throws EvaluatingException {
        if (y < 0) {
            throw new IllegalOperationException("Powering in negative");
        }
    }

    private int binaryPow(int x, int y) throws OverflowException {

        if (y == 0) {
            return 1;
        }

        if (y % 2 == 0) {
            int result = binaryPow(x, y / 2);
            overflowMultiplyCheck(result, result);
            return result * result;
        } else {
            int res = binaryPow(x, y - 1);
            overflowMultiplyCheck(res, x);
            return res * x;
        }
    }

    protected int apply(int x, int y) throws EvaluatingException {
        check(x, y);
        return binaryPow(x, y);
    }

}
