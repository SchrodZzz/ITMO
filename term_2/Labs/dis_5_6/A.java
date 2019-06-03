package solutions.dis_5;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class A {
    final private String fileName = "problem1";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        String input = reader.nextLine();
        int n = reader.nextInt();
        int m = reader.nextInt();
        int k = reader.nextInt();
        boolean[] tStates = new boolean[n + 1];
        int[][] automata = new int[n + 1][26];
        for (int i = 0; i < k; i++) {
            tStates[reader.nextInt()] = true;
        }
        for (int i = 0; i < m; i++) {
            int from = reader.nextInt();
            int to = reader.nextInt();
            char symbol = reader.next().charAt(0);
            automata[from][symbol - 97] = to;
        }
        char[] chs = input.toCharArray();
        int cur = 1;
        for (char ch : chs) {
            if (automata[cur][ch - 97] != 0) {
                cur = automata[cur][ch - 97];
            } else {
                reader.printStr("Rejects");
                return;
            }
        }
        if (tStates[cur]) {
            reader.printStr("Accepts");
        } else {
            reader.printStr("Rejects");
        }
    }

    private class Node {
        boolean end;
        Node a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;

        public Node(boolean end) {
            this.end = end;
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