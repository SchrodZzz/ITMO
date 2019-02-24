package implementation;

import implementation.exceptions.EvaluatingException;
import implementation.exceptions.ParsingException;
import implementation.parser.ExpressionParser;

import java.util.Scanner;

public class CustomTests {

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

        calculateExpression("goose + 2");
        calculateExpression("-------------------1");
        calculateExpression("-*2");

        calculateExpression("high low 6");
        calculateExpression("abs high -4");
    }

    private static void kgTests() {
        for (int i = 0; i < 3; i++) {
            border();
        }
        ExpressionParser parser = new ExpressionParser();
        System.out.println("x\t\tf");
        for (int x = 0; x < 10; x++) {
            try {
                System.out.printf("%d\t\t%s\n", x, parser.parse("1000000*x*x*x*x*x/(x-1)")
                        .evaluate(x, 0, 0));
            } catch (ParsingException | EvaluatingException e) {
                System.out.printf("%d\t\t%s\n", x, e.getMessage());
            }
        }
        border();
    }

    private static void customTest() {
        Scanner reader = new Scanner(System.in);
        System.out.println("To end the custom test - enter \"exit\"");
        String expression;
        while (!(expression = reader.nextLine()).equals("exit")) {
            calculateExpression(expression);
        }
        System.out.println("Thank you for your tests :-D");
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
        kgTests();
        customTest();
    }
}
