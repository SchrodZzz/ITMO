package kgeorgiy.jstest.object;

import kgeorgiy.jstest.ArithmeticTests;
import kgeorgiy.jstest.Language;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ObjectMinMaxTest extends ObjectExpressionTest {
    public static final Dialect MIN_MAX_OBJECT = ObjectExpressionTest.ARITHMETIC_OBJECT.copy()
            .rename("min3", "Min3")
            .rename("max5", "Max5");

    public static class MinMaxTests extends ArithmeticTests {{
        any("min3", 3, args -> args.stream().min(Double::compare).get());
        any("max5", 5, args -> args.stream().max(Double::compare).get());
        tests(
                f("min3", vx, vy, vz),
                f("max5", vx, vy, vz, c(7), f("*", vy, vz)),
                f("min3", vx, f("-", vy, vz), c(7))
        );
    }}

    protected ObjectMinMaxTest(final int mode, final Language language) {
        super(mode, language);
    }

    public static void main(final String... args) {
        test(ObjectMinMaxTest.class, ObjectMinMaxTest::new, new MinMaxTests(), args, MIN_MAX_OBJECT);
    }
}
