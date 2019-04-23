package solutions.dis_4;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class D {
    final private String fileName = "markchain";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = reader.nextDouble();
            }
        }
        double[][] result = data;
        while (true) {
            double[][] last = result;
            int len = result.length;
            double[][] tmpMatrix = new double[len][len];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    tmpMatrix[i][j] = 0;
                    for (int k = 0; k < len; k++) {
                        tmpMatrix[i][j] += result[i][k] * result[k][j];
                    }
                }
            }
            result = tmpMatrix;
            boolean con = false;
            for (int i = 0; i < n; i++) {
                if (Math.abs(result[i][i] - last[i][i]) > 1e-4) {
                    con = true;
                    break;
                }
            }
            if (!con) break;
        }

        for (int i = 0; i < n; i++) {
            reader.printStr(result[i][i]);
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new D()).solution();
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
