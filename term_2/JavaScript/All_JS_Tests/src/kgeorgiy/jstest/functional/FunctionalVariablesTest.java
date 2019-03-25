package kgeorgiy.jstest.functional;

import kgeorgiy.jstest.ArithmeticTests;
import kgeorgiy.jstest.Language;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class FunctionalVariablesTest extends FunctionalExpressionTest {
    public static class VariablesTests extends ArithmeticTests {
    }

    protected FunctionalVariablesTest(final Language language, final boolean testParsing) {
        super(language, testParsing);
    }

    public static void main(final String... args) {
        test(FunctionalVariablesTest.class, FunctionalVariablesTest::new, args, new VariablesTests());
    }
}