package additionExpressions;

import expressions.TripleExpression;

public class Not extends AbstractUnary {
    public Not(TripleExpression value) {
        super(value);
    }

    public int apply(int x) {
        return -x;
    }
}
