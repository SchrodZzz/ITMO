import java.io.*;
import java.util.*;

public class B {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private ArrayList<ArrayList<Edge>> adjacency = new ArrayList<>();
    private PriorityQueue<Edge> queue = new PriorityQueue<>();

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int w = sc.nextInt();
            adjacency.get(u).add(new Edge(v, w));
            adjacency.get(v).add(new Edge(u, w));
        }

        final int inf = 1000000000;
        int[] dp = new int[n];
        for (int i = 0; i < n; i ++) {
            dp[i] = inf;
        }
        dp[0] = 0;
        queue.add(new Edge(0, 0));

        while (!queue.isEmpty()) {
            int u = queue.poll().v;
            for (Edge v : adjacency.get(u)) {
                if (dp[u] + v.v < dp[v.u]) {
                    dp[v.u] = dp[u] + v.v;
                    queue.add(new Edge(dp[v.u], v.u));
                }
            }
        }

        sc.printStr(array2String(dp));
    }

    private int roundVertex(int v) {
        return v % 2 == 0 ? v + 1 : v - 1;
    }

    private enum Colors {
        WHITE,
        GRAY,
        BLACK
    }

    private class Edge implements Comparable<Edge> {
        Integer u;
        Integer v;

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

    private int bool2Int(boolean val) {
        return val ? 1 : 0;
    }

    private <T> String arrayList2String(final ArrayList<T> arr, final char separator) {
        StringBuilder res = new StringBuilder();
        for (T cur : arr) {
            res.append(cur);
            res.append(separator);
        }
        return res.toString();
    }

    private String array2String(final int[] arr) {
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
