import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;
import java.math.BigInteger;

public class K {
    final private String fileName = "kek";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    final private long mod = 998_244_353;

    static ArrayList<ArrayList<Edge>> edges;

    void solve(FastScanner sc) {
        int n = sc.nextInt();
        edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            Utils.addEdge(sc.nextInt() - 1, sc.nextInt() - 1, 1 - sc.nextInt() * 2);
        }
        Rational ans = Utils.dfs(0, -1);
        sc.printStr(ans.numerator + " " + ans.denominator);
    }

    private void superWaiter() {
        long val = 0;
        while (val < Integer.MAX_VALUE) {
            val += 1;
        }
    }

    private static class Utils {
        static void addEdge(int u, int v, int color) {
            edges.get(u).add(new Edge(u, v, color));
            edges.get(v).add(new Edge(v, u, color));
        }
        static Rational dfs(int v, int p) {
            Rational res = new Rational(BigInteger.ZERO, BigInteger.ONE);
            for (Edge e : edges.get(v)) {
                if (e.v != p) {
                    Rational got = dfs(e.v, v);
                    if (got.numerator.signum() == e.color || got.numerator.signum() == 0) {
                        res = res.add(got.add(new Rational(BigInteger.valueOf(e.color), BigInteger.ONE)));
                    } else {
                        BigInteger coef = got.numerator.abs().divide(got.denominator).add(BigInteger.ONE);
                        BigInteger ap = got.numerator.abs().multiply(coef).add(got.denominator.subtract(got.numerator.abs()).multiply(coef.add(BigInteger.ONE)));
                        if (ap.signum() == got.numerator.signum()) {
                            ap = ap.negate();
                        }
                        res = res.add(new Rational(ap, got.denominator.shiftLeft(coef.intValue())));
                    }
                }
            }
            return res;
        }
    }

    private static class Rational {
        private final BigInteger numerator, denominator;

        public Rational(BigInteger numerator, BigInteger denominator) {
            BigInteger gcd = numerator.gcd(denominator);
            numerator = numerator.divide(gcd);
            denominator = denominator.divide(gcd);
            if (denominator.compareTo(BigInteger.ZERO) < 0) {
                numerator = numerator.negate();
                denominator = denominator.negate();
            }

            this.numerator = numerator;
            this.denominator = denominator;
        }

        public BigInteger ceil() {
            if (numerator.remainder(denominator).equals(BigInteger.ZERO)) {
                return numerator.divide(denominator);
            } else {
                return numerator.divide(denominator).add(BigInteger.ONE);
            }
        }

        public Rational add(Rational rational) {
            BigInteger numerator = this.numerator.multiply(rational.denominator).add(this.denominator.multiply(rational.numerator));
            BigInteger denominator = this.denominator.multiply(rational.denominator);
            return new Rational(numerator, denominator);
        }
    }

    private static class Edge {
        int u;
        int v;
        int color;

        Edge(int u, int v, int color) {
            this.u = u;
            this.v = v;
            this.color = color;
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

    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new K()).solution();
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
