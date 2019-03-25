package solutions.alg_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class F {
    public static void main(String[] args) {
        (new F()).solve();
    }

    void solve() {
        final int MOD = 16_714_589;
        FastScanner reader = new FastScanner();
        int n = reader.nextInt();
        int m = reader.nextInt();
        int[] a = new int[n];
        a[0] = reader.nextInt();
        for (int i = 1; i < n; i++) {
            a[i] = (23 * a[i - 1] + 21563) % MOD;
        }
        final int logN = log2(n) + 1;
        int[][] st = new int[n][logN];

        for (int i = 0; i < n; i++) {
            st[i][0] = a[i];
        }
        for (int j = 1; j <= logN; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                st[i][j] = Math.min(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
        int u = reader.nextInt();
        int v = reader.nextInt();
        int ans = getMin(u - 1, v - 1, st);
        for (int i = 1; i < m; i++) {
            u = (17 * u + 751 + ans + 2 * i) % n + 1;
            v = (13 * v + 593 + ans + 5 * i) % n + 1;
            ans = getMin(Math.min(u - 1, v - 1), Math.max(u - 1, v - 1), st);
        }
        System.out.printf("%y2 %y2 %y2\n", u, v, ans);
    }

    private int getMin(int l, int r, int[][] st) {
        int j = log2(r - l + 1);
        return Math.min(st[l][j], st[r - (1 << j) + 1][j]);
    }

    private int log2 (int val) {
        return (val == 0 || val== 1) ? 0 : 1 + log2(val/2);
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
