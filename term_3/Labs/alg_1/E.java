import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class E {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private ArrayList<ArrayList<Pair<Integer, Integer>>> adjacency = new ArrayList<>();
    private Stack<Edge> stack = new Stack<>();

    private Colors[] visitColors;
    private int[] colors;
    private int[] tin;
    private int[] up;

    private int time = 0;
    private int maxColor = 0;

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        colors = new int[m];
        visitColors = new Colors[n];
        tin = new int[n];
        up = new int[n];

        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjacency.get(u).add(new Pair(v, i));
            adjacency.get(v).add(new Pair(u, i));
        }

        Arrays.fill(visitColors, Colors.WHITE);
        Arrays.fill(colors, -1);
        for (int v = 0; v < n; v++) {
            if (visitColors[v] == Colors.WHITE) {
                dfs(v);
            }
        }

        Arrays.fill(visitColors, Colors.WHITE);
        for (int v = 0; v < n; v++) {
            if (visitColors[v] == Colors.WHITE) {
                paint(v, maxColor);
            }
        }

        sc.printStr(maxColor);
        sc.printStr(array2String(colors));
    }

    void paint(int v, int color) {
        visitColors[v] = Colors.GRAY;
        for (Pair<Integer, Integer> u : adjacency.get(v)) {
            if (visitColors[u.getKey()] == Colors.WHITE) {
                if (up[u.getKey()] >= tin[v]) {
                    colors[u.getValue()] = ++maxColor;
                    paint(u.getKey(), maxColor);
                } else {
                    colors[u.getValue()] = color;
                    paint(u.getKey(), color);
                }
            } else if (colors[u.getValue()] == -1) {
                colors[u.getValue()] = color;
            }
        }
        visitColors[v] = Colors.BLACK;
    }

    void dfs(int v) {
        visitColors[v] = Colors.GRAY;
        tin[v] = time++;
        up[v] = tin[v];
        for (Pair<Integer, Integer> u : adjacency.get(v)) {
            if (visitColors[u.getKey()] == Colors.WHITE) {
                dfs(u.getKey());
                up[v] = Math.min(up[v], up[u.getKey()]);
            } else {
                up[v] = Math.min(up[v], tin[u.getKey()]);
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
        (new E()).solution();
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
