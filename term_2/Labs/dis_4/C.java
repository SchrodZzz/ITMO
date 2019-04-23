package solutions.dis_4;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class C {
    final private String fileName = "lottery";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int m = reader.nextInt();
        double ans = 0;
        m++;
        int[] a = new int[m];
        int[] b = new int[m];
        for (int i = 1; i < m; i++) {
            a[i] = reader.nextInt();
            b[i] = reader.nextInt();
        }
        double[] res = new double[m];
        res[0] = 1;
        b[0] = 0;
        for (int i = 1; i < m; i++) {
            res[i] = res[i - 1] / a[i];
        }
        for (int i = 1; i < m; i++) {
            ans += b[i - 1] * res[i - 1] * (1 - (1d / a[i]));
        }
        ans += res[m - 1] * b[m - 1];
        reader.printStr(n - ans);
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
