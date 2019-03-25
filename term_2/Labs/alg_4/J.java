package solutions.alg_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class J {
    public static void main(String[] args) {
        (new J()).solve();
    }

    private void solve() {
        FastScanner reader = new FastScanner();
        int n = reader.nextInt();
        int m = reader.nextInt();
        int size = 1;
        while (size < n) {
            size = size << 1;
        }
        Node root = new Node(1, size);
        for (int i = 0; i < m; ++i) {
            String cmd = reader.next();
            int l = reader.nextInt();
            int r = reader.nextInt();
            switch (cmd.charAt(0)) {
                case 'a': {
                    Node res = root.get(l, r);
                    int ans = res.getIdx();
                    System.out.printf("%d %d\n", res.val, ans);
                    break;
                }
                case 'd': {
                    int val = reader.nextInt();
                    root.set(l, r, val);
                    break;
                }
                default: {
                    System.out.printf("%d",root.setVal);
                }
            }
        }
    }

    class Node {
        int lt;
        int rt;
        long val = 0;
        long setVal = -10000001;
        boolean hasLC = false;
        boolean hasRC = false;
        Node lChildren;
        Node rChildren;

        Node(int a, int b) {
            this.lt = a;
            this.rt = b;
        }

        Node get(int l, int r) {
            check();
            if (setVal != -10000001)
                setVals();
            int mid = (lt + rt) / 2;
            if (l == lt && r == rt) {
                return this;
            }
            if (r <= mid) {
                return lChildren.get(l, r);
            } else if (l > mid) {
                return rChildren.get(l, r);
            } else {
                Node left = lChildren.get(l, mid);
                Node right = rChildren.get(mid + 1, r);
                if (left.val <= right.val)
                    return left;
                else
                    return right;
            }
        }

        void set(int l, int r, long d) {
            check();
            if (setVal != -10000001)
                setVals();
            int mid = (lt + rt) / 2;
            if (l == lt && r == rt) {
                val = Math.max(d, val);
                setVal = Math.max(setVal, d);
            }
            else {
                if (r <= mid) {
                    lChildren.set(l, r, d);
                } else if (l > mid) {
                    rChildren.set(l, r, d);
                } else {
                    lChildren.set(l, mid, d);
                    rChildren.set(mid + 1, r, d);
                }
                val = Math.min(lChildren.val, rChildren.val);
            }
        }

        void check() {
            int mid = (lt + rt) / 2;
            if (!hasLC) {
                lChildren = new Node(lt, mid);
                hasLC = true;
            }
            if (!hasRC) {
                rChildren = new Node(mid + 1, rt);
                hasRC = true;
            }
        }

        int getIdx() {
            if (lt == rt)
                return lt;
            check();
            if (setVal != -10000001)
                setVals();
            if (lChildren.val == val)
                return lChildren.getIdx();
            else
                return rChildren.getIdx();
        }

        void setVals() {
            lChildren.val = Math.max(lChildren.val, setVal);
            rChildren.val = Math.max(rChildren.val, setVal);
            lChildren.setVal = Math.max(lChildren.setVal, setVal);
            rChildren.setVal = Math.max(rChildren.setVal, setVal);
            setVal = -10000001;
        }
    }

    class FastScanner {
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
