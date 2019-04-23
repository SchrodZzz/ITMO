package solutions.alg_5;

import java.io.*;
import java.util.*;

public class E {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    Random rnd = new Random();

    public Set<Integer> xs = new HashSet<>();

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        Treap tree = new Treap(null);
        long curSum = 0;
        for (int i = 0; i < n; ++i) {
            switch (reader.next().charAt(0)) {
                case '+':
                    long sum = (Integer.parseInt(reader.next()) + curSum) % (long) 1e9;
                    tree.add((int) sum);
                    curSum = 0;
                    break;
                case '?':
                    curSum = tree.sum(Integer.parseInt(reader.next()), Integer.parseInt(reader.next()));
                    reader.printStr(curSum);
                    break;
            }
        }
    }

    class Treap {

        private Node root;
        private long sum;

        public Treap(Node root) {
            this.root = root;
        }

        NodePair split(Node t, int x, int y) {
            if (t == null) {
                return new NodePair(null, null);
            }
            if (t.x < x) {
                NodePair tmp = split(t.right, x, y);
                t.right = null;
                t.sum = (t.left == null ? 0 : t.left.sum) + t.x;
                return new NodePair(merge(t, tmp.first), tmp.second);
            } else {
                NodePair tmp = split(t.left, x, y);
                t.left = null;
                t.sum = (t.right == null ? 0 : t.right.sum) + t.x;
                return new NodePair(tmp.first, merge(tmp.second, t));
            }
        }

        private Node merge(Node t1, Node t2) {
            if (t2 == null || t1 == null) {
                return t1 == null ? t2 : t1;
            }
            if (t1.y < t2.y) {
                t2.left = merge(t1, t2.left);
                updateSum(t2);
                return t2;
            } else {
                t1.right = merge(t1.right, t2);
                updateSum(t1);
                return t1;
            }
        }

        void add(int x) {
            if (!xs.contains(x)) {
                xs.add(x);
                insert(x, rnd.nextInt());
            }
        }

        void insert(int x, int y) {
            Node cur = root;
            while (cur != null && cur.x != x) {
                cur = (x < cur.x) ? cur.left : cur.right;
            }
            if (cur == null) {
                NodePair tmp = split(root, x, y);
                root = merge(tmp.first, merge(new Node(x, y), tmp.second));
            }
        }

        long sum(int t1, int t2) {
            sum = 0;
            getSum(t1, t2, root);
            return sum;
        }

        long min(Node t) {
            return t.left != null ? min(t.left) : t.x;
        }

        long max(Node t) {
            return t.right != null ? max(t.right) : t.x;
        }

        void updateSum(Node t) {
            t.sum = (t.left == null ? 0 : t.left.sum) + (t.right == null ? 0 : t.right.sum) + t.x;
        }

        void getSum(int l, int r, Node t) {
            if (t != null && t.x >= l && t.x <= r) {
                if (min(t) >= l && max(t) <= r) {
                    sum += t.sum;
                } else if (min(t) >= l) {
                    sum += t.x;
                    sum += t.left != null ? t.left.sum : 0;
                    getSum(l, r, t.right);
                } else if (max(t) <= r) {
                    sum += t.x;
                    sum += t.right != null ? t.right.sum : 0;
                    getSum(l, r, t.left);
                } else {
                    sum += t.x;
                    getSum(l, r, t.left);
                    getSum(l, r, t.right);
                }
            } else if (t != null && t.x > r) {
                getSum(l, r, t.left);
            } else if (t != null && t.x < l) {
                getSum(l, r, t.right);
            }
        }

        class Node {
            int x;
            int y;
            long sum;

            Node left;
            Node right;

            Node(int x, int y) {
                this.x = x;
                this.y = y;
                this.sum = x;
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
