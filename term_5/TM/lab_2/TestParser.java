import org.junit.jupiter.api.Test;

public class TestParser {

    Parser parser = new Parser(' ');

    @Test
    public void testVoid() {
        parser.process("void f();");
        parser.process("void f(int a);");
        parser.process("void f(int *a);");
        parser.process("void f(int &a);");
        parser.process("void f(int *&a);");
    }

    @Test
    public void testStdType() {
        parser.process("ll f();");
        parser.process("string f();");
        parser.process("int f();");
        parser.process("bool f();");
        parser.process("size_t f();");
    }

    @Test
    public void testArg() {
        parser.process("ll f(ll var);");
        parser.process("string f(ll *var);");
        parser.process("int f(ll &var);");
        parser.process("bool f(boolean *****var);");
        parser.process("size_t f(int a, int b, long c);");
    }

    @Test
    public void testRand() {
        parser.process("int func(s*,type1);"); // empty name is fine (-S
        parser.process("hehehehe hehehehe(heheheh **&heheheheheheh);");
        parser.process("long func(int *a, string s, long ***b, bool f, int e, int **q);");
        parser.process("int func(int **n, ff &b, string param1, _lon *&x, ttt *****&z);");
        parser.process("_ _tp123    (     type **var     )   ;");
    }
}
