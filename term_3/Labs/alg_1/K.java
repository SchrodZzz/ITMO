import javafx.util.Pair;

import java.io.*;
import java.util.*;

//TODO: todo

public class K {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private int[] colors;
    private Edge[] edgeList;

    private long ans = 0;

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        colors = new int[n];
        edgeList = new Edge[m];

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int w = sc.nextInt();
            edgeList[i] = new Edge(u, v, w);
        }

        for (int i = 0; i < n; i++) {
            colors[i] = i;
        }

        Arrays.sort(edgeList);

        for (Edge curVertex : edgeList) {
            int u = curVertex.u;
            int v = curVertex.v;
            if (getVertexColor(u) != getVertexColor(v)) {
                ans += curVertex.w;
                connectVertices(u, v);
            }
        }

        sc.printStr(ans);

    }

    private int getVertexColor(int v) {
        if (colors[v] != v) {
            colors[v] = getVertexColor(colors[v]);
        }
        return colors[v];
    }

    private void connectVertices(int u, int v) {
        int uColor = getVertexColor(u);
        int vColor = getVertexColor(v);
        if (uColor != vColor) {
            colors[uColor] = vColor;
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
        int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge e) {
            return this.w - e.w;
        }
    }

    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new K()).solution();
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
