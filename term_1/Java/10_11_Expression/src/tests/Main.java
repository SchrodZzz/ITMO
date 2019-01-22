package tests;

import expressions.*;

public class Main {

    private static void myFirstTest() {
        System.out.println(new Subtract(
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        ),
                        new Const(3)
                ).evaluate(5)
        );
    }

    private static void myArgsTest(int value) {
        System.out.println("Entered value is equal: " + value);
        System.out.print("((" + value + " * 2) - (2 * " + value + ") + 1) = ");
        System.out.println(new Add(
                        new Subtract(
                                new Multiply(
                                        new Variable("x"),
                                        new Const(2)
                                ), new Multiply(
                                new Const(2),
                                new Variable("x")
                        )), new Const(1)
                ).evaluate(value)
        );
    }

    private static void myTripleTest() {
        System.out.println(new Divide(
                        new Multiply(
                                new Add(
                                        new Variable("x"),
                                        new Variable("y")
                                ), new Subtract(
                                new Variable("z"),
                                new Const(69))
                        ), new Const(-333)
                ).evaluate(2, 72, 24)
        );
    }

    private static void border() {
        System.out.println("\n<><><><><><><><><><><><><><><><><><>\n");
    }

    private static void expressionsTest(int argsValue) {
        String howToUse = "Enter one digit in program arguments";
        try {
            myFirstTest();         // expected - 7
            border();
            myArgsTest(argsValue); // expected - 1
            border();
            myTripleTest();        // expected - 10
        } catch (NumberFormatException e) {
            System.out.println("Invalid input found. " + howToUse);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No arguments found. " + howToUse);
        }
    }

    public static void main(String[] args) {

        expressionsTest(Integer.parseInt(args[0]));

    }
}