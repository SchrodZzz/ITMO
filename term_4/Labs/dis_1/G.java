import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class G {
    final private String fileName = "dabavte swift!!!";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    final private static int mod = 998_244_353;

    static List<String> data;
    static int idx = 0;

    void solve(FastScanner sc) {
        String[] rawData = sc.nextLine().split("[^\\p{Alpha}]");
        data = Arrays.stream(rawData).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        ArrayList<Long> res = Utils.dfs();
        superWaiter();
        sc.printStr(arrayList2String(res, ' '));
    }

    private static class Utils {

        static ArrayList<Long> dfs() {
            ArrayList<Long> res = Utils.empty();
            ArrayList<Long> w1;
            ArrayList<Long> w2;
            switch (data.get(idx++)) {
                case "B": {
                    res.set(1, 1L);
                    break;
                }
                case "L": {
                    w1 = dfs();
                    res.set(0, 1L);
                    for (int i = 1; i < 7; i++) {
                        long sum = 0;
                        for (int j = 1; j <= i; j++) {
                            sum += w1.get(j) * res.get(i - j);
                        }
                        res.set(i, sum);
                    }
                    break;
                }
                case "S": {
                    w1 = dfs();
                    long[][] arr = new long[7][7];
                    for (int i = 0; i < 7; i++) {
                        arr[0][i] = 1;
                    }
                    res.set(0, 1L);
                    for (int i = 1; i < 7; i++) {
                        for (int j = 1; j < 7; j++) {
                            for (int k = 0; k <= i / j; k++) {
                                arr[i][j] += Utils.getCoef2(w1.get(j) + k - 1, k) * arr[i - k * j][j - 1];
                            }
                        }
                        res.set(i, arr[i][i]);
                    }
                    break;
                }
                case "P": {
                    w1 = dfs();
                    w2 = dfs();
                    for (int i = 0; i < 7; i++) {
                        long sum = 0;
                        for (int j = 0; j <= i; j++) {
                            sum += w1.get(j) * w2.get(i - j);
                        }
                        res.set(i, sum);
                    }
                }
                break;
            }
            return res;
        }

        static ArrayList<Long> empty() {
            ArrayList<Long> res = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                res.add(0L);
            }
            return res;
        }

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

        static long getCoef2(long n, long k) {
            long res = 1;
            for (long i = (n - k + 1); i <= n; i++) {
                res *= i;
            }
            for (int i = 2; i <= k; i++) {
                res /= i;
            }
            return res;
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

    private static class Node {
        Node l;
        Node r;
        String val;

        public Node(String val) {
            this.val = val;
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
        (new G()).solution();
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

    private String array2String(final String[] arr) {
        StringBuilder res = new StringBuilder();
        for (String cur : arr) {
            res.append(cur);
            res.append('_');
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