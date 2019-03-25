package solutions.alg_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class C {
    public static void main(String[] args) {
        (new C()).solve();
    }

    private int size;
    private Node tree[];

    private void solve() {
        FastScanner reader = new FastScanner();
        int n = reader.nextInt();
        size = 1;
        while (size < n) {
            size <<= 1;
        }
        tree = new Node[size << 1];
        for (int i = size + n - 1; i < (size << 1); i++)
            tree[i] = new Node(Long.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            tree[size + i] = new Node(reader.nextInt());
            tree[size + i].isLeaf = true;
        }
        buildTree(1, size, 2 * size - 1);
        String cmd = reader.nextLine();
        while (cmd != null) {
            String[] splitedCmd = cmd.split(" ");
            int l = Integer.parseInt(splitedCmd[1]);
            int r = Integer.parseInt(splitedCmd[2]);
            switch (splitedCmd[0]) {
                case "setVal": {
                    set(1, size, 2 * size - 1, size + l - 1, size + r - 1, Integer.parseInt(splitedCmd[3]));
                    break;
                }
                case "add": {
                    add(1, size, 2 * size - 1, size + l - 1, size + r - 1, Integer.parseInt(splitedCmd[3]));
                    break;
                }
                case "min": {
                    System.out.println(getMin(1, size, 2 * size - 1, size + l - 1, size + r - 1).val);
                }
            }
            cmd = reader.nextLine();
        }
    }

    private class Node {
        long val;
        long add;
        long set;
        boolean isLeaf;
        boolean isSet;
        boolean hasAdd;

        Node(long value) {
            val = value;
            isLeaf = false;
            isSet = false;
            hasAdd = false;
        }

        void set(long value) {
            if (isLeaf) {
                val = value;
                return;
            }
            if (hasAdd) {
                hasAdd = false;
                add = 0;
            }
            set = value;
            isSet = true;
            val = value;
        }

        void add(long value) {
            if (isLeaf) {
                val += value;
                return;
            }
            if (isSet) {
                set += value;
                val += value;
                return;
            }
            add += value;
            hasAdd = true;
            val += value;
        }

    }

    void buildTree(int v, int tl, int tr) {
        if (tl != tr) {
            int tm = (tl + tr) / 2;
            buildTree(v * 2, tl, tm);
            buildTree(v * 2 + 1, tm + 1, tr);
            tree[v] = new Node(Math.min(tree[v * 2].val, tree[v * 2 + 1].val));
        }
    }

    void push(int v) {
        if (tree[v].isLeaf) return;
        if (tree[v].isSet) {
            tree[2 * v].set(tree[v].set);
            tree[2 * v + 1].set(tree[v].set);
            tree[v].isSet = false;
        }
        if (tree[v].hasAdd) {
            tree[v * 2].add(tree[v].add);
            tree[v * 2 + 1].add(tree[v].add);
            tree[v].hasAdd = false;
            tree[v].add = 0;
        }
    }

    Node findMin(Node a, Node b) {
        return (a.val >= b.val) ? b : a;
    }

    Node getMin(int v, int tl, int tr, int l, int r) {
        if (l > r) return new Node(Long.MAX_VALUE);
        if (tl == l && tr == r) return tree[v];
        push(v);
        int tm = (tl + tr) / 2;
        Node left = getMin(v * 2, tl, tm, l, Math.min(tm, r));
        Node right = getMin(v * 2 + 1, tm + 1, tr, Math.max(tm + 1, l), r);
        return findMin(left, right);
    }

    void set(int v, int tl, int tr, int l, int r, long value) {
        if (l > r) return;
        if (l == tl && r == tr) {
            tree[v].set(value);
            return;
        }
        push(v);
        int tm = (tl + tr) / 2;
        set(v * 2, tl, tm, l, Math.min(r, tm), value);
        set(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, value);
        tree[v].val = findMin(tree[2 * v], tree[2 * v + 1]).val;
    }

    void add(int v, int tl, int tr, int l, int r, long value) {
        if (l > r) return;
        if (tl == l && tr == r) {
            tree[v].add(value);
            return;
        }
        push(v);
        int tm = (tl + tr) / 2;
        add(v * 2, tl, tm, l, Math.min(r, tm), value);
        add(v * 2 + 1, tm + 1, tr, Math.max(tm + 1, l), r, value);
        tree[v].val = findMin(tree[2 * v], tree[2 * v + 1]).val;
    }


    private class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
    }
}