package parser;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import exceptions.*;

public class Tokenizer {
    private String expression;
    private int index;

    private Token currentToken;

    private static Set <Token> operations = EnumSet.of(Token.ADD, Token.DIVIDE, Token.MULTIPLY, Token.UNARY_MINUS,
            Token.SUBTRACT, Token.LOG, Token.LOG10, Token.POW, Token.POW10,
            Token.ABS, Token.SQRT, Token.MIN, Token.MAX);

    private static Set <Token> binaryOperations = EnumSet.of(Token.ADD, Token.DIVIDE, Token.MULTIPLY,
            Token.SUBTRACT, Token.LOG, Token.POW, Token.MIN, Token.MAX);

    private static Map <String, Token> operationMatcher = new HashMap <>();

    static {
        operationMatcher.put("min", Token.MIN);
        operationMatcher.put("max", Token.MAX);
        operationMatcher.put("abs", Token.ABS);
        operationMatcher.put("sqrt", Token.SQRT);
        operationMatcher.put("log10", Token.LOG10);
        operationMatcher.put("pow10", Token.POW10);
        operationMatcher.put("x", Token.VARIABLE);
        operationMatcher.put("y", Token.VARIABLE);
        operationMatcher.put("z", Token.VARIABLE);
    }

    public Tokenizer(final String expression) {
        this.expression = expression;
        index = 0;
        currentToken = Token.EXPRESSION_BEGIN;
        braceCounter = 0;
    }

    private int value;
    private String name;
    private int braceCounter;

    public Token getNextToken() throws ParsingException {
        nextToken();
        return currentToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public String getExpression() {
        return expression;
    }

    private void operandCheck(final int position) throws MissingOperandException {
        if (operations.contains(currentToken) || currentToken == Token.OPEN_BRACE || currentToken == Token.EXPRESSION_BEGIN) {
            throw new MissingOperandException(expression, position);
        }
    }

    private void operationCheck(final int position) throws MissingOperationException {
        if (currentToken == Token.CLOSE_BRACE || currentToken == Token.VARIABLE || currentToken == Token.CONST) {
            throw new MissingOperationException(expression, position);
        }
    }

    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            ++index;
        }
    }

    private String getNumber() {
        int numberStartIndex = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        int numberEndIndex = index;
        --index;
        return expression.substring(numberStartIndex, numberEndIndex);
    }

    private String getIdentifier() throws UnknownSymbolException {
        if (!Character.isLetter(expression.charAt(index))) {
            throw new UnknownSymbolException(expression, index);
        }
        int l = index;
        while (index < expression.length() && Character.isLetterOrDigit(expression.charAt(index))) {
            ++index;
        }
        int r = index;
        --index;
        return expression.substring(l, r);
    }

    private void nextToken() throws ParsingException {
        skipWhitespace();
        if (index >= expression.length()) {
            operandCheck(index);
            currentToken = Token.EXPRESSION_END;
            return;
        }

        char currentCharacter = expression.charAt(index);
        switch (expression.charAt(index)) {
            case '+':
                operandCheck(index);
                if (index + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, index + 1);
                }
                currentToken = Token.ADD;
                break;
            case '-':
                if (currentToken == Token.CONST || currentToken == Token.VARIABLE || currentToken == Token.CLOSE_BRACE) {
                    currentToken = Token.SUBTRACT;
                } else {
                    if (index + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, index + 1);
                    }
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        ++index;
                        String temp = getNumber();
                        try {
                            value = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException NFE) {
                            throw new IllegalConstantException("-" + temp, expression, index - temp.length());
                        }
                        currentToken = Token.CONST;
                    } else {
                        currentToken = Token.UNARY_MINUS;
                    }
                }
                break;
            case '/':
                operandCheck(index);
                if (index + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, index + 1);
                }
                if (expression.charAt(index + 1) == '/') {
                    ++index;
                    currentToken = Token.LOG;
                } else {
                    currentToken = Token.DIVIDE;
                }
                break;
            case '*':
                operandCheck(index);
                if (index + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, index + 1);
                }
                if (expression.charAt(index + 1) == '*') {
                    ++index;
                    currentToken = Token.POW;
                } else {
                    currentToken = Token.MULTIPLY;
                }
                break;
            case '(':
                operationCheck(index);
                ++braceCounter;
                currentToken = Token.OPEN_BRACE;
                break;
            case ')':
                operandCheck(index);
                --braceCounter;
                if (braceCounter < 0) {
                    throw new ClosingParenthesisException(expression, index);
                }
                currentToken = Token.CLOSE_BRACE;
                break;
            default:
                if (Character.isDigit(currentCharacter)) {
                    operationCheck(index);
                    String currentNumber = getNumber();
                    try {
                        value = Integer.parseInt(currentNumber);
                    } catch (NumberFormatException NFE) {
                        throw new IllegalConstantException(currentNumber,
                                expression, index - currentNumber.length() + 1);
                    }
                    currentToken = Token.CONST;
                } else {
                    String currentIndex = getIdentifier();
                    if (!operationMatcher.containsKey(currentIndex)) {
                        throw new UnknownIdentifierException(currentIndex,
                                expression, index - currentIndex.length() + 1);
                    }
                    if (binaryOperations.contains(operationMatcher.get(currentIndex))) {
                        operandCheck(index - currentIndex.length() + 1);
                    } else {
                        operationCheck(index - currentIndex.length() + 1);
                    }
                    currentToken = operationMatcher.get(currentIndex);
                    if (currentToken == Token.VARIABLE) {
                        name = currentIndex.substring(0, 1);
                    }
                }
        }
        ++index;
    }
}