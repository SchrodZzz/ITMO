package expressions.operators;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import expressions.TripleExpression;
import expressions.Unary;
import generics.Types;

public class Negate<T> extends Unary<T> {
    public Negate(TripleExpression<T> object) {
        super(object);
    }

    protected T calculate(T object, Types<T> op) throws CustomArithmeticException {
        return op.negate(object);
    }
}
