import java.io.*;
import java.util.*;

public class C {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private TreeSet<Integer> ans = new TreeSet<>();
    private ArrayList<ArrayList<Integer>> adjacency = new ArrayList<>();

    private Colors[] colors;
    private int[] tin;
    private int[] up;

    private int time = 0;

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        colors = new Colors[n];
        tin = new int[n];
        up = new int[n];

        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjacency.get(u).add(v);
            adjacency.get(v).add(u);
        }

        Arrays.fill(colors, Colors.WHITE);
        for (int v = 0; v < n; v++) {
            if (colors[v] == Colors.WHITE) {
                dfs(v, -1);
            }
        }


        sc.printStr(ans.size());

        if (ans.size() != 0) {
            StringBuilder res = new StringBuilder();
            for (int cur : ans) {
                res.append(cur);
                res.append(' ');
            }
            sc.printStr(res.toString());
        }
    }

    void dfs(int v, int prv) {
        colors[v] = Colors.GRAY;
        tin[v] = time++;
        up[v] = tin[v];
        int cnt = 0;
        for (int u : adjacency.get(v)) {
            if (u == prv) {
                continue;
            }
            if (colors[u] == Colors.WHITE) {
                dfs(u, v);
                cnt++;
                up[v] = Math.min(up[v], up[u]);
                if (prv != -1 && up[u] >= tin[v]) {
                    ans.add(v + 1);
                }
            } else {
                up[v] = Math.min(up[v], tin[u]);
            }
        }
        if (prv == -1 && cnt >= 2) {
            ans.add(v + 1);
        }
    }

    private enum Colors {
        WHITE,
        GRAY,
        BLACK
    }

    private class Edge implements Comparable<Edge> {
        int u;
        int v;

        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public int compareTo(Edge e) {
            int dif = this.u - e.u;
            return dif == 0 ? this.v - e.v : dif;
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

    private String arrayList2String(ArrayList<Integer> arr) {
        StringBuilder res = new StringBuilder();
        for (int cur : arr) {
            res.append(cur);
            res.append(' ');
        }
        return res.toString();
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
