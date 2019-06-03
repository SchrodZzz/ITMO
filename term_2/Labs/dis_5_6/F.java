package solutions.dis_5;

import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class F {
    final private String fileName = "isomorphism";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    private static boolean[] marked;
    static Node[] dkaA;
    static Node[] dkaB;

    void solve(FastScanner reader) {
        int n1 = reader.nextInt();
        int m1 = reader.nextInt();
        int k1 = reader.nextInt();
        dkaA = new Node[n1 + 1];
        for (int i = 1; i <= n1; i++) {
            dkaA[i] = new Node(i);
        }
        for (int i = 0; i < k1; i++) {
            dkaA[reader.nextInt()].fState = true;
        }
        for (int i = 0; i < m1; i++) {
            int from = reader.nextInt();
            int to = reader.nextInt();
            dkaA[from].trs.put(reader.next().charAt(0), to);
        }
        int n2 = reader.nextInt();
        int m2 = reader.nextInt();
        int k2 = reader.nextInt();
        dkaB = new Node[n2 + 1];
        for (int i = 1; i <= n2; i++) {
            dkaB[i] = new Node(i);
        }
        for (int i = 0; i < k2; i++) {
            dkaB[reader.nextInt()].fState = true;
        }
        for (int i = 0; i < m2; i++) {
            int from = reader.nextInt();
            int to = reader.nextInt();
            dkaB[from].trs.put(reader.next().charAt(0), to);
        }
        if (n1 != n2) {
            reader.printStr("NO");
        } else {
            marked = new boolean[n1 + 1];
            reader.printStr(dfs(dkaA[1], dkaB[1]) ? "YES" : "NO");
        }
    }

    static class Node {
        int num;
        Map<Character, Integer> trs = new TreeMap<>();
        boolean fState = false;
        Node(int num) {
            this.num = num;
        }
    }

    static boolean dfs(Node u, Node v) {
        marked[u.num] = true;
        if (u.fState != v.fState) {
            return false;
        }
        boolean result = true;
        for (char key : u.trs.keySet()) {
            try {
                Node t1 = dkaA[u.trs.get(key)];
                Node t2 = dkaB[v.trs.get(key)];
                if (!marked[t1.num]) {
                    result = result & dfs(t1, t2);
                }
            } catch (NullPointerException error) {
                return false;
            }
        }
        return result;
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
