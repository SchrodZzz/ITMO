package implementation.expressions.types;

import implementation.exceptions.EvaluatingException;

import kgeorgiy.expression.TripleExpression;

public abstract class Binary implements TripleExpression {
    private final TripleExpression firstOperand;
    private final TripleExpression secondOperand;

    public Binary(TripleExpression firstOperand, TripleExpression secondOperand) {
        assert firstOperand != null && secondOperand != null : "Some binary operand is null";
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    protected abstract void check(int x, int y) throws EvaluatingException;

    protected abstract int apply(int x, int y) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }
}