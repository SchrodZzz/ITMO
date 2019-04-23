package solutions.alg_5;

import java.io.*;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

public class I {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    Random rnd = new Random();

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int m = reader.nextInt();
        int q = reader.nextInt();
        Node[] towns = new Node[n];
        Treap tree = new Treap();
        for (int i = 1; i <= n; i++) {
            towns[i - 1] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            tree.addRoad(towns[reader.nextInt() - 1], towns[reader.nextInt() - 1]);
        }
        for (int i = 0; i < q; i++) {
            switch (reader.next().charAt(0)) {
                case '+':
                    tree.addRoad(towns[reader.nextInt() - 1], towns[reader.nextInt() - 1]);
                    break;
                case '-':
                    tree.destroyRoad(towns[reader.nextInt() - 1], towns[reader.nextInt() - 1]);
                    break;
                case '?':
                    int a = reader.nextInt();
                    int b = reader.nextInt();
                    reader.printStr(--a == --b ? 0 : tree.getAnswer(towns[a], towns[b]));
                    break;
            }
        }
    }

    class Treap {

        public Treap() {
        }

        private void update(Node t) {
            if (!isNull(t)) {
                t.size = 1;
                if (t.right != null) {
                    t.size += t.right.size;
                    t.right.parent = t;
                }
                if (t.left != null) {
                    t.size += t.left.size;
                    t.left.parent = t;
                }
            }
        }

        private Node merge(Node t2, Node t1) {
            if (isNull(t1) || isNull(t2)) {
                //update(t1 == null ? t2 : t1);
                return isNull(t1) ? t2 : t1;
            }
            reverse(t2);
            reverse(t1);
            if (t2.y > t1.y) {
                t2.right = merge(t2.right, t1);
                update(t2);
                return t2;
            } else {
                t1.left = merge(t2, t1.left);
                update(t1);
                return t1;
            }
        }

        private NodePair split(Node t, int x) {
            if (isNull(t)) {
                return new NodePair(null, null);
            }
            reverse(t);
            NodePair tmp;
            int idx = (isNull(t.left)) ? 0 : t.left.size;
            if (idx < x) {
                tmp = split(t.right, x - idx - 1);
                if (!isNull(t.right)) {
                    t.right.parent = null;
                }
                t.right = tmp.first;
                if (!isNull(t.right)) {
                    t.right.parent = t;
                }
                update(t);
                update(tmp.second);
                tmp.first = t;
            } else {
                tmp = split(t.left, x);
                if (!isNull(t.left)) {
                    t.left.parent = null;
                }
                t.left = tmp.second;
                if (!isNull(t.left)) {
                    t.left.parent = t;
                }
                update(t);
                update(tmp.first);
                tmp.second = t;
            }
            return tmp;
        }

        boolean isNull(Node t) {
            return t == null;
        }

        private void addRoad(Node t1, Node t2) {
            Node p1 = getRoot(t1);
            Node p2 = getRoot(t2);
            if (p1 != p2) {
                int idx1 = getIndex(t1);
                int idx2 = getIndex(t2);
                if (idx1 == 0) {
                    if (idx2 == 0) {
                        p1.isRev ^= true;
                        merge(p1, p2);
                    } else {
                        merge(p2, p1);
                    }
                } else {
                    if (idx2 == 0) {
                        merge(p1, p2);
                    } else {
                        p2.isRev ^= true;
                        merge(p1, p2);
                    }
                }
            } else {
                p1.isLoop = true;
            }
        }

        private void destroyRoad(Node t1, Node t2) {
            if (t1 != t2) {
                Node root = getRoot(t1);
                int idx1 = getIndex(t1);
                int idx2 = getIndex(t2);
                if (root.isLoop) {
                    root.isLoop = false;
                    if (Math.min(idx1, idx2) != 0 || root.size != Math.max(idx1, idx2) + 1) {
                        NodePair tmp = split(root, Math.min(idx1, idx2) + 1);
                        merge(tmp.second, tmp.first);
                        Node newRoot = getRoot(tmp.first);
                        if (newRoot != null) {
                            newRoot.isLoop = false;
                        }
                    }
                } else {
                    NodePair tmp = split(root, Math.min(idx1, idx2) + 1);
                    if (tmp.first != null) {
                        tmp.first.isLoop = false;
                    }
                    if (tmp.second != null) {
                        tmp.second.isLoop = false;
                    }
                }
            }
        }

        private int getAnswer(Node t1, Node t2) {
            Node root = getRoot(t1);
            if (root == getRoot(t2)) {
                int idx1 = getIndex(t1);
                int idx2 = getIndex(t2);
                int min = Math.min(idx1, idx2);
                int max = Math.max(idx1, idx2);
                return (root.isLoop) ? Math.min(min - max + root.size - 1, max - min - 1) : max - min - 1;
            }
            return -1;
        }

        private Node getRoot(Node t) {
            return (isNull(t.parent)) ? t : getRoot(t.parent);
        }

        private int getIndex(Node t) {
            int idx;
            if (isNull(t.left)) {
                idx = 1;
            } else {
                idx = t.left.size + 1;
            }
            if (t.isRev) {
                idx = t.size - idx + 1;
            }
            while (t.parent != null) {
                if (t.parent.isRev) {
                    idx = t.size - idx + 1;
                    reverse(t.parent);
                } else {
                    if (t == t.parent.right) {
                        idx += t.parent.left == null ? 1 : t.parent.left.size + 1;
                    }
                    t = t.parent;
                }
            }
            return idx - 1;
        }

        private void reverse(Node t) {
            if (!isNull(t) && t.isRev) {
                Node tmp = t.left;
                t.left = t.right;
                t.right = tmp;
                if (t.left != null) {
                    t.left.isRev ^= true;
                }
                if (t.right != null) {
                    t.right.isRev ^= true;
                }
                update(t);
                t.isRev = false;
            }
        }
    }

    class Node {
        int y = rnd.nextInt();
        int size = 1;
        int val;

        Node left;
        Node right;
        Node parent;

        boolean isLoop;
        boolean isRev;

        public Node(int val) {
            this.val = val;
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

    int dbgCnt = 0;

    void debug() {
        System.out.println("Kurwa : " + ++dbgCnt);
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
