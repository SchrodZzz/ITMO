import java.io.*;
import java.util.*;

public class H {
    final private String fileName = "avia";
    final private boolean isTest = false;
    final private boolean isSysRead = true;

    private ArrayList<Integer> order = new ArrayList<>();

    private Colors[] visitColors;
    private int[][] graphT;
    private int[][] graph;

    private int compCnt = 0;
    private int n;

    void solve(FastScanner sc) {
        n = sc.nextInt();

        long l = 0;
        long r = 0;

        visitColors = new Colors[n];
        graphT = new int[n][n];
        graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int w = sc.nextInt();
                graph[i][j] = w;
                graphT[j][i] = w;
                r = Long.max(r, w);
            }
        }

        while (r - l > 1) {
            long m = (r + l) / 2;
            compCnt = 0;
            order.clear();

            Arrays.fill(visitColors, Colors.WHITE);
            for (int v = 0; v < n; v++) {
                if (visitColors[v] == Colors.WHITE) {
                    dfsToOutOrder(v, m);
                }
            }

            Arrays.fill(visitColors, Colors.WHITE);
            for (int i = 0; i < n; i++) {
                int v = order.get(n - i - 1);
                if (visitColors[v] == Colors.WHITE) {
                    dfsColorComponents(v, m);
                    if (compCnt == n) {
                        r = m;
                    } else {
                        l = m;
                    }
                    break;
                }
            }
        }

        sc.printStr(r);

    }

    private void dfsToOutOrder(final int v, final long w) {
        visitColors[v] = Colors.GRAY;
        for (int u = 0; u <n; u++) {
            if (visitColors[u] == Colors.WHITE && graph[v][u] <= w) {
                dfsToOutOrder(u, w);
            }
        }
        order.add(v);
        visitColors[v] = Colors.BLACK;
    }

    private void dfsColorComponents(final int v, final long w) {
        visitColors[v] = Colors.GRAY;
        compCnt++;
        for (int u = 0; u < n; u++) {
            if (visitColors[u] == Colors.WHITE && graphT[v][u] <= w) {
                dfsColorComponents(u, w);
            }
        }
        visitColors[v] = Colors.BLACK;
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
        (new H()).solution();
    }

    void solution() throws IOException {
        try (FastScanner reader = new FastScanner()) {
            solve(reader);
        }
    }

    private String arrayList2String(final ArrayList<Integer> arr) {
        StringBuilder res = new StringBuilder();
        for (int cur : arr) {
            res.append(cur);
            res.append(' ');
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
