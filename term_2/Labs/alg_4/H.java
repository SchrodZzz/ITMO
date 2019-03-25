package solutions.alg_4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class H {

    public static void main(String[] args) throws IOException {
        (new H()).solve();
    }

    List<Long> ans = new ArrayList<>();

    final String IN_FILE_NAME = "test.in";
    final String OUT_FILE_NAME = "test.out";

    private void solve()throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(IN_FILE_NAME))) {
            String[] vars = reader.readLine().split(" ");
            int n = Integer.parseInt(vars[0]);
            int m = Integer.parseInt(vars[1]);
            int size = 1;
            while (size < n) {
                size = size << 1;
            }
            Node root = new Node(1, size);
            int[] L = new int[m];
            int[] R = new int[m];
            long[] x = new long[m];
            for (int i = 0; i < m; ++i) {
                vars = reader.readLine().split(" ");
                L[i] = Integer.parseInt(vars[0]);
                R[i] = Integer.parseInt(vars[1]);
                x[i] = Integer.parseInt(vars[2]);
                root.set(L[i], R[i], x[i]);
            }
            root.setVals();
            boolean isConsistent = true;
            for (int i = 0; i < m; ++i) {
                if (root.get(L[i], R[i]) != x[i])
                    isConsistent = false;
            }
            try (PrintWriter out = new PrintWriter(new File(OUT_FILE_NAME))) {
                if (isConsistent) {
                    out.println("consistent");
                    for (int i = 0; i < n; i++) {
                        out.printf("%y2 ", ans.get(i));
                    }
                    out.println();
                } else {
                    out.println("inconsistent");
                }
            }
        }
    }

    class Node {
        int lt;
        int rt;
        long val = -10_000_000_000L;
        long setVal = -10_000_000_000L;
        boolean hasLC = false;
        boolean hasRC = false;
        Node lChildren;
        Node rChildren;

        Node(int a, int b) {
            this.lt = a;
            this.rt = b;
        }

        long get(int l, int r) {
            int mid = (lt + rt) / 2;
            if (l == lt && r == rt) {
                return val;
            }
            if (r <= mid) {
                return lChildren.get(l, r);
            } else if (l > mid) {
                return rChildren.get(l, r);
            } else {
                return Math.min(lChildren.get(l, mid), rChildren.get(mid + 1, r));
            }
        }

        void set(int l, int r, long d) {
            int mid = (lt + rt) / 2;
            if (l == lt && r == rt) {
                setVal = Math.max(setVal, d);
                val = Math.max(val, setVal);
                return;
            }
            check();
            if (r <= mid) {
                lChildren.set(l, r, d);
            } else if (l > mid) {
                rChildren.set(l, r, d);
            } else {
                lChildren.set(l, mid, d);
                rChildren.set(mid + 1, r, d);
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

        void setVals() {
            if (lt == rt) {
                if (val == -10_000_000_000L) {
                    val = (Integer.MAX_VALUE);
                }
                ans.add(val);
                return;
            }
            check();
            lChildren.val = Math.max(lChildren.val, setVal);
            rChildren.val = Math.max(rChildren.val, setVal);
            lChildren.setVal = Math.max(lChildren.setVal, setVal);
            rChildren.setVal = Math.max(rChildren.setVal, setVal);
            lChildren.setVals();
            rChildren.setVals();
            val = Math.min(lChildren.val, rChildren.val);
            setVal = -10_000_000_000L;
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
