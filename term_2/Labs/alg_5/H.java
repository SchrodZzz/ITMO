package solutions.alg_5;

import sun.jvm.hotspot.utilities.AssertionFailure;

import java.io.*;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

public class H {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    Random rnd = new Random();

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        Treap tree = new Treap(null);
        for (int i = 0; i < n; i++) {
            switch (reader.next()) {
                case "+": {
                    tree.insert(reader);
                    break;
                }
                case "-": {
                    tree.remove(reader);
                    break;
                }
                case "?": {
                    tree.query(reader);
                    break;
                }
            }
        }
    }

    class Treap {
        Node root;

        public Treap(Node root) {
            this.root = root;
        }

        void update(Node t) {
            t.or = ((t.left == null) ? 0 : t.left.or) | ((t.right == null) ? 0 : t.right.or) | t.type;
            t.sum = ((t.left == null) ? 0 : t.left.sum) + ((t.right == null) ? 0 : t.right.sum) + t.num;
        }

        Node merge(Node t1, Node t2) {
            if (t2 == null || t1 == null) {
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

        Node mergeT(Node t1, Node t2) {
            if (t1 == null || t2 == null) {
                return merge(t1, t2);
            }
            Node tmp = getFirst(t2);
            if (getLast(t1).type == tmp.type) {
                t1 = addToLast(t1, tmp.num);
                t2 = removeFirst(t2);
            }
            return merge(t1, t2);
        }

        Node[] split(Node t, int x) {
            if (t == null && x == 0) {
                return new Node[2];
            } else if (t != null) {
                int leftSize = t.left == null ? 0 : t.left.sum;
                if (x <= leftSize) {
                    Node[] tmp = split(t.left, x);
                    t.left = tmp[1];
                    update(t);
                    tmp[1] = t;
                    return tmp;
                } else if (x < leftSize + t.num) {
                    Node tmp = new Node(null, t.right, t.y, t.num, t.type);
                    int tmpN = t.num;
                    t.right = null;
                    t.num = x - leftSize;
                    tmp.num = tmpN - t.num;
                    update(t);
                    update(tmp);
                    return new Node[]{t, tmp};
                } else {
                    Node[] tmp = split(t.right, x - leftSize - t.num);
                    t.right = tmp[0];
                    update(t);
                    tmp[0] = t;
                    return tmp;
                }
            } else {
                throw new AssertionFailure("oops");
            }
        }

        Node getLast(Node a) {
            return a.right == null ? a : getLast(a.right);
        }

        Node getFirst(Node a) {
            return a.left == null ? a : getFirst(a.left);
        }

        Node addToLast(Node a, int number) {
            if (a.right == null) {
                a.num += number;
                update(a);
                return a;
            } else {
                a.right = addToLast(a.right, number);
                update(a);
                return a;
            }
        }

        Node removeFirst(Node a) {
            if (a.left == null) {
                return a.right;
            } else {
                a.left = removeFirst(a.left);
                update(a);
                return a;
            }
        }

        void insert(FastScanner reader) {
            int idx = reader.nextInt();
            Node newNode = new Node(null, null, rnd.nextInt(), reader.nextInt(),
                    1 << (reader.next().charAt(0) - 'a'));
            Node[] t = split(root, idx - 1);
            root = mergeT(mergeT(t[0], newNode), t[1]);
        }

        void remove(FastScanner reader) {
            Node[] t = split(root, reader.nextInt() - 1);
            root = mergeT(t[0], split(t[1], reader.nextInt())[1]);
        }

        void query(FastScanner reader) {
            int idx = reader.nextInt();
            Node[] t1 = split(root, idx - 1);
            Node[] t2 = split(t1[1], reader.nextInt() - idx + 1);
            reader.printStr(Integer.bitCount(t2[0].or));
            root = mergeT(t1[0], mergeT(t2[0], t2[1]));
        }

        class Node {
            int y, num, or, sum, type;

            Node left;
            Node right;

            Node(Node left, Node right, int y, int num, int type) {
                this.right = right;
                this.left = left;
                this.type = type;
                this.num = num;
                this.sum = num;
                this.or = type;
                this.y = y;
            }
        }
    }

    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new H()).solution();
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
