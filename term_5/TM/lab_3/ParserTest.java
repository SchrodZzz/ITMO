import gen.MyGrammarLexer;
import gen.MyGrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ParserTest {

    @Test
    public void testEmpty() {
        test("",
                expectedPairs());
    }

    @Test
    public void testEsterEgg() {
        test("a = 2 @ 3",
                expectedPairs("a", 1337));
    }

    @Test
    public void testBasic() {
        test("""
                        a = 2;
                        b = a + 2;
                        c = a + b * (b - 3); a = 3;
                        c = a + b * (b - 3);
                        """,
                expectedPairs("a", 2, "b", 4, "c", 6, "a", 3, "c", 7));
    }

    @Test
    public void testBigNames() {
        test("""
                        should_be_15 = 15;
                        a_1337 = 10 + __superVar = 10;
                         """,
                expectedPairs("should_be_15", 15, "__superVar", 10, "a_1337", 20));
    }

    @Test
    public void testAssignments() {
        test("""
                        a = b = c = d = 42 + 20 * (e = 10) - f = 12 + 2;
                        """,
                expectedPairs("e", 10, "f", 14, "d", 228, "c", 228, "b", 228, "a", 228));
    }

    @Test
    public void testPriorities() {
        test("""
                        a = 2 + 2 * 2;
                        b = 2 * 2 + 2;
                        c = 2 * b = 7 + 13;
                        d = 20 - 10 - 15 + 1;
                        e = d / -2;
                        f = 11 + -2;
                        g = 11 -- 11;
                        k = -(10 + 15);
                        l = - g = 10;
                        """,
                expectedPairs("a", 6, "b", 6, "b", 20, "c", 40, "d", -4, "e", 2, "f", 9, "g", 22,
                        "k", -25, "g", 10, "l", -10));
    }

    @Test
    public void testBraces() {
        test("""
                        a = 10 + (((15 - (((42 + b = ((-10))))) + 1)));
                        """,
                expectedPairs("b", -10, "a", -6));
    }

    @Test
    public void testTrim() {
        test("""
                        a = \t    \t 12 +

                                                 13 \t \t

                        - 42 + b =
                                       \t    1337;
                        """,
                expectedPairs("b", 1337, "a", 1320));
    }

    @Test
    public void testBigNums() {
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        test(String.format("""
                        a = %d;
                        b = %d;
                        c = a + b;
                        d = %d + %d;
                        """, max, min, max, min),
                expectedPairs("a", max, "b", min, "c", -1, "d", -1));
    }

    @Test
    public void testPow() {
        test("""
                        a = 2;
                        b = 3;
                        c = a ** b;
                        d = c ** 2;
                        """,
                expectedPairs("a", 2, "b", 3, "c", 8, "d", 64));
    }

    @Test
    public void testPowAssoc() {
        test("""
                        a = 2;
                        b = 3;
                        c = a ** b ** 2;
                        """,
                expectedPairs("a", 2, "b", 3, "c", 512));
    }


    // MARK: Private

    private void test(String data, List<Map.Entry<String, Integer>> expected) {
        MyGrammarLexer lexer = new MyGrammarLexer(CharStreams.fromString(data));
        MyGrammarParser parser = new MyGrammarParser(new CommonTokenStream(lexer));
        parser.main();
        assertArrayEquals(expected.toArray(), parser.assignments.toArray());
    }

    private List<Map.Entry<String, Integer>> expectedPairs(Object... args) {
        List<Map.Entry<String, Integer>> arr = new ArrayList<>();
        for (int i = 0; i < args.length; i += 2) {
            arr.add(new AbstractMap.SimpleEntry<>((String) args[i], (Integer) args[i + 1]));
        }
        return arr;
    }
}
