import java.io.*;
import java.util.*;

public class A {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private ArrayList<Integer> ans = new ArrayList<>();

    private Colors[] colors;
    private ArrayList<ArrayList<Integer>> adjacency;

    private boolean hasCycle = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int m = reader.nextInt();
        colors = new Colors[n];
        adjacency = new ArrayList<>();
        for (int i = 0; i < n; i++) {
             adjacency.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = reader.nextInt() - 1;
            int v = reader.nextInt() - 1;
            adjacency.get(u).add(v);
        }
        topSort();
        if (hasCycle) {
            reader.printStr(-1);
        } else {
            reader.printStr(arrayList2String(ans));
        }
    }

    void topSort() {
        Arrays.fill(colors, Colors.WHITE);
        int size = adjacency.size();
        for (int v = 0; v < size; v++) {
            if (colors[v] == Colors.WHITE) {
                dfs(v);
            }
        }
        Collections.reverse(ans);
    }

    void dfs(int v) {
        colors[v] = Colors.GRAY;
        for (int u : adjacency.get(v)) {
            if (colors[u] == Colors.WHITE) {
                dfs(u);
            } else if (colors[u] == Colors.GRAY) {
                hasCycle = true;
            }
        }
        colors[v] = Colors.BLACK;
        ans.add(v + 1);
    }

    private enum Colors {
        WHITE,
        GRAY,
        BLACK
    }

    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new A()).solution();
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
