import java.io.*;
import java.util.*;

public class F {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private ArrayList<ArrayList<Integer>> adjacency = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> adjacencyT = new ArrayList<>();
    private ArrayList<Integer> order = new ArrayList<>();
    private TreeMap<Edge, Integer> condEdges = new TreeMap();

    private Colors[] visitColors;
    private int[] colors;

    private int maxColor = 0;

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        visitColors = new Colors[n];
        colors = new int[n];

        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
            adjacencyT.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjacency.get(u).add(v);
            adjacencyT.get(v).add(u);
        }

        Arrays.fill(visitColors, Colors.WHITE);
        for (int v = 0; v < n; v++) {
            if (visitColors[v] == Colors.WHITE) {
                dfsToOutOrder(v);
            }
        }

        Arrays.fill(visitColors, Colors.WHITE);
        for (int i = 0; i < n; i++) {
            int v = order.get(n - i - 1);
            if (visitColors[v] == Colors.WHITE) {
                dfsColorComponents(v);
                maxColor++;
            }
        }

        for (int v = 0; v < n; v++) {
            for (int curVertex : adjacency.get(v)) {
                if (colors[v] != colors[curVertex]) {
                    Edge e = new Edge(colors[v], colors[curVertex]);
                    if (!condEdges.containsKey(e)) {
                        condEdges.put(e, 7071);
                    }
                }
            }
        }
        System.out.println();
        sc.printStr(condEdges.size());

    }

    private void dfsToOutOrder(final int v) {
        visitColors[v] = Colors.GRAY;
        for (int u : adjacency.get(v)) {
            if (visitColors[u] == Colors.WHITE) {
                dfsToOutOrder(u);
            }
        }
        order.add(v);
        visitColors[v] = Colors.BLACK;
    }

    private void dfsColorComponents(final int v) {
        visitColors[v] = Colors.GRAY;
        colors[v] = maxColor;
        for (int u : adjacencyT.get(v)) {
            if (visitColors[u] == Colors.WHITE) {
                dfsColorComponents(u);
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
        (new F()).solution();
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
