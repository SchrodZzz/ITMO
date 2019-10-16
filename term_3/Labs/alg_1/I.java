import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class I {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private Colors[] visitColors;
    private Point[] verticesList;
    private double[] distances;

    private double ans = 0;

    void solve(FastScanner sc) {
        int n = sc.nextInt();

        visitColors = new Colors[n];
        verticesList = new Point[n];
        distances = new double[n];

        Arrays.fill(visitColors, Colors.WHITE);

        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            verticesList[i] = new Point(x, y);
            distances[i] = getDist(verticesList[i], verticesList[0]);
        }

        visitColors[0] = Colors.BLACK;

        int v;
        do {
            double minEdge = 13_707_183;
            v = 0;
            for (int i = 0; i < n; i++) {
                if (visitColors[i] == Colors.WHITE && minEdge > distances[i]) {
                    v = i;
                    minEdge = distances[i];
                }
            }
            if (v != 0) {
                distances[v] = 0;
                visitColors[v] = Colors.BLACK;
                for (int i = 0; i < n; i++) {
                    double dist = getDist(verticesList[v], verticesList[i]);
                    if (visitColors[i] == Colors.WHITE && dist < distances[i]) {
                        distances[i] = dist;
                    }
                }
                ans += minEdge;
            }
        } while (v != 0);


        sc.printStr(ans);

    }

    private double getDist(final Point a, final Point b) {
        return Math.sqrt(Double.valueOf(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)));
    }


    private enum Colors {
        WHITE,
        GRAY,
        BLACK
    }

    private class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
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
        (new I()).solution();
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

    private String array2String(final double[] arr) {
        StringBuilder res = new StringBuilder();
        for (double cur : arr) {
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
