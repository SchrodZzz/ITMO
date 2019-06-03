//package solutions.alg_6;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class C {
    final private String fileName = "minonpath";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    private int ln;
    private int ds[];
    private int pows[];
    private int up[][];
    private int dpW[][];
    private ArrayList<Integer> egs[];

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int[] p = new int[n];
        ln = (int) (Math.log(n) / Math.log(2)) + 1;
        up = new int[n][ln + 1];
        pows = new int[ln + 1];
        dpW = new int[n][ln + 1];
        egs = new ArrayList[n];
        ds = new int[n];
        for (int i = 0; i < n; i++) {
            egs[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            int v = reader.nextInt() - 1;
            p[i] = v;
            dpW[i][0] = reader.nextInt();
            egs[v].add(i);
        }
        dfs(0, 0);
        pows[0] = 1;
        for (int i = 1; i <= ln; i++) {
            pows[i] = pows[i - 1] << 1;
        }
        for (int i = 0; i < n; i++) {
            up[i][0] = p[i];
        }
        for (int j = 1; j < ln; j++) {
            for (int i = 0; i < n; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
            for (int i = 0; i < n; i++) {
                dpW[i][j] = Math.min(dpW[i][j - 1], dpW[up[i][j - 1]][j - 1]);
            }
        }
        int m = reader.nextInt();
        for (int i = 0; i < m; i++) {
            reader.printStr(getAns(reader.nextInt() - 1, reader.nextInt() - 1));
        }
    }

    private void dfs(int v, int d) {
        ds[v] = d;
        int size = egs[v].size();
        for (int i = 0; i < size; i++) {
            dfs(egs[v].get(i), d + 1);
        }
    }

    private int getAns(int v, int u) {
        int min = Integer.MAX_VALUE;
        if (ds[u] < ds[v]) {
            u ^= v ^= u;
            v ^= u;
        }
        for (int i = ln - 1; i >= 0; i--) {
            if (ds[u] - ds[v] >= pows[i]) {
                min = Math.min(dpW[u][i], min);
                u = up[u][i];
            }
        }
        if (v == u) return min;
        for (int i = ln - 1; i >= 0; i--) {
            if (up[v][i] != up[u][i]) {
                min = Integer.min(dpW[u][i], min);
                min = Integer.min(dpW[v][i], min);
                v = up[v][i];
                u = up[u][i];
            }
        }
        return Integer.min(Integer.min(dpW[v][0], min), Integer.min(dpW[u][0], min));
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
