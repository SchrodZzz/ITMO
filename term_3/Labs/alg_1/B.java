import java.io.*;
import java.util.*;

public class B {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private ArrayList<Integer> ans = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> adjacency = new ArrayList<>();
    private TreeMap<Edge, Integer> helper = new TreeMap<>();

    private Colors[] colors;
    private int[] tin;
    private int[] up;

    private int time = 0;
    private int bridgeCnt = 0;

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
            helper.put(new Edge(u, v), i + 1);
            helper.put(new Edge(v, u), i + 1);
        }

        Arrays.fill(colors, Colors.WHITE);
        for (int v = 0; v < n; v++) {
            if (colors[v] == Colors.WHITE) {
                dfs(v, -1);
            }
        }

        sc.printStr(bridgeCnt);

        Collections.sort(ans);
        sc.printStr(arrayList2String(ans));
    }

    void dfs(int v, int prv) {
        colors[v] = Colors.GRAY;
        tin[v] = time++;
        up[v] = tin[v];
        for (int u : adjacency.get(v)) {
            if (u == prv) {
                continue;
            }
            if (colors[u] == Colors.WHITE) {
                dfs(u, v);
                up[v] = Math.min(up[v], up[u]);
                if (up[u] > tin[v]) {
                    bridgeCnt++;
                    ans.add(helper.get(new Edge(v, u)));
                }
            } else {
                up[v] = Math.min(up[v], tin[u]);
            }
        }
        colors[v] = Colors.BLACK;
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
        (new B()).solution();
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
