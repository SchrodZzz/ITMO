package additionExpressions;

import expressions.TripleExpression;

public class BitCount extends AbstractUnary {
    public BitCount(TripleExpression value) {
        super(value);
    }

    public int apply(int x) {
        return Integer.bitCount(x);
    }
}
