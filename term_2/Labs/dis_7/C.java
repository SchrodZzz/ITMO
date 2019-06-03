package solutions.dis_6;

import java.io.*;
import java.util.*;

public class C {
    final private String fileName = "useless";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        char start = reader.next().charAt(0);
        HashMap<Character, HashSet<String>> rules = new HashMap<>();
        TreeSet<Character> nonTs = new TreeSet<>();
        nonTs.add(start);
        for (int i = 0; i < n; i++) {
            String[] str = reader.nextLine().split(" -> ");
            char nonT = str[0].charAt(0);
            nonTs.add(nonT);
            String rule;
            if (str.length == 2) {
                rule = str[1];
            } else {
                rule = "";
            }
            for (char letter : rule.toCharArray()) {
                if (Character.isUpperCase(letter)) {
                    nonTs.add(letter);
                }
            }
            if (rules.containsKey(nonT)) {
                rules.get(nonT).add(rule);
            } else {
                HashSet<String> temp = new HashSet<>();
                temp.add(rule);
                rules.put(nonT, temp);
            }
        }
        HashSet<Character> genNonT = new HashSet<>();
        HashSet<String> genRules = new HashSet<>();
        while (true) {
            HashSet<Character> curGen = new HashSet<>();
            for (char nonT : rules.keySet()) {
                HashSet<String> curRules = rules.get(nonT);
                for (String rule : curRules) {
                    boolean isGenerating = true;
                    if (!rule.equals("")) {
                        for (char letter : rule.toCharArray()) {
                            if (Character.isUpperCase(letter) && !genNonT.contains(letter)) {
                                isGenerating = false;
                                break;
                            }
                        }
                    }
                    if (isGenerating) {
                        curGen.add(nonT);
                        genRules.add(rule);
                    }
                }
            }
            if (genNonT.equals(curGen)) {
                break;
            } else {
                genNonT.addAll(curGen);
            }
        }
        HashSet<Character> clo = new HashSet<>();
        dfs(rules, clo, start, genNonT, genRules);
        StringBuilder buf = new StringBuilder();
        for (char nonT : nonTs) {
            if (!genNonT.contains(nonT) || !clo.contains(nonT)) {
                buf.append(nonT).append(" ");
            }
        }
        reader.printStr(buf);
    }

    void dfs(HashMap<Character, HashSet<String>> rules, HashSet<Character> reachable,
                            char nonT, HashSet<Character> genNonT, HashSet<String> genRules) {
        reachable.add(nonT);
        if (genNonT.contains(nonT)) {
            HashSet<String> curRules = rules.get(nonT);
            if (curRules != null) {
                for (String rule : curRules) {
                    if (genRules.contains(rule)) {
                        for (char letter : rule.toCharArray()) {
                            if (Character.isUpperCase(letter) && !reachable.contains(letter)) {
                                dfs(rules, reachable, letter, genNonT, genRules);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new C()).solution();
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
