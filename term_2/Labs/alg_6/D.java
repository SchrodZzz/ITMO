package solutions.alg_6;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class D {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private int ln;
    private int cnt;
    private int p[];
    private int pows[];
    private int ds[];
    private int dp[][];
    private DSU DSU_tree;
    private ArrayList<Integer> egs[];

    void solve(FastScanner reader) {
        int m = reader.nextInt();
        int n = ((int)10e4 << 1) + 1;
        cnt = 0;
        p = new int[n];
        ds = new int[n];
        ln = (int) (Math.log(n) / Math.log(2)) + 1;
        dp = new int[n][ln + 1];
        pows = new int[ln + 1];
        DSU_tree = new DSU(n);
        egs = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            egs[i] = new ArrayList<>();
        }
        pows[0] = 1;
        for (int i = 1; i < ln + 1; i++) {
            pows[i] = pows[i - 1] << 1;
        }
        for (int i = 0; i < m; i++) {
            switch (reader.next().charAt(0)) {
                case '+':
                    addEdge(reader.nextInt() - 1);
                    break;
                case '?':
                    reader.printStr(lca(reader.nextInt() - 1, reader.nextInt() - 1) + 1);
                    break;
                default:
                    deleteVertex(reader.nextInt() - 1);
                    break;
            }
        }
    }

    private int lca(int v, int u) {
        if (ds[v] > ds[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
        for (int i = ln - 1; i >= 0; i--) {
            if (ds[u] - ds[v] >= pows[i]) {
                u = dp[u][i];
            }
        }
        if (v == u) return v;
        for (int i = ln - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return DSU_tree.getNearLive(p[v]);
    }

    private void addEdge(int v) {
        cnt++;
        egs[v].add(cnt);
        ds[cnt] = ds[v] + 1;
        p[cnt] = v;
        dp[cnt][0] = v;
        for (int j = 1; j < ln; j++) {
            dp[cnt][j] = dp[dp[cnt][j - 1]][j - 1];
        }
    }

    private void deleteVertex(int v) {
        DSU_tree.isDs[v] = true;
        DSU_tree.nls[v] = DSU_tree.nls[p[v]];
        if (DSU_tree.isDead(p[v])) {
            DSU_tree.union(v, p[v]);
        }
        int size = egs[v].size();
        for (int i = 0; i < size; i++) {
            if (DSU_tree.isDead(egs[v].get(i))) {
                DSU_tree.union(egs[v].get(i), v);
            }
        }
    }

    private class DSU {
        int nls[];
        private int prs[];
        private int rgs[];
        private boolean isDs[];

        private DSU(int size) {
            rgs = new int[size];
            nls = new int[size];
            prs = new int[size];
            isDs = new boolean[size];

            for (int i = 0; i < size; i++) {
                rgs[i] = 0;
                prs[i] = i;
                nls[i] = i;
            }
        }

        private int get(int x) {
            if (prs[x] != x) {
                prs[x] = get(prs[x]);
            }
            return prs[x];
        }

        private void union(int x, int y) {
            int oldX = x;
            int oldY = y;
            x = get(x);
            y = get(y);
            int tmp = D.this.ds[oldX] < D.this.ds[oldY] ? nls[x] : nls[y];
            if (x == y) return;
            if (rgs[x] == rgs[y]) {
                rgs[x]++;
            }
            if (rgs[x] < rgs[y]) {
                prs[x] = y;
                nls[y] = tmp;
            } else {
                prs[y] = x;
                nls[x] = tmp;
            }
        }

        private boolean isDead(int x) {
            return isDs[x];
        }

        private int getNearLive(int x) {
            return nls[get(x)];
        }
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
