package parser;

import expressions.TripleExpression;
import additionExpressions.*;

public class ExpressionParser implements Parser {

    private String expression;
    private String variable;
    private int index;
    private int value;

    private enum Token {
        INCORRECT_EXPRESSION,
        EXPRESSION_END,
        OPEN_BRACE,
        CLOSE_BRACE,

        CONST,
        VARIABLE,

        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,

        BITWISE_AND,
        BITWISE_OR,
        BITWISE_XOR,

        UNARY_MINUS,
        BITWISE_NOT,
        BIT_COUNT
    }

    private Token curToken = Token.INCORRECT_EXPRESSION;

    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            ++index;
        }
    }

    private void nextToken() {
        skipWhitespace();
        if (index >= expression.length()) {
            curToken = Token.EXPRESSION_END;
        } else {
            char currentCharacter = expression.charAt(index);
            switch (expression.charAt(index)) {
                case '+':
                    curToken = Token.ADD;
                    break;
                case '-':
                    if (curToken == Token.CONST || curToken == Token.VARIABLE || curToken == Token.CLOSE_BRACE) {
                        curToken = Token.SUBTRACT;
                    } else {
                        curToken = Token.UNARY_MINUS;
                    }
                    break;
                case '/':
                    curToken = Token.DIVIDE;
                    break;
                case '*':
                    curToken = Token.MULTIPLY;
                    break;
                case '(':
                    curToken = Token.OPEN_BRACE;
                    break;
                case ')':
                    curToken = Token.CLOSE_BRACE;
                    break;
                case '&':
                    curToken = Token.BITWISE_AND;
                    break;
                case '|':
                    curToken = Token.BITWISE_OR;
                    break;
                case '^':
                    curToken = Token.BITWISE_XOR;
                    break;
                case '~':
                    curToken = Token.BITWISE_NOT;
                    break;
                default:
                    if (Character.isDigit(currentCharacter)) {
                        int l = index;
                        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                            ++index;
                        }

                        value = Integer.parseUnsignedInt(expression.substring(l, index));
                        curToken = Token.CONST;
                        --index;
                    } else if (index + 5 <= expression.length()
                            && expression.substring(index, index + 5).equals("count")) {
                        index += 4;
                        curToken = Token.BIT_COUNT;
                    } else if (Character.isLetter(expression.charAt(index))) {
                        int l = index;
                        while (index < expression.length() && Character.isLetter(expression.charAt(index))) {
                            ++index;
                        }

                        variable = expression.substring(l, index);
                        curToken = Token.VARIABLE;
                        --index;
                    } else {
                        curToken = Token.INCORRECT_EXPRESSION;
                    }
            }
        }
        ++index;
    }

    private TripleExpression unary() {
        nextToken();
        TripleExpression operationResult;

        switch (curToken) {
            case CONST:
                operationResult = new Const(value);
                nextToken();
                break;
            case VARIABLE:
                operationResult = new Variable(variable);
                nextToken();
                break;
            case UNARY_MINUS:
                operationResult = new Not(unary());
                break;
            case BITWISE_NOT:
                operationResult = new BitwiseNot(unary());
                break;
            case BIT_COUNT:
                operationResult = new BitCount(unary());
                break;
            case OPEN_BRACE:
                operationResult = or();
                nextToken();
                break;
            default:
                return new Const(0);
        }
        return operationResult;
    }

    private TripleExpression mulAndDiv() {
        TripleExpression operationResult = unary();
        while (true) {
            switch (curToken) {
                case MULTIPLY:
                    operationResult = new Multiply(operationResult, unary());
                    break;
                case DIVIDE:
                    operationResult = new Divide(operationResult, unary());
                    break;
                default:
                    return operationResult;
            }
        }
    }

    private TripleExpression addAndSub() {
        TripleExpression operationResult = mulAndDiv();
        while (true) {
            switch (curToken) {
                case ADD:
                    operationResult = new Add(operationResult, mulAndDiv());
                    break;
                case SUBTRACT:
                    operationResult = new Subtract(operationResult, mulAndDiv());
                    break;
                default:
                    return operationResult;
            }
        }
    }

    private TripleExpression and() {
        TripleExpression operationResult = addAndSub();
        while (true) {
            switch (curToken) {
                case BITWISE_AND:
                    operationResult = new BitwiseAnd(operationResult, addAndSub());
                    break;
                default:
                    return operationResult;
            }
        }
    }

    private TripleExpression xor() {
        TripleExpression operationResult = and();
        while (true) {
            switch (curToken) {
                case BITWISE_XOR:
                    operationResult = new BitwiseXor(operationResult, and());
                    break;
                default:
                    return operationResult;
            }
        }
    }

    private TripleExpression or() {
        TripleExpression operationResult = xor();
        while (true) {
            switch (curToken) {
                case BITWISE_OR:
                    operationResult = new BitwiseOr(operationResult, xor());
                    break;
                default:
                    return operationResult;
            }
        }
    }

    public TripleExpression parse(final String expression) {
        assert expression != null : "Expression is null";

        index = 0;
        this.expression = expression;
        return or();
    }
}