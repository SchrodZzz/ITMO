package additionExpressions;

import expressions.TripleExpression;

public class BitwiseOr extends AbstractBinary {
    public BitwiseOr(TripleExpression firstValue, TripleExpression secondValue) {
        super(firstValue, secondValue);
    }

    protected int apply(int first, int second) {
        return first | second;
    }
}
