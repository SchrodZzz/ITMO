package solutions.alg_6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class B {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private int ln;
    private int[][] up;
    private int[] tin;
    private int[] tout;
    private int time;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        List<Integer>[] tree = new ArrayList[n + 1];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            int current = reader.nextInt() - 1;
            tree[current].add(i);
            tree[i].add(current);
        }
        n = tree.length;
        ln = 1;
        while ((1 << ln) <= n) ++ln;
        up = new int[ln][n];
        tin = new int[n];
        tout = new int[n];
        dfs(tree, 0, 0);
        int m = reader.nextInt();
        for (int i = 0; i < m; i++) {
            reader.printStr(lca(reader.nextInt() - 1, reader.nextInt() - 1) + 1);
        }
    }

    void dfs(List<Integer>[] tree, int u, int p) {
        tin[u] = time++;
        up[0][u] = p;
        for (int i = 1; i < ln; i++)
            up[i][u] = up[i - 1][up[i - 1][u]];
        for (int v : tree[u])
            if (v != p)
                dfs(tree, v, u);
        tout[u] = time++;
    }

    int lca(int a, int b) {
        if (isParent(a, b) || isParent(b, a))
            return isParent(a, b) ? a : b;
        for (int i = ln - 1; i >= 0; i--)
            if (!isParent(up[i][a], b))
                a = up[i][a];
        return up[0][a];
    }

    boolean isParent(int parent, int child) {
        return tin[parent] <= tin[child] && tout[child] <= tout[parent];
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
