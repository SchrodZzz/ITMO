package implementation.parser;

import implementation.exceptions.MissingClosingParenthesisException;
import implementation.exceptions.ParsingException;
import implementation.expressions.*;
import implementation.expressions.data.*;

import kgeorgiy.expression.TripleExpression;
import kgeorgiy.expression.parser.Parser;

public class ExpressionParser implements Parser {
    private Tokenizer tokenizer;

    private TripleExpression unary() throws ParsingException {
        TripleExpression currentResult;

        switch (tokenizer.getNextToken()) {
            case CONST:
                currentResult = new Const(tokenizer.getValue());
                tokenizer.getNextToken();
                break;
            case VARIABLE:
                currentResult = new Variable(tokenizer.getVariableName());
                tokenizer.getNextToken();
                break;
            case UNARY_MINUS:
                currentResult = new CheckedNegate(unary());
                break;
            case LOG10:
                currentResult = new CheckedLog(unary(), new Const(10));
                break;
            case POW10:
                currentResult = new CheckedPow(new Const(10), unary());
                break;
            case ABS:
                currentResult = new CheckedAbs(unary());
                break;
            case SQRT:
                currentResult = new CheckedSqrt(unary());
                break;
            case LOW:
                currentResult = new CheckedLow(unary());
                break;
            case HIGH:
                currentResult = new CheckedHigh(unary());
                break;
            case OPENING_BRACE:
                int position = tokenizer.getPositionIndex();
                currentResult = minAndMax();
                if (tokenizer.getCurrentToken() != Token.CLOSING_BRACE) {
                    throw new MissingClosingParenthesisException(tokenizer.getExpression(), position - 1);
                }
                tokenizer.getNextToken();
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + tokenizer.getExpression());
        }
        return currentResult;
    }

    private TripleExpression logAndPow() throws ParsingException {
        TripleExpression currentResult = unary();
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case LOG:
                    currentResult = new CheckedLog(currentResult, unary());
                    break;
                case POW:
                    currentResult = new CheckedPow(currentResult, unary());
                    break;
                default:
                    return currentResult;
            }
        }
    }

    private TripleExpression mulAndDiv() throws ParsingException {
        TripleExpression currentResult = logAndPow();
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case MULTIPLY:
                    currentResult = new CheckedMultiply(currentResult, logAndPow());
                    break;
                case DIVIDE:
                    currentResult = new CheckedDivide(currentResult, logAndPow());
                    break;
                default:
                    return currentResult;
            }
        }
    }

    private TripleExpression addAndSub() throws ParsingException {
        TripleExpression currentResult = mulAndDiv();
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case ADD:
                    currentResult = new CheckedAdd(currentResult, mulAndDiv());
                    break;
                case SUBTRACT:
                    currentResult = new CheckedSubtract(currentResult, mulAndDiv());
                    break;
                default:
                    return currentResult;
            }
        }
    }

    private TripleExpression minAndMax() throws ParsingException {
        TripleExpression currentResult = addAndSub();
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case MIN:
                    currentResult = new CheckedMin(currentResult, addAndSub());
                    break;
                case MAX:
                    currentResult = new CheckedMax(currentResult, addAndSub());
                    break;
                default:
                    return currentResult;
            }
        }
    }

    public TripleExpression parse(final String expression) throws ParsingException {
        assert expression != null : "Expression is null";

        tokenizer = new Tokenizer(expression);
        return minAndMax();
    }
}
