package solutions.alg_5;

import java.io.*;
import java.util.*;

public class C {
    final private String fileName = "in task A AVL would be OK too (-;";
    final private boolean isTest = false;
    final private boolean isSysRead = true;

    String[] ans;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        ans = new String[n];
        List<Triple> data = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            data.add(new Triple(reader.nextLong(), reader.nextInt(), i));
        }
        data.sort(Comparator.comparing(o -> o.x));
        Node root = new Node(data.get(0).x, data.get(0).y, data.get(0).z, null, null, null);
        for (int i = 1; i < n; i++) {
            long curX = data.get(i).x;
            int curY = data.get(i).y;
            int curZ = data.get(i).z;
            if (root.y < curY) {
                root.right = new Node(curX, curY, curZ, null, null, root);
                root = root.right;
            } else {
                Node tmp = root;
                while (tmp.parent != null && tmp.y >= curY) {
                    tmp = tmp.parent;
                }
                if (tmp.y >= curY) {
                    root = new Node(curX, curY, curZ, tmp, null, null);
                    tmp.parent = root;
                } else {
                    root = new Node(curX, curY, curZ, tmp.right, null, tmp);
                    tmp.right = root;
                    root.parent = tmp;
                    root.left.parent = root;
                }
            }
        }
        reader.printStr("YES");
        while (root.parent != null) {
            root = root.parent;
        }
        printTree(root);
        for (String cur : ans) {
            reader.printStr(cur);
        }
    }

    void printTree(Node x) {
        if (x != null) {
            int idx = x.z-1;
            ans[idx] = (String.format("%d %d %d",
                    x.parent == null ? 0 : x.parent.z,
                    x.left == null ? 0 : x.left.z,
                    x.right == null ? 0 : x.right.z));
            printTree(x.left);
            printTree(x.right);
        }
    }

    private class Triple {
        long x;
        int y, z;

        public Triple(long x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private class Node {
        long x;
        int y;
        int z;

        Node left = null;
        Node right = null;
        Node parent = null;

        public Node(long x, int y, int z, Node left, Node right, Node parent) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new C()).solution();
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
