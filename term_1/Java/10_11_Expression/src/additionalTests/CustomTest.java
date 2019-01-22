/**
 * @Written by Andrey "SchrodZzz" Ivshin
 * @Date: 12/3/18 8:42 AM
 */

package additionalTests;

import parser.*;

import java.util.ArrayList;

public class CustomTest {

    private static ArrayList<Boolean> testResults = new ArrayList<>();
    private static ExpressionParser parser = new ExpressionParser();
    private static int testCounter = 1;

    private static void parseTest(String currentExpression, int expectedAnswer, int x, int y, int z) {
        testNum();
        int expressionResult;

        System.out.print("\t(" + currentExpression
                .replaceAll("x", String.valueOf(x))
                .replaceAll("y", String.valueOf(y))
                .replaceAll("z", String.valueOf(z))
                + ") = ");

        if (!currentExpression.contains("count")) {
            expressionResult = parser.parse(
                    "((((((((((((((("
                            + currentExpression.substring(0, currentExpression.length() / 2)
                            + "\n\t\r"
                            + currentExpression.substring(currentExpression.length() / 2)
                            + "))))))))))))())))")
                    .evaluate(x, y, z);
        } else {
            expressionResult = parser.parse("((" + currentExpression + "\n\t\r))").evaluate(5, 0, 0);
        }

        System.out.print(expressionResult);
        System.out.printf(" == %d\n", expectedAnswer);
        testResults.add(expectedAnswer == expressionResult);

        border();
    }

    private static void border() {
        System.out.println("><><><><><><><><><><><><><><><><><");
    }

    private static void testNum() {
        System.out.printf("№%d\n", testCounter++);
    }

    private static void standardTest() {
        parseTest("x * (x - 2) * x + 1", 76, 5, 0, 0);
    }

    private static void bitwiseTest() {
        parseTest("6 & 1 + 2", 2, 0, 0, 0);
        parseTest("6 & (1 + 2)", 2, 0, 0, 0);

        parseTest("6 ^ 1 + 2", 5, 0, 0, 0);
        parseTest("6 ^ (1 + 2)", 5, 0, 0, 0);

        parseTest("6 | 1 + 2", 7, 0, 0, 0);
        parseTest("6 | (1 + 2)", 7, 0, 0, 0);
    }

    private static void countTest() {
        parseTest("~-5", 4, 0, 0, 0);

        parseTest("count -5", 31, 0, 0, 0);
    }

    private static void bonusTest() {
        parseTest("error or exception or message required", 0, 0, 0, 0);

        parseTest("x + (y + (-z)) / 2 * x - z", -15691, 76, 1337, 1707);

        parseTest("5+3 -weebs are the smartest people + 2", 10, 0, 0, 0);
    }

    private static void result() {
        if (!testResults.contains(false)) {
            System.out.println("\t\t TEST COMPLETED");
        } else {
            System.out.println("\t\t\tTEST FAILED\n");
            int testNumber = 0;
            for (boolean currentCorrectness : testResults) {
                ++testNumber;
                if (!currentCorrectness) {
                    System.out.printf("Failed in test №%d\n", testNumber);
                }
            }
        }
        border();
    }

    public static void main(String[] args) {
        standardTest();
        bitwiseTest();
        countTest();

        bonusTest();

        result();
    }
}