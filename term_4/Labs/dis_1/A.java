import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class A {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    final private long mod = 998_244_353;

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        ArrayList<Long> p = new ArrayList<>();
        ArrayList<Long> q = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            p.add(sc.nextLong());
        }
        for (int i = 0; i <= m; i++) {
            q.add(sc.nextLong());
        }

        ArrayList<Long> vec = new ArrayList<>();
        long a, b, sum;

        int top = Math.max(n, m);
        for (int i = 0; i <= top; i++) {
            a = 0;
            b = 0;
            if (i < n + 1) {
                a = p.get(i);
            }
            if (i < m + 1) {
                b = q.get(i);
            }
            vec.add((a + b) % mod);
        }
        while (top > 0 && vec.get(top) == 0) {
            top--;
        }
        sc.printStr(top);
        sc.ln();
        for (int i = 0; i <= top; i++) {
            sc.printStr(vec.get(i));
        }
        sc.ln();
        sc.printStr(n + m);
        sc.ln();
        int all = n + m;
        for (int i = 0; i <= all; i++) {
            sum = 0;
            for (int j = 0; j <= i; j++) {
                a = 0;
                b = 0;
                if (j < n + 1) {
                    a = p.get(j);
                }
                if (i - j < m + 1) {
                    b = q.get(i - j);
                }
                sum += a * b;
                sum %= mod;
            }
            sc.printStr(sum);
        }
        sc.ln();
        vec = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            sum = 0;
            for (int j = 0; j < i; j++) {
                a = 0;
                if (i - j < m + 1) {
                    a = q.get(i - j);
                }
                sum += vec.get(j) * a;
                sum %= mod;
            }
            a = 0;
            if (i < n + 1) {
                a = p.get(i);
            }
            vec.add((a + mod - sum) % mod);
        }
        superWaiter();
        sc.printStr(arrayList2String(vec, ' '));
    }

    private void superWaiter() {
        long val = 0;
        while (val < Integer.MAX_VALUE) {
            val += 1;
        }
    }

    private static class Utils {

    }

    private class Pair<T extends Number> implements Comparable<Pair<T>> {
        T a;
        T b;

        Pair(T a, T b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Pair<T> e) {
            if (e.a instanceof Long) {
                long dif = this.a.longValue() - e.a.longValue();
                return (int) (dif == 0 ? this.b.longValue() - e.b.longValue() : dif);
            } else if (e.a instanceof Integer) {
                int dif = this.a.intValue() - e.a.intValue();
                return dif == 0 ? this.b.intValue() - e.b.intValue() : dif;
            } else {
                throw new IllegalArgumentException("oops");
            }
        }
    }

    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new A()).solution();
    }

    void solution() throws IOException {
        try (FastScanner reader = new FastScanner()) {
            solve(reader);
        }
    }

    private int bool2Int(boolean val) {
        return val ? 1 : 0;
    }

    private <T> String arrayList2String(final ArrayList<T> arr, final char separator) {
        StringBuilder res = new StringBuilder();
        for (T cur : arr) {
            res.append(cur);
            res.append(separator);
        }
        return res.toString();
    }

    private String array2String(final int[] arr) {
        StringBuilder res = new StringBuilder();
        for (int cur : arr) {
            res.append(cur);
            res.append(' ');
        }
        return res.toString();
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
                System.out.print(str + " ");
            } else {
                pw.print(str + " ");
            }
        }

        void ln() {
            if (isSysRead) {
                System.out.println();
            } else {
                pw.println();
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
