package additionExpressions;

import expressions.TripleExpression;

public abstract class AbstractBinary implements TripleExpression {
    private TripleExpression firstValue;
    private TripleExpression secondValue;

    AbstractBinary(TripleExpression first, TripleExpression second) {
        assert (first != null) && (second != null);

        this.firstValue = first;
        this.secondValue = second;
    }

    public int evaluate(int x, int y, int z) {
        return apply(firstValue.evaluate(x, y, z), secondValue.evaluate(x, y, z));
    }

    protected abstract int apply(int first, int second);

}
