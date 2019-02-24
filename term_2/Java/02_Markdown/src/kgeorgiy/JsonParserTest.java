package kgeorgiy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class JsonParserTest {

    @Test
    public void testNull() throws JsonException {
        testParse("null", null);
        assertError("hello");
    }


    @Test
    public void testTrue() throws JsonException {
        testParse("true", true);
    }

    @Test
    public void testFalse() throws JsonException {
        testParse("false", false);
    }

    @Test
    public void testInteger() throws JsonException {
        testParse("1", 1.0);
        testParse("123", 123.0);
        testParse("-123", -123.0);
        testParse("" + Integer.MIN_VALUE, (double) Integer.MIN_VALUE);
        testParse("" + Integer.MAX_VALUE, (double) Integer.MAX_VALUE);
        testParse("12345678900", 12345678900.0);
        assertError("123hello");
    }

    @Test
    public void testDouble() throws JsonException {
        testParse("1.1", 1.1);
        testParse("12.3", 12.3);
        testParse("-12.3", -12.3);
        testParse("1000000000000000.0", 1000000000000000.0);
        testParse("-1000000000000000.0", -1000000000000000.0);
        testParse("-12.3e10", -12.3e10);
        testParse("-12.3e-10", -12.3e-10);
    }

    @Test
    public void testString() throws JsonException {
        testString("hello");
        testString("");
        testString("1234");
        assertError("\"");
        assertError("\"123");
    }

    @Test
    public void testArray() throws JsonException {
        testParse("[]", List.of());
        testParse("[false]", List.of(false));
        testParse("[  false  ]", List.of(false));
        testParse("[  false  , true  ]", List.of(false, true));
        testParse("[false,true]", List.of(false, true));
        testParse("[  \"hello\"  , 123 , false ]", List.of("hello", 123.0, false));
        testParse("[[], [[]]]", List.of(List.of(), List.of(List.of())));
        assertError("[");
        assertError("[ true");
        assertError("[ true , ");
        assertError("[ true , ]");
    }

    @Test
    public void testObject() throws JsonException {
        testParse("{}", Map.of());
        testParse("{hello:false}", Map.of("hello", false));
        testParse("{   hello :   false  }", Map.of("hello", false));
        testParse("{  k1:false  , k2:true  }", Map.of("k1", false, "k2", true));
        testParse("{  \"k1\":\"hello\"  , k2:123 , k10:false }", Map.of("k1", "hello", "k2", 123.0, "k10", false));
        testParse("{a:{}, \nb:{c\n:{}}}", Map.of("a", Map.of(), "b", Map.of("c", Map.of())));
        assertError("{");
        assertError("{ true");
        assertError("{ true , ");
        assertError("{ true , ]");
        assertError("{ true , }");
        assertError("{ k: true");
        assertError("{ k: true , ");
        assertError("{ k: true , ]");
        assertError("{ k: true , }");
        assertError("{ k: }");
        assertError("{ k }");
    }

    @Test
    public void testErrors() throws JsonException {
        assertError("[\n1\n,2\n3]");
    }


    private void testString(final String string) throws JsonException {
        testParse("\"" + string + "\"", string);
    }

    private void assertError(final String input) {
        try {
            parse(input);
            Assertions.fail(input);
        } catch (final JsonException e) {
            // Ok
            System.err.println("Input: " + input);
            System.err.println(e.getMessage());
        } catch (final Exception e) {
            // Ok
            System.err.println("Input: " + input);
            throw e;
        }
    }

    private void testParse(final String input, final Object result) throws JsonException {
        try {
            Assertions.assertEquals(result, parse(input), input);
            Assertions.assertEquals(result, parse("  \t  " + input), input);
            Assertions.assertEquals(result, parse(input + "   "), input);
            Assertions.assertEquals(result, parse("  \t  " + input + "   "), input);
        } catch (final JsonException e) {
            System.err.format("Error while parsing '%s'%n", input);
            throw e;
        }
    }

    private Object parse(final String input) throws JsonException {
        return new JsonParser(new StringJsonSource(input)).parse();
    }
}
