
package generated.helper;

import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.io.IOException;
import generated.helper.runtime.TokenData;
import generated.helper.runtime.LexingException;
import java.nio.file.Path;

public class CalcLexer {
    private final static Token _END_ = new Token(CalcTokens._END, new TokenData("_END", ""));
    private final Pattern ignore;
    private final Matcher matcher;
    private String text;
    private final Map<CalcTokens, Pattern> tokens;

    public CalcLexer(final Path input) throws IOException {
        this.ignore = Pattern.compile("([ \t]+)|([\n\r]+)");
        this.matcher = Pattern.compile("").matcher("");
        this.text = Files.newBufferedReader(input).lines().collect(Collectors.joining());
        this.tokens = new HashMap<>();
        this.tokens.put(CalcTokens.NUMBER, Pattern.compile("[1-9][0-9]*"));
        this.tokens.put(CalcTokens.PLUS, Pattern.compile("[+]"));
        this.tokens.put(CalcTokens.MINUS, Pattern.compile("[-]"));
        this.tokens.put(CalcTokens.MULT, Pattern.compile("[*]"));
        this.tokens.put(CalcTokens.POW, Pattern.compile("[\\^]"));
        this.tokens.put(CalcTokens.LP, Pattern.compile("[(]"));
        this.tokens.put(CalcTokens.RP, Pattern.compile("[)]"));
    }

    public CalcLexer(final String input) {
        this.ignore = Pattern.compile("([ \t]+)|([\n\r]+)");
        this.matcher = Pattern.compile("").matcher("");
        this.text = input;
        this.tokens = new HashMap<>();
        this.tokens.put(CalcTokens.NUMBER, Pattern.compile("[1-9][0-9]*"));
        this.tokens.put(CalcTokens.PLUS, Pattern.compile("[+]"));
        this.tokens.put(CalcTokens.MINUS, Pattern.compile("[-]"));
        this.tokens.put(CalcTokens.MULT, Pattern.compile("[*]"));
        this.tokens.put(CalcTokens.POW, Pattern.compile("[\\^]"));
        this.tokens.put(CalcTokens.LP, Pattern.compile("[(]"));
        this.tokens.put(CalcTokens.RP, Pattern.compile("[)]"));
    }

    public Token getNext() throws LexingException {
        matcher.usePattern(ignore);
        matcher.reset(text);
        while (matcher.lookingAt()) {
            text = text.substring(matcher.end());
            matcher.reset(text);
        }
        final String curText = text;
        boolean matched = Arrays.stream(CalcTokens.values()).filter(type -> (type != CalcTokens._END)).map(tokens::get).anyMatch(regex -> {
            matcher.usePattern(regex);
            matcher.reset(curText);
            return matcher.lookingAt();
        });
        if (!matched) {
            if (text.isEmpty()) {
                return _END_;
            } else {
                throw new LexingException("Unmatched data in input: \"" + text + "\"");
            }
        } else {
            for (final CalcTokens type : CalcTokens.values()) {
                final Pattern regex = tokens.get(type);
                matcher.usePattern(regex);
                matcher.reset(text);
                if (matcher.lookingAt()) {
                    final String curMatch = text.substring(0, matcher.end());
                    text = text.substring(curMatch.length());
                    return new Token(type, new TokenData(type.name(), curMatch));
                }
            }
        }
        return null;
    }
}
