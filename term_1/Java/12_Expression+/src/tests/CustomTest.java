package tests;

import exceptions.*;
import parser.ExpressionParser;

public class CustomTest {

    private static void briefTests() {
        calculateExpression("x + (y ** z)", 1, 2, 3);
        calculateExpression("(z + x) ** y", 2, 3, 1);
        calculateExpression("h + (y ** z)", 1, 2, 3);

        calculateExpression("1 + 2 ** 3)");
        calculateExpression("1 + (2 ** 3");
        calculateExpression("1 + (2 ** 3333333333)");
        calculateExpression("1 + (22 ** 33)");
        calculateExpression("1   (2    3)");
        calculateExpression("1 + (2 ++ 3)");
        calculateExpression("! + (2 ** 3)");

        calculateExpression("11 + (22 ** 33)");
        calculateExpression("1 ** -1");
        calculateExpression("1 / 0");
        calculateExpression("-1 // 2");
        calculateExpression("13 // 1");
        calculateExpression("sqrt -2");
        calculateExpression("(min 5) ** 3");

        calculateExpression("bubaa + 2");
    }

    private static void calculateExpression(String expression, int x, int y, int z) {
        calculate(expression, x, y, z);
    }

    private static void calculateExpression(String expression) {
        calculate(expression, 0, 0, 0);
    }

    private static void calculate(String expression, int x, int y, int z) {
        assert expression != null : "Expression is null";

        ExpressionParser parser = new ExpressionParser();
        try {
            System.out.printf("%s = %s\n", showValues(expression, x, y, z), parser.parse(expression).evaluate(x, y, z));
        } catch (ParsingException | EvaluatingException e) {
            System.out.printf("\t\"%s\"\n\n%s\n", showValues(expression, x, y, z), e.getMessage());
        } catch (NullPointerException e) {
            System.out.print(e.getMessage());
        }
        border();
    }

    private static void border() {
        System.out.println("><><><><><><><><><><><><><><><><><><><><><><><");
    }

    private static String showValues(String expression, int x, int y, int z) {
        return expression.replaceAll("x", String.valueOf(x))
                .replaceAll("y", String.valueOf(y))
                .replaceAll("z", String.valueOf(z));
    }

    public static void main(String[] args) {
        briefTests();
    }
}