package solutions.alg_5;

import java.io.*;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

public class F {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    Random rnd = new Random();

    void solve(FastScanner reader) {
        Treap tree = new Treap(null);
        int n = reader.nextInt();
        for (int i = 0; i < n; ++i) {
            String cmd = reader.next();
            int k = reader.nextInt();
            switch (cmd) {
                case "1":
                case "+1": {
                    tree.insert(k);
                    break;
                }
                case "-1": {
                    tree.delete(k);
                    break;
                }
                case "0": {
                    reader.printStr(-tree.kth(k));
                    break;
                }
            }
        }
    }

    class Treap {
        private Node root;

        public Treap(Node root) {
            this.root = root;
        }

        void update(Node t) {
            if (t != null) {
                t.size = (t.left == null ? 0 : t.left.size) + (t.right == null ? 0 : t.right.size) + 1;
            }
        }

        Node merge(Node t1, Node t2) {
            if (t2 == null || t1 == null) {
                update(t1 == null ? t2 : t1);
                return t1 == null ? t2 : t1;
            }
            if (t1.y > t2.y) {
                t1.right = merge(t1.right, t2);
                update(t1);
                return t1;
            } else {
                t2.left = merge(t1, t2.left);
                update(t2);
                return t2;
            }
        }

        NodePair split(Node t, int k) {
            if (t == null) {
                return new NodePair(null, null);
            }
            if (t.k >= k) {
                NodePair tmp = split(t.left, k);
                t.left = tmp.second;
                update(t);
                update(tmp.first);
                return new NodePair(tmp.first, t);
            } else {
                NodePair tmp = split(t.right, k);
                t.right = tmp.first;
                update(tmp.second);
                update(t);
                return new NodePair(t, tmp.second);
            }
        }

        void insert(int x) {
            x = -x;
            NodePair tmp = split(root, x);
            root = merge(tmp.first, merge(new Node(rnd.nextInt(), x, 1), tmp.second));
        }

        void delete(int x) {
            x = -x;
            NodePair tmp = split(root, x);
            root = merge(tmp.first, splitT(tmp.second, 1).second);
        }

        NodePair splitT(Node t, int x) {
            if (t == null) {
                return new NodePair(null, null);
            }
            if ((t.left == null ? 0 : t.left.size) >= x) {
                NodePair tmp = splitT(t.left, x);
                t.left = tmp.second;
                update(tmp.first);
                update(t);
                return new NodePair(tmp.first, t);
            } else {
                NodePair tmp = splitT(t.right, x - (t.left != null ? t.left.size : 0) - 1);
                t.right = tmp.first;
                update(tmp.second);
                update(t);
                return new NodePair(t, tmp.second);
            }
        }

        int kth(int x) {
            NodePair tmp1 = splitT(root, x - 1);
            NodePair tmp2 = splitT(tmp1.second, 1);
            int res = tmp2.first.k;
            root = merge(tmp1.first, merge(tmp2.first, tmp2.second));
            return res;
        }

        class Node {
            int y, k, size;

            Node left;
            Node right;

            public Node(int y, int k, int size) {
                this.y = y;
                this.k = k;
                this.size = size;
            }
        }

        class NodePair {
            Node first;
            Node second;

            NodePair(Node first, Node second) {
                this.first = first;
                this.second = second;
            }
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
