package solutions.alg_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class A {
    public static void main(String[] args) {
        (new A()).solve();
    }
    long[] prefix;
    int n;
    void solve() {
        final int MOD_A = (1 << 16) - 1;
        final int MOD_B = (1 << 30) - 1;
        int m, z, t;
        long res = 0, x, y;

        FastScanner reader = new FastScanner();
        n = reader.nextInt();
        x = reader.nextInt();
        y = reader.nextInt();

        prefix = new long[n + 1];
        prefix[0] = reader.nextInt();
        prefix[n] = 0;

        m = reader.nextInt();
        z = reader.nextInt();
        t = reader.nextInt();
        int prev = reader.nextInt();

        long prv = prefix[0];
        for (int i = 1; i < n; i++) {
            long tmp = (x * prv + y) & MOD_A;
            prefix[i] = prefix[i - 1] + tmp;
            prv = tmp;
        }
        final int size = m << 1;
        int[] arr = new int[2];
        arr[0] = prev % n;
        for (int i = 1; i < size; i++) {
            int temp = (z * prev + t) & MOD_B;
            arr[i % 2] = temp % n;
            prev = temp;
            if (i % 2 == 1) {
                int a = arr[0];
                int b = arr[1];
                res += prefix[Math.max(a, b)] - prefix[(Math.min(a, b) > 0) ? Math.min(a, b) - 1 : n];
            }
        }
        if ((size - 1)% 2 == 0) {
            int a = arr[0];
            int b = arr[1];
            res += prefix[Math.max(a, b)] - prefix[(Math.min(a, b) > 0) ? Math.min(a, b) - 1 : n];
        }
        System.out.println(res);
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