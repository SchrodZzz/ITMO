import java.io.*;
import java.util.*;

public class G {
    final private String fileName = "kek";
    final private boolean isTest = false;
    final private boolean isSysRead = true;

    private ArrayList<String> ans = new ArrayList<>();
    private ArrayList<Integer> order = new ArrayList<>();
    private TreeMap<String, Integer> namesIndex = new TreeMap<>();

    private ArrayList<Integer>[] graph;
    private ArrayList<Integer>[] graphT;

    private Colors[] visitColors;
    private String[] names;
    private int[] colors;

    private int maxColor = -1;
    private int n;

    void solve(FastScanner sc) {
        n = 2 * sc.nextInt();
        int m = sc.nextInt();

        visitColors = new Colors[n];
        names = new String[n / 2];
        colors = new int[n];

        graphT = new ArrayList[n];
        graph = new ArrayList[n];

        for (int i = 0; i < (n / 2); i++) {
            names[i] = sc.next();
            namesIndex.put(names[i], i);
        }

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            graphT[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            String fst = sc.next();
            sc.next();
            String snd = sc.next();

            int u = 2 * namesIndex.get(fst.substring(1)) + bool2Int(fst.charAt(0) == '-');
            int v = 2 * namesIndex.get(snd.substring(1)) + bool2Int(snd.charAt(0) == '-');

            graph[u].add(v);
            graph[roundVertex(v)].add(roundVertex(u));
            graphT[v].add(u);
            graphT[roundVertex(u)].add(roundVertex(v));
        }

        Arrays.fill(visitColors, Colors.WHITE);
        Arrays.fill(colors, -1);
        for (int v = 0; v < n; v++) {
            if (visitColors[v] == Colors.WHITE) {
                dfsToOutOrder(v);
            }
        }

        for (int i = 0; i < n; i++) {
            int v = order.get(n - i - 1);
            if (colors[v] == -1) {
                maxColor++;
                dfsColorComponents(v);
            }
        }

        for (int v = 0; v < n; v += 2) {
            if (colors[v] == colors[v + 1]) {
                break;
            }
        }

        for (int v = 0; v < n; v += 2) {
            if (colors[v] > colors[v + 1]) {
                ans.add(names[v / 2]);
            }
        }
        sc.printStr(ans.size() == 0 ? -1 : ans.size());

        if (ans.size() > 0) {
            sc.printStr(arrayList2String(ans, '\n'));
        }

        if (isTest) {
            for (ArrayList<Integer> cur : graph) {
                sc.printStr(arrayList2String(cur, ' '));
            }
            sc.printStr('-');
            for (ArrayList<Integer> cur : graphT) {
                sc.printStr(arrayList2String(cur, ' '));
            }
        }
    }

    private void dfsToOutOrder(final int v) {
        visitColors[v] = Colors.GRAY;
        for (int u : graph[v]) {
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
        for (int u : graphT[v]) {
            if (colors[u] == -1) {
                dfsColorComponents(u);
            }
        }
        visitColors[v] = Colors.BLACK;
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
        (new G()).solution();
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
