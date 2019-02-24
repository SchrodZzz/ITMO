//package md2html;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class Test {
//    private final Reader reader;
//    private final Writer writer;
//
//    private static final byte END = -1;
//    private static final char STX = (char) 2;
//
//    private char curChar = STX;
//    private int headerLvl = 0;
//    private StringBuilder curTagsLine;
//
//    private ArrayList <String> buffer = new ArrayList <>();
//
//    private final Map <String, String> TAGS = Map.of(
//            "--", "<%s>",
//            "`", "<%code>",
//            "*", "<%em>",
//            "**", "<%strong>",
//            "_", "<%em>",
//            "__", "<%strong>"
//    );
//
//    private final Map <Character, String> CODE = Map.of(
//            '&', "&amp",
//            '<', "&lt",
//            '>', "rt"
//    );
//
//    public Md2Html(String inFileName, String outFileName) throws Md2HtmlException {
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(
//                            new FileInputStream(inFileName),
//                            StandardCharsets.UTF_8));
//        } catch (final IOException e) {
//            throw error("Error opening input file '%s': %s", inFileName, e.getMessage());
//        }
//        try {
//            writer = new BufferedWriter(
//                    new OutputStreamWriter(
//                            new FileOutputStream(outFileName),
//                            StandardCharsets.UTF_8));
//        } catch (final IOException e) {
//            throw error("Error creating output file '%s': %s", outFileName, e.getMessage());
//        }
//    }
//
//    private void parse() throws Md2HtmlException {
//        while ((byte) curChar != END) {
//            curTagsLine = new StringBuilder();
//            while (curChar == '\n' || curChar == STX) {
//                nextChar();
//            }
//            headerLvl = 0;
//            if (curChar == '#') {
//                while (curChar == '#') {
//                    nextChar();
//                    headerLvl++;
//                }
//                if (curChar == ' ') {
//                    addHeader();
//                } else {
//                    addParagraph();
//                }
//            } else {
//                addParagraph();
//            }
//            buffer.add(curTagsLine.append('\n').toString());
//            System.out.print(curTagsLine.append('\n').toString());
//        }
//        writeText();
//    }
//
//    private void recursiveParse(String prvTag) throws Md2HtmlException {
//        int counter = 0;
//        while (!TAGS.containsKey(String.valueOf(curChar)) && (byte) curChar != END) {
//            counter = 0;
//            do  {
//                nextChar();
//                counter++;
//            } while (curChar == '\n');
//        }
//        char prvChar = curChar;
//        nextChar();
//        if (counter >= 2 || (byte)curChar == END || String.valueOf(curChar).equals(prvTag)
//                || String.format("%c%c", curChar, prvChar).equals(prvTag)) {
//            clearLastSymbols(counter);
//            return;
//        }
//        else if (prvChar == curChar) {
//            recursiveParseCall(String.format("%c%c", curChar, curChar));
//        } else {
//            recursiveParseCall(String.valueOf(prvChar));
//        }
//    }
//
//    private void recursiveParseCall(String tagIdentifier) throws Md2HtmlException {
//        String tag = TAGS.get(tagIdentifier);
//        clearLastSymbols(tagIdentifier.length());
//        addTag(tag.replaceFirst("%", ""));
//        recursiveParse(tag);
//        clearLastSymbols(tagIdentifier.length());
//        addTag(tag.replaceFirst("%", "/"));
//    }
//
//    private void addTag(String tag) {
//        curTagsLine.append(tag);
//    }
//
//    private void addHeader() throws Md2HtmlException{
//        clearLastSymbols(headerLvl);
//        addTag(String.format("<h%d>", headerLvl));
//        recursiveParse("");
//        addTag(String.format("</h%d>", headerLvl));
//    }
//
//    private void addParagraph() throws Md2HtmlException {
//        curTagsLine.insert(0,"<p>");
//        recursiveParse("");
//        addTag("</p>");
//    }
//
//    private void clearLastSymbols(int amountOfChars) {
//        curTagsLine.delete(curTagsLine.length() - amountOfChars, curTagsLine.length());
//    }
//
//    private void nextChar() throws Md2HtmlException {
//        try {
//            curChar = (char) reader.read();
//            if (CODE.containsKey(curChar)) {
//                curTagsLine.append(CODE.get(curChar));
//            } else {
//                curTagsLine.append(curChar);
//            }
//        } catch (IOException e) {
//            throw error("Source read error: ", e.getMessage());
//        }
//    }
//
//    private void writeText() throws Md2HtmlException {
//        try {
//            for (String curLine : buffer) {
//                writer.write(curLine);
//            }
//        } catch (IOException e) {
//            throw error("Write error: ", e.getMessage());
//        }
//    }
//
//    public Md2HtmlException error(final String format, final Object... args) throws Md2HtmlException {
//        return new Md2HtmlException(String.format(format, args));
//    }
//
//    public static void main(String[] args) throws Md2HtmlException {
//        Md2Html converter = new Md2Html(args[0], args[1]);
//        converter.parse();
//    }
//}
