package solutions.alg_7;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class J {
    final private String fileName = "jurassic";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int[] masks = new int[n];
        for (int i = 0; i < n; ++i) {
            String curMark = reader.nextLine();
            int curMask = 0;
            for (char j : curMark.toCharArray())
                curMask += 1 << (j - 'A');
            masks[i] = curMask;
        }
        int maxN = 0;
        int maxMask = 0;
        for (int i = 1; i < 1 << n; ++i) {
            int j = i;
            int curMask = 0;
            int cnt = 0;
            for (int count = 0; j > 0; ++count, j >>= 1)
                if ((j % 2) == 1) {
                    ++cnt;
                    curMask ^= masks[count];
                }
            if (curMask == 0 && cnt > maxN) {
                maxN = cnt;
                maxMask = i;
            }
        }
        reader.printStr(maxN);
        StringBuilder ans = new StringBuilder();
        for (int i = 1; maxMask > 0; ++i, maxMask >>= 1) {
            if (maxMask % 2 == 1) {
                ans.append(i).append(' ');
            }
        }
        reader.printStr(ans);
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new J()).solution();
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
