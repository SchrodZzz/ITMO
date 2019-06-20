package solutions.alg_7;

import java.io.*;
import java.util.*;

public class K {
    final private String fileName = "dowry";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    long cur;
    int lB, rB;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        long L = reader.nextLong();
        long R = reader.nextLong();
        Node[] a = new Node[256000];
        Node[] b = new Node[256000];
        int[][] dp = new int[256000][24];
        Node temp = new Node(0, 0, 0);
        long[] w = new long[n + 1];
        long[] v = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            w[i] = reader.nextLong();
            v[i] = reader.nextLong();
        }
        int aSize = n >> 1;
        int bSize = n - aSize;
        int aShift = 1 << aSize;
        int bShift = 1 << bSize;
        for (int i = 0; i < aShift; i++) {
            long curV = 0;
            long curW = 0;
            for (int j = 0; j < aSize; ++j) {
                if ((i & (1 << j)) != 0) {
                    curV += v[j];
                    curW += w[j];
                }
            }
            a[i] = new Node(curW, curV, i);
        }
        for (int i = 0; i < bShift; i++) {
            long curV = 0;
            long curW = 0;
            for (int j = 0; j < bSize; ++j) {
                if ((i & (1 << j)) != 0) {
                    curV += v[j + aSize];
                    curW += w[j + aSize];
                }
            }
            b[i] = new Node(curW, curV, i);
        }
        Arrays.sort(a, 0, aShift);
        Arrays.sort(b, 0, bShift);
        for (int i = bShift - 1; i > -1; --i) {
            dp[i][0] = i;
            for (int j = 0; j < 16; ++j) {
                if (b[dp[i][j]].b >= b[dp[i + (1 << j)][j]].b) {
                    dp[i][j + 1] = dp[i][j];
                } else {
                    dp[i][j + 1] = dp[i + (1 << j)][j];
                }
            }
        }
        for (int i = 0; i < aShift; ++i) {
            temp.a = L - a[i].a;
            int p = (int) lowerBound(b, 0, bShift, temp.a);
            if (p < bShift) {
                if (a[i].a + b[p].a > R) {
                    break;
                }
                int gr = dp[p][0];
                for (int car = 0, j = 16; j > -1; --j) {
                    if ((p + car + (1 << j) < bShift) && (a[i].a + b[p + car + (1 << j)].a <= R)) {
                        if (b[dp[p + car + 1][j]].b > b[gr].b) {
                            gr = dp[p + car + 1][j];
                        }
                        car += (1 << j);
                    }
                }
                if (cur < b[gr].b + a[i].b) {
                    cur = b[gr].b + a[i].b;
                    lB = a[i].idx;
                    rB = b[gr].idx;
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < aSize; ++i)
            if ((lB & (1 << i)) != 0) {
                res.add(i + 1);
            }
        for (int i = 0; i < bSize; ++i)
            if ((rB & (1 << i)) != 0) {
                res.add(i + 1 + aSize);
            }
        reader.printStr(res.size());
        StringBuilder ans = new StringBuilder();
        for (Integer cur : res) {
            ans.append(cur).append(' ');
        }
        reader.printStr(ans);
    }

    private long lowerBound(Node[] arr, int l, int r, long x) {
        while (l < r) {
            int m = l + (r - l) / 2;
            if (x > arr[m].a)
                l = m + 1;
            else
                r = m;
        }
        return l;
    }

    private class Node implements Comparable<Node> {
        long a;
        long b;
        int idx;

        Node(long a, long b, int idx) {
            this.a = a;
            this.b = b;
            this.idx = idx;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(a, o.a);
        }
    }

    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new K()).solution();
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
