package tests;

import generated.helper.*;
import basicCalculator.BasicCalculatorLexer;
import basicCalculator.BasicCalculatorParser;
import generated.helper.runtime.LexingException;
import generated.helper.runtime.ParsingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTests {

    // MARK: - Calculator

    @Test
    void testPow() {
        testCalculator("2 ^ 2 ^ 2", 16);
    }

    @Test
    void testPowAdv() {
        testCalculator("2 ^ 3 + 2 ^ (3 + 2 ^ 2)", 8 + 128);
    }

    @Test
    void testy() {
        testCalculator("2 ^ 3 ^ 3", -4);
    }

    @Test
    void testMinus() {
        testCalculator("2 - 2 * (2 - 10)", 18);
    }

    @Test
    void testCommon() {
        testCalculator("2 + (2 + 2) * (2 + 2 - 3)", 6);
        testCalculator("3 + (2 + 2) * (2 + 2 - 3)", 7);
        testCalculator("10 + 20 * 10 - (12 + 1)", 197);
    }

    @Test
    void testPriority() {
        testCalculator("2 + 2 * 2", 6);
        testCalculator("2 * 2 + 2", 6);
        testCalculator("20 - 10 - 15 + 1", -4);
        testCalculator("(20 - 10 - 15 + 1) / -2", 2);
        testCalculator("11 + -2", 9);
        testCalculator("11 -- 11", 22);
        testCalculator("-(10 + 15)", -25);
        testCalculator("-11 -- 11", 0);
    }

    @Test
    void testBracesA() {
        testCalculator("10 + (((15 - (((42 +  ((-10))))) + 1)))", -6);
    }

    @Test
    void testWhitespace() {
        testCalculator("""
                   \t    \t 10 +
                   15 \t \t
                   - 43 +
                   \t \t


                                       12
                """, -6);
    }

    private void testCalculator(String data, int expected) {
        CalcParser parser = new CalcParser(new CalcLexer(data));
        int res = 1;
        try {
            res = (int) parser.mainRule();
        } catch (ParsingException | LexingException e) {
            System.out.println(e);
        }
        assertEquals(expected, res);
    }


    // MARK: - 3rd lab
//
//    @Test
//    public void testEmpty() {
//        test("",
//                expectedPairs());
//    }
//
//    @Test
//    public void testBasic() {
//        test("""
//                        a = 2;
//                        b = a + 2;
//                        c = a + b * (b - 3); a = 3;
//                        c = a + b * (b - 3);
//                        """,
//                expectedPairs("a", 2, "b", 4, "c", 6, "a", 3, "c", 7));
//    }
//
//    @Test
//    public void testBigNames() {
//        test("""
//                        should_be_15 = 15;
//                        a_1337 = 10 + __superVar = 10;
//                         """,
//                expectedPairs("should_be_15", 15, "__superVar", 10, "a_1337", 20));
//    }
//
//    @Test
//    public void testAssignments() {
//        test("""
//                        a = b = c = d = 42 + 20 * (e = 10) - f = 12 + 2;
//                        """,
//                expectedPairs("e", 10, "f", 14, "d", 228, "c", 228, "b", 228, "a", 228));
//    }
//
//    @Test
//    public void testPriorities() {
//        test("""
//                        a = 2 + 2 * 2;
//                        b = 2 * 2 + 2;
//                        c = 2 * b = 7 + 13;
//                        d = 20 - 10 - 15 + 1;
//                        e = d / -2;
//                        f = 11 + -2;
//                        g = 11 -- 11;
//                        k = -(10 + 15);
//                        l = - g = 10;
//                        """,
//                expectedPairs("a", 6, "b", 6, "b", 20, "c", 40, "d", -4, "e", 2, "f", 9, "g", 22,
//                        "k", -25, "g", 10, "l", -10));
//    }
//
//    @Test
//    public void testBraces() {
//        test("""
//                        a = 10 + (((15 - (((42 + b = ((-10))))) + 1)));
//                        """,
//                expectedPairs("b", -10, "a", -6));
//    }
//
//    @Test
//    public void testTrim() {
//        test("""
//                        a = \t    \t 12 +
//
//                                                 13 \t \t
//
//                        - 42 + b =
//                                       \t    1337;
//                        """,
//                expectedPairs("b", 1337, "a", 1320));
//    }
//
//    @Test
//    public void testBigNums() {
//        int max = Integer.MAX_VALUE;
//        int min = Integer.MIN_VALUE;
//        test(String.format("""
//                        a = %d;
//                        b = %d;
//                        c = a + b;
//                        d = %d + %d;
//                        """, max, min, max, min),
//                expectedPairs("a", max, "b", min, "c", -1, "d", -1));
//    }
//
//
//    // MARK: Private
//
//    private void test(String data, List<Map.Entry<String, Integer>> expected) {
//        ThirdLabParser parser = new ThirdLabParser(new ThirdLabLexer(data));
//        assertArrayEquals(expected.toArray(), ((List<Map.Entry<String, Integer>>) parser.root().getRes()).toArray());
//    }
//
//    private List<Map.Entry<String, Integer>> expectedPairs(Object... args) {
//        List<Map.Entry<String, Integer>> arr = new ArrayList<>();
//        for (int i = 0; i < args.length; i += 2) {
//            arr.add(new AbstractMap.SimpleEntry<>((String) args[i], (Integer) args[i + 1]));
//        }
//        return arr;
//    }

}
