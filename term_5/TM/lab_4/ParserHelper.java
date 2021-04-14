import source.*;

public class ParserHelper {

    final static String DIR = "term_4/TM/lab_4/";

    public static void main(String[] args) {
        System.out.println("Generation started");
        ParserGenerator generator = new ParserGenerator();
        (new ParserGenerator()).generate(DIR + "parsers/BasicCalculator.ggf", DIR);
        (new ParserGenerator()).generate(DIR + "parsers/ThirdLab.ggf", DIR);
        System.out.println("Generation complete");
    }
}
