package kgeorgiy.expression.parser;

import implementation.exceptions.ParsingException;
import kgeorgiy.expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws ParsingException;
}
