package expressions.operators;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import expressions.TripleExpression;
import expressions.Unary;
import generics.Types;

public class Abs<T> extends Unary<T> {
    public Abs(TripleExpression<T> object) {
        super(object);
    }

    protected T calculate(T object, Types<T> op) throws CustomArithmeticException {
        return op.abs(object);
    }
}
