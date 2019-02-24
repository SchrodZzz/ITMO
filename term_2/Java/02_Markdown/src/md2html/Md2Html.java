package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Md2Html {

    final private Reader reader;
    final private Writer writer;

    private static final int END = -1;

    private String lineSep = System.lineSeparator();
    private TreeSet <String> openedTags = new TreeSet <>();

    private int curCharValue;
    private int prvCharValue = ' ';

    private boolean isNewTextUnit;
    private boolean isStartOfText;

    private static Map <String, String> TAGS = Map.of(
            "*", "em",
            "**", "strong",
            "_", "em",
            "__", "strong",
            "--", "s",
            "`", "code",
            "++", "u",
            "~", "mark"
    );

    private final Map <String, String> CODES = Map.of(
            "&", "&amp;",
            "<", "&lt;",
            ">", "&gt;"
    );

    public Md2Html(final String inFileName, final String outFileName) throws Md2HtmlException {
        try {
            reader = new BufferedReader(new FileReader(inFileName, StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw error("Error opening input file '%s': %s", inFileName, e.getMessage());
        }
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(outFileName),
                            StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw error("Error creating output file '%s': %s", outFileName, e.getMessage());
        }
    }

    public void parse() throws Md2HtmlException {
        boolean tagIsCode = false;
        isNewTextUnit = true;
        isStartOfText = true;
        skipEmptyLines();
        while (!isEndOfFile()) {
            if (isNewTextUnit) {
                if ((char) curCharValue == '#') {
                    StringBuilder header = new StringBuilder();
                    while ((char) curCharValue == '#') {
                        header.append((char) curCharValue);
                        nextChar();
                    }
                    if (Character.isWhitespace(curCharValue) && header.length() < 7) {
                        unitStart("h" + header.length());
                    } else {
                        unitStart("p");
                        writeData(header.toString());
                        continue;
                    }
                } else {
                    unitStart("p");
                    continue;
                }
            } else {
                if (isTag((char) curCharValue)) {
                    int temp = curCharValue;
                    nextChar();
                    StringBuilder tagToAdd = new StringBuilder();
                    if ((temp == '-' || temp == '+') && temp != curCharValue) {
                        writeData(Character.toString((char) temp));
                    } else {
                        tagToAdd.append((char) temp);
                        if (temp == curCharValue) {
                            tagToAdd.append((char) curCharValue);
                            nextChar();
                        }
                        if (tagToAdd.toString().equals("`")) {
                            tagIsCode = !tagIsCode;
                        }
                        if (Character.isWhitespace(prvCharValue) && Character.isWhitespace(curCharValue)) {
                            writeData(tagToAdd.toString());
                        } else {
                            writeTag(tagToAdd);
                        }
                    }
                    continue;
                } else if (isLineSep((char) prvCharValue, (char) curCharValue)) {
                    prvCharValue = curCharValue;
                    nextChar();
                    if (isEndOfFile()) {
                        break;
                    }
                    if (isLineSep((char) prvCharValue, (char) curCharValue)) {
                        skipEmptyLines();
                        unitEnd();
                        openedTags = new TreeSet <>();
                        continue;
                    } else {
                        writeData(lineSep);
                        continue;
                    }
                } else {
                    if ((char) curCharValue == '\\') {
                        nextChar();
                        if (isEndOfFile()) {
                            break;
                        }
                    }
                    writeData(CODES.getOrDefault(Character.toString((char) curCharValue),
                            Character.toString((char) curCharValue)));
                }
            }
            isStartOfText = false;
            prvCharValue = curCharValue;
            nextChar();
        }
        unitEnd();
        streamsClose();
    }

    private boolean isTag(final char ch) {
        return "`*+~-_".contains(Character.toString(ch));
    }

    private String createTag(final String element, final boolean isOpeningTag) {
        return (isOpeningTag ? "<" : "</") + TAGS.getOrDefault(element, element) + ">";
    }

    private void unitStart(final String tag) throws Md2HtmlException {
        isNewTextUnit = false;
        writeData(createTag(tag, true));
        openedTags.add(tag);
    }

    private void unitEnd() throws Md2HtmlException {
        if (openedTags.contains("p") || !openedTags.isEmpty() && openedTags.last().charAt(0) == 'h') {
            writeData(createTag(openedTags.pollLast(), false));
            writeData(lineSep);
            isNewTextUnit = true;
        }
    }

    private boolean isLineSep(final char curCh, final char nextCh) {
        return lineSep.length() == 1 && nextCh == lineSep.charAt(0) ||
                lineSep.length() == 2 && String.format("%c%c", curCh, nextCh).equals(lineSep);
    }

    private boolean isLineSepSymbol(final char ch) {
        return String.valueOf(ch).equals(lineSep);
    }

    private boolean isEndOfFile() {
        return curCharValue == END;
    }

    private void writeTag(final StringBuilder tagToAdd) throws Md2HtmlException {
        String element = tagToAdd.toString();
        if (openedTags.contains(element)) {
            writeData(createTag(element, false));
            openedTags.remove(element);
        } else {
            writeData(createTag(element, true));
            openedTags.add(element);
        }
    }

    private void skipEmptyLines() throws Md2HtmlException {
        nextChar();
        while (isLineSepSymbol((char) curCharValue)) {
            nextChar();
        }
        if (!isStartOfText) {
            unitEnd();
        }
    }

    private void nextChar() throws Md2HtmlException {
        try {
            curCharValue = reader.read();
        } catch (IOException e) {
            throw error("Source read error: %s", e.getMessage());
        }
    }

    private void writeData(final String data) throws Md2HtmlException {
        try {
            writer.write(data);
        } catch (IOException e) {
            throw error("Data write error: %s", e.getMessage());
        }
    }

    private void streamsClose() throws Md2HtmlException {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            throw error("Stream close error: %s", e.getMessage());
        }
    }

    private Md2HtmlException error(final String format, final Object... args) throws Md2HtmlException {
        return new Md2HtmlException(String.format(format, args));
    }

    public static void main(String[] args) throws Md2HtmlException {
        if (args.length != 2) {
            System.out.printf("Wrong number of arguments: %d", args.length);
        } else {
            new Md2Html(args[0], args[1]).parse();
        }
    }
}
