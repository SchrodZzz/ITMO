import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class C {
    final private String fileName = "dabavte swift!!!";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    final private static int mod = 104_857_601;

    void solve(FastScanner sc) {
        int k = sc.nextInt();

        ArrayList<Long> a = new ArrayList<>();
        ArrayList<Long> q = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            a.add(sc.nextLong());
        }
        q.add(1L);
        for (int i = 0; i < k; i++) {
            q.add(-sc.nextLong());
        }

        superWaiter();
        ArrayList<Long> p = Utils.getMultiplicationSeries(a, q, k);

        sc.printStr(p.size()-1);
        sc.ln();
        sc.printStr(arrayList2String(p, ' '));
        sc.ln();
        sc.printStr(q.size()-1);
        sc.ln();
        sc.printStr(arrayList2String(q, ' '));
        sc.ln();
    }

    private static class Utils {

        static ArrayList<Long> getMultiplicationSeries(ArrayList<Long> p, ArrayList<Long> q, int max) {
            ArrayList<Long> mul = new ArrayList<>();
            int size = Math.min(p.size() + q.size() + 3, max);
            for (int i = 0; i < size; i++) {
                long sum = 0;
                for (int j = 0; j <= i; j++) {
                    long a = j < p.size() ? p.get(j) : 0;
                    long b = i - j < q.size() ? q.get(i - j) : 0;
                    sum += a * b;
                }
                mul.add(sum);
            }
            while (--size > 0 && mul.get(size) == 0) {
                mul.remove(size);
            }
            return mul;
        }

        static long getCoef2(long n) {
            long a = 1;
            long b = 1;
            for (int i = 0; i < n; i++) {
                a *= (1 - 2 * i + mod);
                a %= mod;
                b *= (i + 1) * 2;
                b %= mod;
            }
            return (a * pow(b, mod - 2)) % mod;
        }

        static long pow(long x, int n) {
            if (n == 1) {
                return x;
            } else if (n % 2 == 0) {
                long val = pow(x, n / 2);
                return (val * val) % mod;
            }
            return (x * pow(x, n - 1)) % mod;
        }

        static long updateFac(long val, int i) {
            val *= i;
            val %= mod;
            return val;
        }

        static long updateCoef(long val) {
            val *= -1;
            val += mod;
            return val;
        }

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

    private void superWaiter() {
        long val = 0;
        while (val < Long.MAX_VALUE / (long) 10e9) {
            val += 1;
        }
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