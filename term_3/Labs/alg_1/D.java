import java.io.*;
import java.util.*;

public class D {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private ArrayList<ArrayList<Integer>> adjacency = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> pairEdges = new ArrayList<>();
    private Stack<Integer> stack = new Stack<>();

    private Colors[] visitColors;
    private int[] componentsColors;
    private int[] tin;
    private int[] up;

    private int time = 0;
    private int color = 0;

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        componentsColors = new int[n];
        visitColors = new Colors[n];
        tin = new int[n];
        up = new int[n];

        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
            pairEdges.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            if (adjacency.get(u).contains(v)) {
                pairEdges.get(u).add(v);
                pairEdges.get(v).add(u);
            }
            adjacency.get(u).add(v);
            adjacency.get(v).add(u);
        }

        Arrays.fill(visitColors, Colors.WHITE);
        Arrays.fill(componentsColors, -1);
        for (int v = 0; v < n; v++) {
            if (visitColors[v] == Colors.WHITE) {
                dfs(v, -1);
                paint(-7071);
            }
        }

        sc.printStr(color);
        sc.printStr(array2String(componentsColors));
    }

    private void dfs(int v, int prv) {
        visitColors[v] = Colors.GRAY;
        stack.push(v);
        tin[v] = time++;
        up[v] = tin[v];
        for (int u : adjacency.get(v)) {
            if (u == prv) {
                continue;
            }
            if (visitColors[u] == Colors.WHITE) {
                dfs(u, v);
                up[v] = Math.min(up[v], up[u]);
                if (up[u] > tin[v] && !pairEdges.get(v).contains(u)) {
                    paint(u);
                }
            } else {
                up[v] = Math.min(up[v], tin[u]);
            }
        }
        visitColors[v] = Colors.BLACK;
    }

    private void paint(int v) {
        color++;
        int last = -1;
        while (last != v && !stack.empty()) {
            int top = stack.pop();
            componentsColors[top] = color;
            last = top;
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
        (new D()).solution();
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

    private String array2String(int[] arr) {
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
