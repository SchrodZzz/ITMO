package additionExpressions;

import expressions.TripleExpression;

public class Divide extends AbstractBinary {
    public Divide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int apply(int first, int second) {
        return first / second;
    }
}