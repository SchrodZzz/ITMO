package expressions;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import generics.Types;

public abstract class Binary<T> implements TripleExpression<T> {
    private final TripleExpression<T> firstObject;
    private final TripleExpression<T> secondObject;

    public Binary(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    protected abstract T calculate(T x, T y, Types<T> op) throws ParserException, CustomArithmeticException;

    public T evaluate(T x, T y, T z, Types<T> op) throws CustomArithmeticException, ParserException {
        return calculate(firstObject.evaluate(x, y, z, op), secondObject.evaluate(x, y, z, op), op);
    }
}
