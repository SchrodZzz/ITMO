package solutions.dis_6;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class D {
    final private String fileName = "nfc";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    final int MOD = (int)10e8 + 7;

    int[][][] dp = new int[26][][];
    ArrayList<Node>[] dataH = new ArrayList[26];

    String word;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        char s = reader.next().charAt(0);
        for (int i = 0; i < 26; i++) {
            dataH[i] = new ArrayList<>();
            dp[i] = new int[101][];
            for (int j = 0; j < 101; j++) {
                dp[i][j] = new int[101];
                for (int k = 0; k < 101; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            char a = (char)(reader.next().charAt(0) - 'A');
            reader.next();
            String b = reader.next();
            Node to = new Node();
            to.a = b.charAt(0);
            if (b.length() > 1) {
                to.b = b.charAt(1);
                to.l = false;
            } else {
                to.l = true;
            }
            dataH[a].add(to);
        }
        word = reader.next();
        reader.printStr(ans(s - 'A', 0, word.length()));
    }

    long ans(int v, int l, int r) {
        if (dp[v][l][r] == -1) {
            int res = 0;
            int size = dataH[v].size();
            for (int j = 0; j < size; j++) {
                Node u = dataH[v].get(j);
                if (u.l) {
                    if (l + 1 == r && word.charAt(l) == u.a) {
                        res++;
                        res %= MOD;
                    }
                } else {
                    for (int i = l + 1; i < r; ++i) {
                        res = (int)((ans(u.a - 'A', l, i) * ans(u.b - 'A', i, r) + res) % MOD);
                    }
                }
            }
            dp[v][l][r] = res;
        }
        return dp[v][l][r];
    }

    class Node {
        boolean l;
        char a, b;
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new D()).solution();
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
