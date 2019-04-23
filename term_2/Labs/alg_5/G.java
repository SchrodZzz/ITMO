package solutions.alg_5;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class G {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    void solve(FastScanner reader) {
        Treap tree = new Treap(null);
        tree.gen(reader.nextInt());
        int m = reader.nextInt();
        for (int i = 0; i < m; i++) {
            int left = reader.nextInt();
            int right = reader.nextInt() + 1;
            Treap.NodePair t1 = tree.split(tree.root, left);
            Treap.NodePair t2 = tree.split(t1.second, right);
            if (t1.first != null) t1.first.shift += (right - left);
            if (t2.first != null) t2.first.shift -= (left - 1);
            tree.root = tree.merge(t2.first, tree.merge(t1.first, t2.second));
        }
        tree.printTree(tree.root);
    }

    class Treap {

        private Node root;

        public Treap(Node root) {
            this.root = new Node(1, 1);
        }

        NodePair split(Node t, int x) {
            if (t == null) {
                return new NodePair(null, null);
            }
            push(t);
            if (t.x >= x) {
                NodePair tmp = split(t.left, x);
                t.left = tmp.second;
                push(t);
                return new NodePair(tmp.first, t);
            } else {
                NodePair tmp = split(t.right, x);
                t.right = tmp.first;
                push(t);
                return new NodePair(t, tmp.second);
            }
        }

        Node merge(Node t1, Node t2) {
            if (t2 == null && t1 == null) {
                return null;
            } else if (t2 == null || t1 == null) {
                push(t1 == null ? t2 : t1);
                return t1 == null ? t2 : t1;
            } else {
                update(t1, t2);
                if (t1.y < t2.y) {
                    t2.left = merge(t1, t2.left);
                    update(t1, t2);
                    return t2;
                } else {
                    t1.right = merge(t1.right, t2);
                    update(t1, t2);
                    return t1;
                }
            }
        }

        void gen(int n) {
            Node tmp = root;
            for (int i = 2; i <= n; i++) {
                root.right = new Node(i, i);
                root = root.right;
            }
            root = tmp;
        }

        void push(Node node) {
            if (node != null) {
                if (node.right != null) {
                    node.right.shift += node.shift;
                }
                if (node.left != null) {
                    node.left.shift += node.shift;
                }
                node.x += node.shift;
                node.shift = 0;
            }
        }

        void update(Node t1, Node t2) {
            push(t1);
            push(t2);
        }

        void printTree(Node t) {
            if (t != null) {
                printTree(t.left);
                System.out.print(t.y + " ");
                printTree(t.right);
            }
        }

        class Node {
            int x;
            int y;
            int shift;

            Node left;
            Node right;

            Node(int x, int y) {
                this.x = x;
                this.y = y;
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
