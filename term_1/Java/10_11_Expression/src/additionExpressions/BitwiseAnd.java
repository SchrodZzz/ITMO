package additionExpressions;

import expressions.TripleExpression;

public class BitwiseAnd extends AbstractBinary {
    public BitwiseAnd(TripleExpression firstValue, TripleExpression secondValue) {
        super(firstValue, secondValue);
    }

    protected int apply(int first, int second) {
        return first & second;
    }
}
