
package generated.helper;

import generated.helper.runtime.TokenData;

public class Token {
    public final CalcTokens type;
    public final TokenData data;

    public Token(final CalcTokens type, final TokenData data) {
        this.type = type;
        this.data = data;
    }
}
