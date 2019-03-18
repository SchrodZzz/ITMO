package parser;

import exceptions.ParserException;
import expressions.Const;
import expressions.TripleExpression;
import expressions.Variable;
import expressions.operators.*;
import generics.Types;

import java.util.List;

public class ExpressionParser<T> implements Parser<T> {
    private int index = 0;
    private char curChar;
    private String expression;
    private int parenthesisBalance = 0;

    private final List<String> operators = List.of(
            "square",
            "count",
            "abs",
            "min",
            "max",
            "mod"
    );

    private final Types<T> parser;

    public ExpressionParser(Types<T> parser) {
        this.parser = parser;
    }

    private void checkCurChar() throws ParserException {
        if (!curCharIsCorrect() && !operators.contains(getOperator())) {
            System.out.println();
            throw new ParserException("Unknown symbol " + curChar + " at position " + index);
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private boolean curCharIsCorrect() {
        return ("+-*/()xyz0123456789\0").contains(Character.toString(curChar));
    }

    private T getVal() throws ParserException {
        int start = index;
        while (Character.isDigit(expression.charAt(index))) {
            index++;
        }
        return parser.parse2Digit(expression.substring(start, index));
    }

    private String getOperator() {
        int end = index;
        while (Character.isLetter(expression.charAt(end))) {
            end++;
        }
        return expression.substring(index, end);
    }

    private void getCurChar() throws ParserException {
        skipWhitespace();
        curChar = expression.charAt(index);
        checkCurChar();
    }

    private void parenthesisCheck() throws ParserException {
        parenthesisBalance += (curChar == '(') ? 1 : (curChar == ')') ? -1 : 0;
        //System.out.printf("%c : %d\n", curChar,parenthesisBalance);
        if (parenthesisBalance < 0) {
            throw new ParserException("Missing opening parenthesis");
        } else if (index + 1 == expression.length() && parenthesisBalance > 0) {
            throw new ParserException("Missing closing parenthesis");
        }
    }

    private TripleExpression<T> unary() throws ParserException {
        getCurChar();
        parenthesisCheck();
        switch (curChar) {
            case '(': {
                parenthesisBalance++;
                index++;
                TripleExpression<T> curRes = minAndMax();
                index++;
                return curRes;
            }
            case '-': {
                index++;
                return new Negate<>(unary());
            }
            case 'x': {
                index++;
                return new Variable<>("x");
            }
            case 'y': {
                index++;
                return new Variable<>("y");
            }
            case 'z': {
                index++;
                return new Variable<>("z");
            }
            case 'a': {
                index += 3;
                return new Abs<>(unary());
            }
            case 'c': {
                index += 5;
                return new Count<>(unary());
            }
            case 's': {
                index += 6;
                return new Square<>(unary());
            }
            default:
                if (Character.isDigit(curChar)) {
                    return new Const<>(getVal());
                } else {
                    throw new ParserException("Missed argument at position " + index);
                }
        }
    }

    private TripleExpression<T> mulAndDiv() throws ParserException {
        TripleExpression<T> curRes = unary();
        getCurChar();
        while (true) {
            parenthesisCheck();
            switch (curChar) {
                case '*': {
                    index++;
                    curRes = new Multiply<>(curRes, unary());
                    break;
                }
                case '/': {
                    index++;
                    skipWhitespace();
                    curRes = new Divide<>(curRes, unary());
                    break;
                }
                case 'm': {
                    if (getOperator().equals("mod")) {
                        index += 3;
                        curRes = new Mod<>(curRes, unary());
                    }
                }
                default:
                    skipWhitespace();
                    return curRes;
            }
            getCurChar();
        }
    }

    private TripleExpression<T> addAndSub() throws ParserException {
        TripleExpression<T> curRes = mulAndDiv();
        getCurChar();
        while (true) {
            parenthesisCheck();
            switch (curChar) {
                case '+': {
                    index++;
                    curRes = new Add<>(curRes, mulAndDiv());
                    break;
                }
                case '-': {
                    index++;
                    curRes = new Subtract<>(curRes, mulAndDiv());
                    break;
                }
                default:
                    return curRes;
            }
            getCurChar();
        }
    }

    private TripleExpression<T> minAndMax() throws ParserException {
        TripleExpression<T> curRes = addAndSub();
        getCurChar();
        while (true) {
            switch (getOperator()) {
                case "min":
                    index += 3;
                    curRes = new Min<>(curRes, addAndSub());
                    break;
                case "max":
                    index += 3;
                    curRes = new Max<>(curRes, addAndSub());
                    break;
                default:
                    return curRes;
            }
        }
    }

    public TripleExpression<T> parse(final String expression) throws ParserException {
        this.expression = expression + "\0";
        return minAndMax();
    }
}