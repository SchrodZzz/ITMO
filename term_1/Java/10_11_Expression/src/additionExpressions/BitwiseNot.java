package additionExpressions;

import expressions.TripleExpression;

public class BitwiseNot extends AbstractUnary {
    public BitwiseNot(TripleExpression value) {
        super(value);
    }

    public int apply(int x) {
        return ~x;
    }
}
