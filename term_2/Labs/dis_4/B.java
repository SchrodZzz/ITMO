package solutions.dis_4;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class B {
    final private String fileName = "shooter";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int m = reader.nextInt();
        int k = reader.nextInt();
        double generalProbability = 0;
        double curProbability = 0;
        for (int i = 0; i < n; i++) {
            double curAccuracy = 1 - reader.nextDouble();
            generalProbability += fastPow(curAccuracy, m);
            if (i == k - 1)
                curProbability = fastPow(curAccuracy, m);
        }
        reader.printStr(curProbability / generalProbability);

    }

    double fastPow(double num, int pow) {
        return pow == 0 ? 1 : pow % 2 == 0 ? fastPow(num * num, pow / 2) : num * fastPow(num, pow - 1);
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
