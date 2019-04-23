package solutions.alg_5;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class B {
    final private String fileName = "in task A AVL would be OK too (-;";
    final private boolean isTest = false;
    final private boolean isSysRead = true;

    void solve(FastScanner reader) {
        AVL tree = new AVL(null);
        String cmd;
        while ((cmd = reader.nextLine()) != null) {
            long curVal = Long.parseLong(cmd.split(" ")[1]);
            Node root = tree.root;
            switch (cmd.split(" ")[0]) {
                case "insert": {
                    tree.insert(root, curVal);
                    break;
                }
                case "delete": {
                    tree.delete(tree.search(root, curVal), curVal);
                    break;
                }
                case "exists": {
                    reader.printStr(tree.search(root, curVal) != null);
                    break;
                }
                case "next": {
                    reader.printStr(tree.nxt(root, curVal) == null ? "none" : tree.nxt(root, curVal).val);
                    break;
                }
                case "prev": {
                    tree.prv(root, curVal);
                    reader.printStr(tree.prv(root, curVal) == null ? "none" : tree.prv(root, curVal).val);
                    break;
                }
                case "printTree": {
                    tree.printTree(root);
                }
            }
        }
    }

    private class AVL {
        Node root;

        public AVL(Node root) {
            this.root = root;
        }

        Node search(Node x, long val) {
            if (x == null) {
                return null;
            }
            if (x.val == val) {
                return x;
            }
            if (x.val > val) {
                return search(x.left, val);
            } else {
                return search(x.right, val);
            }
        }

        void insert(Node x, long val) {
            if (x == null) {
                root = new Node(val);
                return;
            }
            if (x.val == val) {
                return;
            }
            if (x.val > val) {
                if (x.left == null) {
                    x.left = new Node(val);
                    x.left.parent = x;
                    balance(x.left);
                    updateBalance(x);
                } else {
                    insert(x.left, val);
                }
            } else {
                if (x.right == null) {
                    x.right = new Node(val);
                    x.right.parent = x;
                    balance(x.right);
                    updateBalance(x);
                } else {
                    insert(x.right, val);
                }
            }
        }

        void balance(Node x) {
            if (x == null) {
                return;
            }
            updateBalance(x);
            if (x.dif == -2) {
                if (x.right.dif == 1) {
                    RL(x);
                } else {
                    rotateLeft(x);
                }
            }
            if (x.dif == 2) {
                if (x.left.dif == -1) {
                    LR(x);
                } else {
                    rotateRight(x);
                }
            }
            updateBalance(x);
            balance(x.parent);
        }

        void rotateRight(Node x) {
            Node tmp = x.left;
            x.left = tmp.right;
            if (tmp.right != null) {
                tmp.right.parent = x;
            }
            tmp.right = x;
            if (x.parent == null) {
                root = tmp;
                tmp.parent = null;
            } else {
                if (x.parent.left == x) {
                    x.parent.left = tmp;
                } else {
                    x.parent.right = tmp;
                }
                tmp.parent = x.parent;
            }
            x.parent = tmp;
            updateBalance(x);
            updateBalance(tmp);
        }

        void rotateLeft(Node x) {
            Node tmp = x.right;
            x.right = tmp.left;
            if (tmp.left != null) {
                tmp.left.parent = x;
            }
            tmp.left = x;
            if (x.parent == null) {
                root = tmp;
                tmp.parent = null;
            } else {
                if (x.parent.left == x) {
                    x.parent.left = tmp;
                } else {
                    x.parent.right = tmp;
                }
                tmp.parent = x.parent;
            }
            x.parent = tmp;
            updateBalance(x);
            updateBalance(tmp);
        }

        void LR(Node x) {
            rotateLeft(x.left);
            rotateRight(x);
        }

        void RL(Node x) {
            rotateRight(x.right);
            rotateLeft(x);
        }

        int getHeight(Node x) {
            return x == null ? 0 : x.height;
        }

        void updateBalance(Node x) {
            if (x == null) {
                return;
            }
            x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
            x.dif = getHeight(x.left) - getHeight(x.right);
        }

        void delete(Node x, long val) {
            if (x == null) {
                return;
            }
            if (x.left == null && x.right == null) {
                if (x.parent == null) {
                    root = null;
                    return;
                }
                if (x == x.parent.right) {
                    x.parent.right = null;
                } else {
                    x.parent.left = null;
                }
                balance(x.parent);
                return;
            }
            if (x.left != null && x.right != null) {
                Node nextNode = nxt(x.right, val);
                x.val = nextNode.val;
                delete(nextNode, val);
                return;
            }
            if (x.left != null) {
                if (x.parent == null) {
                    root = x.left;
                    x.left.parent = null;
                    return;
                }
                if (x == x.parent.right) {
                    x.parent.right = x.left;
                    x.left.parent = x.parent;
                } else {
                    x.parent.left = x.left;
                    x.left.parent = x.parent;
                }
            } else {
                if (x.parent == null) {
                    root = x.right;
                    x.right.parent = null;
                    return;
                }
                if (x == x.parent.right) {
                    x.parent.right = x.right;
                    x.right.parent = x.parent;
                } else {
                    x.parent.left = x.right;
                    x.right.parent = x.parent;
                }
            }
            balance(x.parent);
        }

        Node min(Node x) {
            return x.left == null ? x : min(x.left);
        }

        Node max(Node x) {
            return x.right == null ? x : max(x.right);
        }

        Node nxt(Node x, long val) {
            Node tmp = null;
            while (x != null) {
                if (x.val > val) {
                    tmp = x;
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            return tmp;
        }

        Node prv(Node x, long val) {
            Node tmp = null;
            while (x != null) {
                if (x.val < val) {
                    tmp = x;
                    x = x.right;
                } else {
                    x = x.left;
                }
            }
            return tmp;
        }

        void printTree(Node x) {
            if (x != null) {
                printTree(x.left);
                System.out.print(x.val + " ");
                printTree(x.right);
            }
        }
    }

    private class Node {
        long val;
        int dif = 0;
        int height = 1;

        Node left = null;
        Node right = null;
        Node parent = null;

        public Node(long val) {
            this.val = val;
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new B()).solution();
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
