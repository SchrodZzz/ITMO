
package generated.helper;


import generated.helper.runtime.TokenData;
import generated.helper.runtime.LexingException;
import generated.helper.runtime.ParsingException;

public class CalcParser {
    private final CalcLexer lexer;
    private Token curToken;

    public CalcParser(final CalcLexer lexer) {
        this.lexer = lexer;
        this.curToken = null;
    }

    public Integer mainRule() throws ParsingException, LexingException {
        curToken = lexer.getNext();
        final Integer res = parseS();
        if (curToken.type != CalcTokens._END) {
            throw new ParsingException("Expected end of input but found " + curToken.data.getName());
        }
        return res;
    }

    private Integer parseS() throws ParsingException, LexingException {
        Integer res = null;
        switch (curToken.type) {
            case NUMBER:
            case LP:
            case MINUS: {
                
                Integer head = parseT();
                
                Integer tail = parseX(head);
                res = tail;
                break;
            }
            default: {
                throw new ParsingException("Expected NUMBER, LP, MINUS but found " + curToken.type.name());
            }
        }
        return res;
    }

    private Integer parseX(Integer acc) throws ParsingException, LexingException {
        Integer val = null;
        switch (curToken.type) {
            case PLUS: {
                
                if (curToken.type != CalcTokens.PLUS) {
                    throw new LexingException("Expected PLUS but found " + curToken.type.name());
                }
                TokenData p = curToken.data;
                
                curToken = lexer.getNext();
                Integer head = parseT();
                Integer nextAcc = acc + head;
                Integer tail = parseX(nextAcc);
                val = tail;
                break;
            }
            case MINUS: {
                
                if (curToken.type != CalcTokens.MINUS) {
                    throw new LexingException("Expected MINUS but found " + curToken.type.name());
                }
                TokenData m = curToken.data;
                
                curToken = lexer.getNext();
                Integer head = parseT();
                Integer nextAcc = acc - head;
                Integer tail = parseX(nextAcc);
                val = tail;
                break;
            }
            case _END:
            case RP: {
                
                val = acc;
                break;
            }
            default: {
                throw new ParsingException("Expected PLUS, MINUS, _END, RP but found " + curToken.type.name());
            }
        }
        return val;
    }

    private Integer parseT() throws ParsingException, LexingException {
        Integer val = null;
        switch (curToken.type) {
            case NUMBER:
            case LP:
            case MINUS: {
                
                Integer head = parseF();
                
                Integer tail = parseY(head);
                val = tail;
                break;
            }
            default: {
                throw new ParsingException("Expected NUMBER, LP, MINUS but found " + curToken.type.name());
            }
        }
        return val;
    }

    private Integer parseY(Integer acc) throws ParsingException, LexingException {
        Integer val = null;
        switch (curToken.type) {
            case MULT: {
                
                if (curToken.type != CalcTokens.MULT) {
                    throw new LexingException("Expected MULT but found " + curToken.type.name());
                }
                TokenData m = curToken.data;
                
                curToken = lexer.getNext();
                Integer head = parseF();
                Integer nextAcc = acc * head;
                Integer tail = parseY(nextAcc);
                val = tail;
                break;
            }
            case _END:
            case RP:
            case PLUS:
            case MINUS: {
                
                val = acc;
                break;
            }
            default: {
                throw new ParsingException("Expected MULT, _END, RP, PLUS, MINUS but found " + curToken.type.name());
            }
        }
        return val;
    }

    private Integer parseF() throws ParsingException, LexingException {
        Integer val = null;
        switch (curToken.type) {
            case NUMBER:
            case LP:
            case MINUS: {
                
                Integer head = parseW();
                
                Integer tail = parseQ();
                val = (int) Math.pow(head.doubleValue(), tail.doubleValue());
                break;
            }
            default: {
                throw new ParsingException("Expected NUMBER, LP, MINUS but found " + curToken.type.name());
            }
        }
        return val;
    }

    private Integer parseQ() throws ParsingException, LexingException {
        Integer val = null;
        switch (curToken.type) {
            case POW: {
                
                if (curToken.type != CalcTokens.POW) {
                    throw new LexingException("Expected POW but found " + curToken.type.name());
                }
                TokenData p = curToken.data;
                
                curToken = lexer.getNext();
                Integer head = parseW();
                
                Integer tail = parseQ();
                val = (int) Math.pow(head.doubleValue(), tail.doubleValue());
                break;
            }
            case _END:
            case MULT:
            case RP:
            case PLUS:
            case MINUS: {
                
                val = 1;
                break;
            }
            default: {
                throw new ParsingException("Expected POW, _END, MULT, RP, PLUS, MINUS but found " + curToken.type.name());
            }
        }
        return val;
    }

    private Integer parseW() throws ParsingException, LexingException {
        Integer val = null;
        switch (curToken.type) {
            case MINUS: {
                
                if (curToken.type != CalcTokens.MINUS) {
                    throw new LexingException("Expected MINUS but found " + curToken.type.name());
                }
                TokenData m = curToken.data;
                
                curToken = lexer.getNext();
                Integer tail = parseW();
                val = -1 * tail;
                break;
            }
            case NUMBER: {
                
                if (curToken.type != CalcTokens.NUMBER) {
                    throw new LexingException("Expected NUMBER but found " + curToken.type.name());
                }
                TokenData num = curToken.data;
                val = new Integer(num.getText());
                curToken = lexer.getNext();
                break;
            }
            case LP: {
                
                if (curToken.type != CalcTokens.LP) {
                    throw new LexingException("Expected LP but found " + curToken.type.name());
                }
                TokenData l = curToken.data;
                
                curToken = lexer.getNext();
                Integer mid = parseS();
                
                if (curToken.type != CalcTokens.RP) {
                    throw new LexingException("Expected RP but found " + curToken.type.name());
                }
                TokenData r = curToken.data;
                val = mid;
                curToken = lexer.getNext();
                break;
            }
            default: {
                throw new ParsingException("Expected MINUS, NUMBER, LP but found " + curToken.type.name());
            }
        }
        return val;
    }

}
