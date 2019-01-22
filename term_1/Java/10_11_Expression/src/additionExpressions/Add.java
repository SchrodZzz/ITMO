package additionExpressions;

import expressions.TripleExpression;

public class Add extends AbstractBinary {
    public Add(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int apply(int first, int second) {
        return first + second;
    }

}