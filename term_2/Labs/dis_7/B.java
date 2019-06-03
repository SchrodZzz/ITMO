package solutions.dis_6;

import java.io.*;
import java.util.*;

public class B {
    final private String fileName = "epsilon";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        HashMap<Character, HashSet<String>> rules = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String[] str = reader.nextLine().split(" -> ");
            char nonT = str[0].charAt(0);
            String rule;
            if (str.length == 2) {
                rule = str[1];
            } else {
                rule = "";
            }
            if (rules.containsKey(nonT)) {
                rules.get(nonT).add(rule);
            } else {
                HashSet<String> temp = new HashSet<>();
                temp.add(rule);
                rules.put(nonT, temp);
            }
        }
        HashSet<Character> eps = new HashSet<>();
        while (true) {
            HashSet<Character> curEpsilon = new HashSet<>();
            for (char nonterminal : rules.keySet()) {
                HashSet<String> curRules = rules.get(nonterminal);
                for (String rule : curRules) {
                    boolean toEpsilon = true;
                    if (!rule.equals("")) {
                        for (char letter : rule.toCharArray()) {
                            if (!eps.contains(letter)) {
                                toEpsilon = false;
                                break;
                            }
                        }
                    }
                    if (toEpsilon) {
                        curEpsilon.add(nonterminal);
                    }
                }
            }
            if (eps.equals(curEpsilon)) {
                break;
            } else {
                eps.addAll(curEpsilon);
            }
        }
        TreeSet<Character> answer = new TreeSet<>();
        answer.addAll(eps);
        for (char epsNonT : answer) {
            reader.printStr(epsNonT);
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new B()).solution();
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
