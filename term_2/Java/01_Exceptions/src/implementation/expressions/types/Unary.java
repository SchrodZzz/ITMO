package implementation.expressions.types;

import implementation.exceptions.EvaluatingException;

import kgeorgiy.expression.TripleExpression;

public abstract class Unary implements TripleExpression {
    private final TripleExpression operand;

    protected Unary(final TripleExpression operand) {
        assert operand != null : "Operand is null";
        this.operand = operand;
    }

    protected abstract void check(int x) throws EvaluatingException;

    protected abstract int apply(int x) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(operand.evaluate(x, y, z));
    }
}