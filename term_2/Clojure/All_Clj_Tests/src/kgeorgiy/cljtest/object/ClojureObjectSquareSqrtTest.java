package kgeorgiy.cljtest.object;

import kgeorgiy.cljtest.functional.ClojureFunctionalExpressionTest;
import kgeorgiy.cljtest.multi.MultiSquareSqrtTests;
import kgeorgiy.jstest.Language;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureObjectSquareSqrtTest extends ClojureObjectExpressionTest {
    public static final Dialect PARSED = ClojureObjectExpressionTest.PARSED.copy()
            .rename("square", "Square")
            .rename("sqrt", "Sqrt");

    protected ClojureObjectSquareSqrtTest(final boolean testMulti) {
        super(new Language(PARSED, ClojureFunctionalExpressionTest.UNPARSED, new MultiSquareSqrtTests(testMulti)));
    }

    public static void main(final String... args) {
        new ClojureObjectSquareSqrtTest(mode(args, ClojureObjectSquareSqrtTest.class)).run();
    }
}