package kgeorgiy.expression;

import implementation.exceptions.EvaluatingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression {
    int evaluate(int x, int y, int z) throws EvaluatingException;
}
