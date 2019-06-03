package solutions.alg_6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class A { //better routsTo write it in kotlin))
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    private int ln = 0;
    private List[] up;
    private Node[] ps;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        final int SIZE = (int) 10e5 + 1;
        ps = new Node[SIZE];
        for (int i = 0; i < SIZE; i++) {
            ps[i] = new Node();
        }
        int root = 0;
        for (int i = 0; i < n; i++) {
            int current = reader.nextInt() - 1;
            if (current == -1) {
                root = i;
                ps[i].a = i;
            } else {
                ps[i].a = current;
                ps[current].chs.add(i);
            }
        }
        dfs(root, 1);
        up = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            up[i] = new ArrayList();
            up[i].add(ps[i].a);
        }
        ln = 1;
        while ((1 << ln) <= n) ++ln;
        for (int j = 1; j < ln + 1; j++) {
            for (int i = 0; i < n; i++) {
                up[i].add(up[(int) up[i].get(j - 1)].get(j - 1));
            }
        }
        int[] pows = new int[n];
        pows[0] = 1;
        for (int i = 1; i < n; i++) {
            pows[i] = pows[i - 1] << 1;
        }
        for (int i = 0; i < n; i++) {
            StringBuilder curAns = new StringBuilder();
            curAns.append(i + 1).append(": ");
            int cnt = 0;
            while (pows[cnt] + 1 <= ps[i].b) {
                curAns.append((int) up[i].get(cnt) + 1).append(" ");
                cnt++;
            }
            reader.printStr(curAns.toString());
        }
    }

    void dfs(int v, int d) {
        ps[v].b = d;
        if (ln < d) {
            ln = d;
        }
        int size = ps[v].chs.size();
        for (int i = 0; i < size; i++) {
            dfs(ps[v].chs.get(i), d + 1);
        }
    }

    class Node {
        ArrayList<Integer> chs = new ArrayList<>();
        int a;
        int b;
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
