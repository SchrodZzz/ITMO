package additionExpressions;

import expressions.TripleExpression;

public class Multiply extends AbstractBinary {
    public Multiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int apply(int first, int second) {
        return first * second;
    }

}