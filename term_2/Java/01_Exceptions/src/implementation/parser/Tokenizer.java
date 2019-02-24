package implementation.parser;

import implementation.exceptions.*;

import java.util.EnumSet;
import java.util.Map;


class Tokenizer {
    private int value;
    private int braceBalance;
    private int positionInExpression;

    private String expression;
    private String variableName;

    private Token currentToken;

    private static EnumSet <Token> BINARY_OPERATORS = EnumSet.of(Token.ADD, Token.DIVIDE, Token.MULTIPLY,
            Token.SUBTRACT, Token.LOG, Token.POW, Token.MIN, Token.MAX);

    private static Map <String, Token> IDENTIFIER_MATCHER = Map.of(
            "min", Token.MIN,
            "max", Token.MAX,
            "abs", Token.ABS,
            "sqrt", Token.SQRT,
            "log10", Token.LOG10,
            "low", Token.LOW,
            "high", Token.HIGH,
            "x", Token.VARIABLE,
            "y", Token.VARIABLE,
            "z", Token.VARIABLE
    );

    Tokenizer(final String expression) {
        this.expression = expression;
        variableName = "";

        currentToken = Token.EXPRESSION_BEGINNING;

        value = 0;
        braceBalance = 0;
        positionInExpression = 0;
    }

    Token getNextToken() throws ParsingException {
        nextToken();
        return currentToken;
    }

    Token getCurrentToken() {
        return currentToken;
    }

    int getValue() {
        return value;
    }

    String getVariableName() {
        return variableName;
    }

    int getPositionIndex() {
        return positionInExpression;
    }

    public String getExpression() {
        return expression;
    }

    private void operandCheck(final int position) throws MissingOperandException {
        if (!(currentToken == Token.CLOSING_BRACE || currentToken == Token.VARIABLE || currentToken == Token.CONST)) {
            throw new MissingOperandException(expression, position);
        }
    }

    private void operatorCheck(final int position) throws MissingOperatorException {
        if (currentToken == Token.CLOSING_BRACE || currentToken == Token.VARIABLE || currentToken == Token.CONST) {
            throw new MissingOperatorException(expression, position);
        }
    }

    private void skipWhitespace() {
        while (positionInExpression < expression.length() &&
                Character.isWhitespace(expression.charAt(positionInExpression))) {
            ++positionInExpression;
        }
    }

    private String getNumber() {
        int numberStartIndex = positionInExpression;
        while (positionInExpression < expression.length() &&
                Character.isDigit(expression.charAt(positionInExpression))) {
            positionInExpression++;
        }
        int numberEndIndex = positionInExpression;
        --positionInExpression;
        return expression.substring(numberStartIndex, numberEndIndex);
    }

    private String getIdentifier() throws UnknownSymbolException {
        if (!Character.isLetter(expression.charAt(positionInExpression))) {
            throw new UnknownSymbolException(expression, positionInExpression);
        }
        int l = positionInExpression;
        while (positionInExpression < expression.length() &&
                Character.isLetterOrDigit(expression.charAt(positionInExpression))) {
            ++positionInExpression;
        }
        int r = positionInExpression;
        --positionInExpression;
        return expression.substring(l, r);
    }

    private void nextToken() throws ParsingException {
        skipWhitespace();
        if (positionInExpression >= expression.length()) {
            operandCheck(positionInExpression);
            return;
        }

        char currentCharacter = expression.charAt(positionInExpression);
        switch (expression.charAt(positionInExpression)) {
            case '+':
                operandCheck(positionInExpression);
                if (positionInExpression + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, positionInExpression + 1);
                }
                currentToken = Token.ADD;
                break;
            case '-':
                if (currentToken == Token.CONST || currentToken == Token.VARIABLE
                        || currentToken == Token.CLOSING_BRACE) {
                    currentToken = Token.SUBTRACT;
                } else {
                    if (positionInExpression + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, positionInExpression + 1);
                    }
                    if (Character.isDigit(expression.charAt(positionInExpression + 1))) {
                        ++positionInExpression;
                        String tmp = getNumber();
                        try {
                            value = Integer.parseInt("-" + tmp);
                        } catch (NumberFormatException e) {
                            throw new IllegalConstantException("-" + tmp, expression,
                                    positionInExpression - tmp.length());
                        }
                        currentToken = Token.CONST;
                    } else {
                        currentToken = Token.UNARY_MINUS;
                    }
                }
                break;
            case '/':
                operandCheck(positionInExpression);
                if (positionInExpression + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, positionInExpression + 1);
                }
                if (expression.charAt(positionInExpression + 1) == '/') {
                    ++positionInExpression;
                    currentToken = Token.LOG;
                } else {
                    currentToken = Token.DIVIDE;
                }
                break;
            case '*':
                operandCheck(positionInExpression);
                if (positionInExpression + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, positionInExpression + 1);
                }
                if (expression.charAt(positionInExpression + 1) == '*') {
                    ++positionInExpression;
                    currentToken = Token.POW;
                } else {
                    currentToken = Token.MULTIPLY;
                }
                break;
            case '(':
                operatorCheck(positionInExpression);
                ++braceBalance;
                currentToken = Token.OPENING_BRACE;
                break;
            case ')':
                operandCheck(positionInExpression);
                --braceBalance;
                if (braceBalance < 0) {
                    throw new ClosingParenthesisException(expression, positionInExpression);
                }
                currentToken = Token.CLOSING_BRACE;
                break;
            default:
                if (Character.isDigit(currentCharacter)) {
                    operatorCheck(positionInExpression);
                    String currentNumber = getNumber();
                    try {
                        value = Integer.parseInt(currentNumber);
                    } catch (NumberFormatException NFE) {
                        throw new IllegalConstantException(currentNumber,
                                expression, positionInExpression - currentNumber.length() + 1);
                    }
                    currentToken = Token.CONST;
                } else {
                    String currentIdentifier = getIdentifier();
                    if (!IDENTIFIER_MATCHER.containsKey(currentIdentifier)) {
                        throw new UnknownIdentifierException(currentIdentifier,
                                expression, positionInExpression - currentIdentifier.length() + 1);
                    }
                    if (BINARY_OPERATORS.contains(IDENTIFIER_MATCHER.get(currentIdentifier))) {
                        operandCheck(positionInExpression - currentIdentifier.length() + 1);
                    } else {
                        operatorCheck(positionInExpression - currentIdentifier.length() + 1);
                    }
                    currentToken = IDENTIFIER_MATCHER.get(currentIdentifier);
                    if (currentToken == Token.VARIABLE) {
                        variableName = currentIdentifier.substring(0, 1);
                    }
                }
        }
        ++positionInExpression;
    }
}