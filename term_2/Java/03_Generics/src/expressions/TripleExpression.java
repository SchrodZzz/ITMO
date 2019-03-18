package expressions;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import generics.Types;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression<T> {
    T evaluate(T x, T y, T z, Types<T> op) throws ParserException, CustomArithmeticException;
}
