package generated.helper.runtime;

import java.util.regex.Pattern;

public class TokenData {
    private final String name;
    private final String text;

    public TokenData(final String name, final String text) {
        this.name = name;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
