package additionExpressions;

import expressions.TripleExpression;

public abstract class AbstractUnary implements TripleExpression {
    private TripleExpression value;

    AbstractUnary(TripleExpression value) {
        assert (value != null);

        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        return apply(value.evaluate(x, y, z));
    }

    protected abstract int apply(int x);
}
