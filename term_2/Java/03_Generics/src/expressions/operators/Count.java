package expressions.operators;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import expressions.TripleExpression;
import expressions.Unary;
import generics.Types;

public class Count<T> extends Unary<T> {
    public Count(TripleExpression<T> object) {
        super(object);
    }

    protected T calculate(T object, Types<T> op) throws CustomArithmeticException {
        return op.count(object);
    }
}
