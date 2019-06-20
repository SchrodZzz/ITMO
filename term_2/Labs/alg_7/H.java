package solutions.alg_7;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class H {
    final private String fileName = "vectors2";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    String[] ans = new String[3000];
    char[] curNum;
    int n, idx = 0;

    void solve(FastScanner reader) {
        n = reader.nextInt();
        curNum = new char[n];
        Arrays.fill(curNum, '0');
        Arrays.fill(ans, "+");
        gen(0);
        int ansCnt = 0;
        for(; !ans[ansCnt].equals("+"); ansCnt++);
        reader.printStr(ansCnt);
        for(int i = 0; i < ansCnt; i++){
            reader.printStr(ans[i]);
        }
    }

    void gen(int rank){
        if (rank == n) {
            ans[idx++] = new String(curNum);
        }
        else {
            curNum[rank] = '0';
            gen(rank + 1);
            if (rank == 0 || curNum[rank - 1] != '1') {
                curNum[rank] = '1';
                gen(rank + 1);
            }
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new H()).solution();
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
