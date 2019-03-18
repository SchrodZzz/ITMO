package expressions.operators;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import expressions.Binary;
import expressions.TripleExpression;
import generics.Types;

public class Multiply<T> extends Binary<T> {

    public Multiply(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        super(firstObject, secondObject);
    }

    protected T calculate(T firstObject, T secondObject, Types<T> op) throws CustomArithmeticException {
        return op.multiply(firstObject, secondObject);
    }
}
