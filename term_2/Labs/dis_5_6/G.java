package solutions.dis_5;

import java.io.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.StringTokenizer;

public class G {
    final private String fileName = "equivalence";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    private static int[][] dkaA;
    private static int[][] dkaB;
    private static boolean[] fStatesA;
    private static boolean[] fStatesB;
    private static Queue<Node> queue = new LinkedList<>();
    private static boolean[] markedA;
    private static boolean[] markedB;

    void solve(FastScanner reader) {
        int n1 = reader.nextInt();
        int m1 = reader.nextInt();
        int k1 = reader.nextInt();
        dkaA = new int[n1 + 1][26];
        fStatesA = new boolean[n1 + 1];
        markedA = new boolean[n1 + 1];
        for (int i = 0; i < k1; i++) {
            fStatesA[reader.nextInt()] = true;
        }
        for (int i = 0; i < m1; i++) {
            int from = reader.nextInt();
            int to = reader.nextInt();
            dkaA[from][reader.next().charAt(0) - 97] = to;
        }
        int n2 = reader.nextInt();
        int m2 = reader.nextInt();
        int k2 = reader.nextInt();
        dkaB = new int[n2 + 1][26];
        fStatesB = new boolean[n2 + 1];
        markedB = new boolean[n2 + 1];
        for (int i = 0; i < k2; i++) {
            fStatesB[reader.nextInt()] = true;
        }
        for (int i = 0; i < m2; i++) {
            int from = reader.nextInt();
            int to = reader.nextInt();
            dkaB[from][reader.next().charAt(0) - 97] = to;
        }
        reader.printStr((getAns()) ? "YES" : "NO");
    }

    private boolean getAns() {
        queue.add(new Node(1, 1));
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int u = cur.u;
            int v = cur.v;
            if (fStatesA[u] != fStatesB[v]) {
                return false;
            }
            markedA[u] = (u != 0);
            markedB[v] = (v != 0);
            if ((u != 0) || (v != 0)) {
                for (int i = 0; i < 26; i++) {
                    if (!markedA[dkaA[u][i]] || !markedB[dkaB[v][i]]) {
                        queue.add(new Node(dkaA[u][i], dkaB[v][i]));
                    }
                }
            }
        }
        return true;
    }

    private class Node {
        int u;
        int v;

        public Node(int u, int v) {
            this.u = u;
            this.v = v;
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
