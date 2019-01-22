package expressions;

import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;

public class CheckedSqrt extends Unary {

    private static final int MAX_SQRT_FROM_INTEGER = 46341;

    public CheckedSqrt(TripleExpression firstOperator) {
        super(firstOperator);
    }

    protected void check(int x) throws EvaluatingException {
        if (x < 0) {
            throw new IllegalOperationException("Negative value");
        }
    }

    protected int apply(int x) throws EvaluatingException {
        check(x);

        if (x != 0 && x != 1) {
            int left = -1, right = MAX_SQRT_FROM_INTEGER, result = 0;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (mid * mid == x)
                    return mid;

                if (mid * mid < x) {
                    left = mid + 1;
                    result = mid;
                } else
                    right = mid - 1;
            }
            x = result;
        }
        return x;
    }
}
