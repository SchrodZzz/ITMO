package expressions.operators;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import expressions.Binary;
import expressions.TripleExpression;
import generics.Types;

public class Mod<T> extends Binary<T> {

    public Mod(TripleExpression<T> firstObject, TripleExpression<T> secondObject) {
        super(firstObject, secondObject);
    }

    protected T calculate(T firstObject, T secondObject, Types<T> op) throws CustomArithmeticException {
        return op.mod(firstObject, secondObject);
    }
}
