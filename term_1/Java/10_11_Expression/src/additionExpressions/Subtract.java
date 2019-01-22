package additionExpressions;

import expressions.TripleExpression;

public class Subtract extends AbstractBinary {
    public Subtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int apply(int first, int second) {
        return first - second;
    }

}