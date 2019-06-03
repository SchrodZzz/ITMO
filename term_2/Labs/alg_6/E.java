package solutions.alg_6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class E {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        Node[] vs = new Node[n];
        for (int i = 0; i < n; ++i) {
            vs[i] = new Node(i);
        }
        for (int i = 1; i < n; ++i) {
            int x = reader.nextInt() - 1;
            int y = reader.nextInt() - 1;
            vs[x].hol.add(vs[y]);
            vs[y].hol.add(vs[x]);
        }
        int m = reader.nextInt();
        for (int i = 0; i < m; ++i) {
            int x = reader.nextInt() - 1;
            int y = reader.nextInt() - 1;
            vs[x].toAns.add(vs[y]);
            vs[y].toAns.add(vs[x]);
        }
        vs[0].d = 0;
        dfs1(vs[0], null);
        int ans = dfs2(vs[0], null);
        reader.printStr(n - 1 - ans);
    }

    static class Node {
        final List<Node> hol = new ArrayList<Node>();
        final List<Node> toAns = new ArrayList<Node>();

        Node pr = this;
        Node ch = this;
        int rn = 0;
        int idx;

        int d = -1;
        int lk = 0;
        boolean ched = false;

        Node(int idx) {
            this.idx = idx;
        }

        Node parent() {
            if (this == pr) {
                return this;
            } else {
                return pr = pr.parent();
            }
        }

        void unite(Node v) {
            Node a = parent();
            Node b = v.parent();
            if (a != b) {
                if (a.rn != b.rn) {
                    ++a.rn;
                }
                if (a.rn > b.rn) {
                    b.pr = a;
                } else {
                    a.pr = b;
                }
            }
            parent().ch = this;
        }
    }

    static void dfs1(Node node, Node pr) {
        int size = node.hol.size();
        for (int i = 0; i < size; i++) {
            Node v = node.hol.get(i);
            if (v != pr) {
                v.d = node.d + 1;
                dfs1(v, node);
                node.unite(v);
            }
        }
        node.ched = true;
        size = node.toAns.size();
        for (int i = 0; i < size; i++) {
            Node v = node.toAns.get(i);
            if (v.ched) {
                Node ancestor = v.parent().ch;
                node.lk = Math.max(node.lk, node.d - ancestor.d);
                v.lk = Math.max(v.lk, v.d - ancestor.d);
            }
        }
    }

    static int dfs2(Node node, Node parent) {
        int rv = 0;
        int size = node.hol.size();
        for (int i = 0; i < size; i++) {
            Node v = node.hol.get(i);
            if (v != parent) {
                rv += dfs2(v, node);
                node.lk = Math.max(node.lk, v.lk - 1);
            }
        }
        rv += node.lk > 0 ? 1 : 0;
        return rv;
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
