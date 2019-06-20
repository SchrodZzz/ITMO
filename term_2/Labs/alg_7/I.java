package solutions.alg_7;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class I {
    final private String fileName = "sqroot";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int[][] matrix = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = reader.nextInt();
            }
        }
        int sum = 0;
        boolean isFlag = true;
        for (int i = 0; i < (1 << 16); ++i, isFlag = true) {
            for (int j = 0; isFlag && j < 4; ++j) {
                for (int k = 0; isFlag && k < 4; ++k, sum = 0) {
                    for (int l = 0; l < 4; ++l) {
                        sum += ((i >> (j * 4 + l)) & 1) * ((i >> (k + 4 * l)) & 1);
                    }
                    if ((sum & 1) != matrix[j][k]) {
                        isFlag = false;
                    }
                }
            }
            if (isFlag) {
                StringBuilder ans = new StringBuilder();
                for (int j = 0; j < 4; ++j) {
                    for (int k = 0; k < 4; ++k) {
                        ans.append((i >> (4 * j + k)) & 1).append(" ");
                    }
                    ans.append("\n");
                }
                reader.printStr(ans);
                return;
            }
        }
        reader.printStr("NO SOLUTION");
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new I()).solution();
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
