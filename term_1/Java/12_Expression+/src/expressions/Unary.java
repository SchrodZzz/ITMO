package expressions;

import exceptions.*;

public abstract class Unary implements TripleExpression {
    private final TripleExpression operand;

    protected Unary(final TripleExpression x) {
        operand = x;
    }

    protected abstract void check(int x) throws EvaluatingException;

    protected abstract int apply(int x) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(operand.evaluate(x, y, z));
    }
}