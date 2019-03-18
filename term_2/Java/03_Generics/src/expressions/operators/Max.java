package expressions.operators;

import expressions.Binary;
import expressions.TripleExpression;
import generics.Types;

public class Max<T> extends Binary<T> {
    public Max(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        super(firstObject, secondObject);
    }

    protected T calculate(T firstObject, T secondObject, Types<T> op) {
        return op.max(firstObject, secondObject);
    }
}
