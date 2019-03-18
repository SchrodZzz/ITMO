package expressions;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import generics.Types;

public abstract class Unary<T> implements TripleExpression<T> {
    private final TripleExpression<T> object;

    public Unary(TripleExpression<T> object) {
        this.object = object;
    }

    protected abstract T calculate(T x, Types<T> op) throws ParserException, CustomArithmeticException;

    public T evaluate(T x, T y, T z, Types<T> op) throws CustomArithmeticException, ParserException {
        return calculate(object.evaluate(x, y, z, op), op);
    }
}
