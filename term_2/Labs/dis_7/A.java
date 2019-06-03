package solutions.dis_6;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;

public class A {
    final private String fileName = "automaton";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int start = reader.next().charAt(0) - 65;
        Node[] nfa = new Node[27];
        for (int i = 0; i < 27; i++) {
            nfa[i] = new Node((char) (i + 65));
        }
        for (int i = 0; i < n; i++) {
            int nonterminal = reader.next().charAt(0) - 65;
            reader.next();
            String rule = reader.next();
            char symbol = rule.charAt(0);
            if (rule.length() == 2) {
                if (!nfa[nonterminal].trs.containsKey(symbol)) {
                    HashSet<Node> temp = new HashSet<>();
                    temp.add(nfa[rule.charAt(1) - 65]);
                    nfa[nonterminal].trs.put(symbol, temp);
                } else {
                    nfa[nonterminal].trs.get(symbol).add(nfa[rule.charAt(1) - 65]);
                }
            } else {
                if (!nfa[nonterminal].trs.containsKey(symbol)) {
                    HashSet<Node> temp = new HashSet<>();
                    temp.add(nfa[26]);
                    nfa[nonterminal].trs.put(symbol, temp);
                } else {
                    nfa[nonterminal].trs.get(symbol).add(nfa[26]);
                }
            }
        }
        int m = reader.nextInt();
        for (int i = 0; i < m; i++) {
            char[] word = reader.nextLine().toCharArray();
            if (check(word, nfa, start)) {
                reader.printStr("yes");
            } else {
                reader.printStr("no");
            }
        }
    }

    private static boolean check(char[] word, Node[] nfa, int start) {
        HashSet<Node> curStates = new HashSet<>();
        curStates.add(nfa[start]);
        for (char curLetter : word) {
            HashSet<Node> newStates = new HashSet<>();
            for (Node curState : curStates) {
                HashSet<Node> to = curState.trs.get(curLetter);
                if (to != null) {
                    newStates.addAll(to);
                }
            }
            curStates = newStates;
        }
        for (Node finalState : curStates) {
            if (finalState.a == (char) 91) {
                return true;
            }
        }
        return false;
    }

    class Node {
        char a;
        HashMap<Character, HashSet<Node>> trs = new HashMap<>();

        Node(char a) {
            this.a = a;
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new A()).solution();
    }

    void solution() throws IOException {
        try (FastScanner reader = new FastScanner()) {
            solve(reader);
        }
    }

    class FastScanner implements AutoCloseable {
        final BufferedReader br;
        final PrintWriter pw;
        StringTokenizer st;

        public FastScanner() throws IOException {
            br = new BufferedReader(isSysRead
                    ? new InputStreamReader(System.in)
                    : new FileReader((isTest ? "test" : fileName) + ".in"));
            pw = isSysRead ? null : new PrintWriter((isTest ? "test" : fileName) + ".out");
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        void printStr(Object str) {
            if (isSysRead) {
                System.out.println(String.valueOf(str));
            } else {
                pw.println(String.valueOf(str));
            }
        }

        public void close() throws IOException {
            if (!isSysRead) {
                pw.close();
            }
            br.close();
        }
    }
}
