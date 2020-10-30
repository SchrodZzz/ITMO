import java.util.ArrayList;
import java.util.Map;

public class Parser {

    // MARK: Private properties

    private final Tree empty = new Tree(Step.eps, new ArrayList<>());
    private final Map<Character, Token> map = Map.of(
            '(', Token.LPAREN,
            ')', Token.RPAREN,
            ';', Token.SEMICOLON,
            '*', Token.ASTERISK,
            '&', Token.AMPERSAND,
            ',', Token.COMMA
    );

    private ArrayList<Token> tokens = new ArrayList<>();
    private boolean isProcessed = false; // too lazy to create additional class with vizualization func
    private int curTokenIdx = 0;

    private Tree tree;

    private Character sep;


    // MARK: Lifecycle

    public Parser(Character sep) {
        this.sep = sep;
    }


    // MARK: Public

    public void process(String data) {
        resetVars();
        fillTokens(data);
        tree = S();
        isProcessed = true;
    }

    public ArrayList<String> getTreeImage() {
        if (!isProcessed) {
            expected("!!processed tree!!");
        }
        return getTreeImage(tree, 0);
    }


    // MARK: Private helpers

    private void fillTokens(String data) {
        int tmpIdx = 0;
        while (tmpIdx < data.length()) {
            if ("();*&,".contains(String.valueOf(data.charAt(tmpIdx)))) {
                tokens.add(map.get(data.charAt(tmpIdx)));
                tmpIdx += 1;
            } else if (" \r\n\t".contains(String.valueOf(data.charAt(tmpIdx)))) {
                tmpIdx += 1;
            } else {
                if (data.charAt(tmpIdx) >= '0' && data.charAt(tmpIdx) <= '9') {
                    expected("non-number at the begin");
                } else if (isOk(data.charAt(tmpIdx))) {
                    tmpIdx += 1;
                    while (tmpIdx < data.length()) {
                        if ("();*&, \r\n\t".contains(String.valueOf(data.charAt(tmpIdx)))) {
                            tokens.add(Token.NAME);
                            break;
                        } else {
                            if (!isOk(data.charAt(tmpIdx))) {
                                expected(String.format("known symbol, but got: '%c'", data.charAt(tmpIdx)));
                            } else {
                                tmpIdx += 1;
                            }
                        }
                    }
                }
            }
        }
        tokens.add(Token.END);
    }

    private ArrayList<String> getTreeImage(Tree node, int d) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(String.valueOf(sep).repeat(Math.max(0, d)) + node.step);
        for (Tree child : node.children) {
            arr.addAll(getTreeImage(child, d + 1));
        }
        return arr;
    }

    private void resetVars() {
        tokens = new ArrayList<>();
        isProcessed = false;
        curTokenIdx = 0;
    }

    private boolean isOk(Character ch) {
        return (ch >= '0' && ch <= '9') || ch == '_' || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    // MARK: Private steps

    private Tree S() {
        ArrayList<Tree> tmp = new ArrayList<>();
        if (tokens.get(curTokenIdx) == Token.NAME) {
            tmp.add(T());
            tmp.add(N());
            if (tokens.get(curTokenIdx) == Token.LPAREN) {
                curTokenIdx += 1;
            } else {
                expected("LPAREN");
            }
            tmp.add(A());
            if (tokens.get(curTokenIdx) == Token.RPAREN) {
                curTokenIdx++;
            } else {
                expected("RPAREN");
            }
            if (tokens.get(curTokenIdx) == Token.SEMICOLON) {
                curTokenIdx++;
            } else {
                expected("SEMICOLON");
            }
        } else {
            expected("TYPE");
        }
        return new Tree(Step.S, tmp);
    }

    private Tree A() {
        ArrayList<Tree> tmp = new ArrayList<>();
        if (tokens.get(curTokenIdx) == Token.NAME) {
            tmp.add(T());
            tmp.add(N());
            tmp.add(B());
        } else if (tokens.get(curTokenIdx) != Token.RPAREN) {
            expected("TYPE or RPAREN");
        }
        return new Tree(Step.A, tmp);
    }

    private Tree B() {
        ArrayList<Tree> tmp = new ArrayList<>();
        if (tokens.get(curTokenIdx) == Token.COMMA) {
            curTokenIdx += 1;
            tmp.add(T());
            tmp.add(N());
            tmp.add(B());
        } else if (tokens.get(curTokenIdx) != Token.RPAREN) {
            expected("TYPE or RPAREN");
        }
        return new Tree(Step.B, tmp);
    }

    private Tree T() {
        ArrayList<Tree> tmp = new ArrayList<>();
        if (tokens.get(curTokenIdx) == Token.NAME) {
            curTokenIdx += 1;
            tmp.add(empty);
        } else {
            expected("TYPE");
        }
        return new Tree(Step.T, tmp);
    }

    private Tree N() {
        ArrayList<Tree> tmp = new ArrayList<>();
        if (tokens.get(curTokenIdx) == Token.AMPERSAND
                || tokens.get(curTokenIdx) == Token.ASTERISK
                || tokens.get(curTokenIdx) == Token.NAME) {
            tmp.add(Z());
        } else if (tokens.get(curTokenIdx) != Token.LPAREN
                && tokens.get(curTokenIdx) != Token.RPAREN
                && tokens.get(curTokenIdx) != Token.COMMA) {
            expected("AMPERSAND, ASTERISK or NAME");
        }
        return new Tree(Step.N, tmp);
    }

    private Tree Z() {
        ArrayList<Tree> tmp = new ArrayList<>();
        if (tokens.get(curTokenIdx) == Token.ASTERISK) {
            curTokenIdx += 1;
            tmp.add(Z());
        } else if (tokens.get(curTokenIdx) == Token.AMPERSAND) {
            curTokenIdx += 1;
            tmp.add(M());
        } else if (tokens.get(curTokenIdx) == Token.NAME) {
            curTokenIdx += 1;
            tmp.add(empty);
        } else if (tokens.get(curTokenIdx) != Token.LPAREN
                && tokens.get(curTokenIdx) != Token.RPAREN
                && tokens.get(curTokenIdx) != Token.COMMA) {
            expected("NAME");
        }
        return new Tree(Step.Z, tmp);
    }

    private Tree M() {
        ArrayList<Tree> tmp = new ArrayList<>();
        if (tokens.get(curTokenIdx) == Token.NAME) {
            curTokenIdx += 1;
            tmp.add(empty);
        } else if (tokens.get(curTokenIdx) != Token.LPAREN
                && tokens.get(curTokenIdx) != Token.RPAREN
                && tokens.get(curTokenIdx) != Token.COMMA) {
            expected("NAME");
        }
        return new Tree(Step.M, tmp);
    }


    private void expected(String msg) {
        System.err.println("expected " + msg);
        System.exit(1);
    }


    // MARK: - Inner classes

    enum Step {
        S, A, B, T, N, Z, M, eps
    }

    enum Token {
        LPAREN, RPAREN, END, SEMICOLON, ASTERISK, NAME, COMMA, AMPERSAND
    }

    static class Tree {
        Step step;
        ArrayList<Tree> children;

        public Tree(Step step, ArrayList<Tree> children) {
            this.step = step;
            this.children = children;
        }
    }
}
