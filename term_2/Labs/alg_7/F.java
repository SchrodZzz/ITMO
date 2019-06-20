package solutions.alg_7;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class F {
    final private String fileName = "john";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    Node[] arr;
    long ans = 0;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        arr = new Node[n];
        for (int i = 0; i < n; i++){
            arr[i] = new Node(reader.nextInt(), reader.nextInt());
        }
        Arrays.sort(arr);
        helloFromTreap(0, n-1);
        reader.printStr(ans);
    }

    void helloFromTreap(int l, int r) {
        if (l >= r) {
            return;
        }
        helloFromTreap(l, (l + r) / 2);
        helloFromTreap((l + r) / 2 + 1, r);
        Node[] tmp = new Node[r - l + 1];
        if (r + 1 - l >= 0) System.arraycopy(arr, l, tmp, 0, r + 1 - l);
        int idxA = 0;
        int idxB = (l + r) / 2 + 1 - l;
        for (int i = l; i <= r; ++i) {
            int idx = (l + r) / 2 + 1 - l;
            if (idxB > r - l || (idxA < idx && tmp[idxA].b <= tmp[idxB].b)) {
                arr[i] = tmp[idxA++];
            }
            else {
                arr[i] = tmp[idxB++];
                if (idxA < idx) {
                    ans += idx - idxA;
                }
            }
        }
    }

    private class Node implements Comparable<Node> {
        int a;
        int b;

        Node(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Node o) {
            return (a != o.a) ? Integer.compare(a, o.a) : Integer.compare(b, o.b);
        }
    }

    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new F()).solution();
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
