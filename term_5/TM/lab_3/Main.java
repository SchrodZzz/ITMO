import gen.MyGrammarLexer;
import gen.MyGrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.Map;

public class Main {

    // MARK: Private properties

    private static final String inFileName = "term_5/TM/lab_3/data/in";




    // MARK: Lifecycle

    public static void main(String[] args) throws IOException {
        MyGrammarLexer lexer = new MyGrammarLexer(CharStreams.fromFileName(inFileName));
        MyGrammarParser parser = new MyGrammarParser(new CommonTokenStream(lexer));
        parser.main();
        System.out.println();
        for (Map.Entry<String, Integer> pair : parser.assignments) {
            System.out.print(String.format("%s = %d\n", pair.getKey(), pair.getValue()));
        }
    }
}
