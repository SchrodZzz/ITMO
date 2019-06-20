package solutions.alg_7;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class E {
    final private String fileName = "apples";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int h = reader.nextInt();
        Node[] arr = new Node[n];
        for (int i = 1; i <= n; i++) {
            arr[i - 1] = new Node(reader.nextInt(), reader.nextInt(), i);
        }
        Arrays.sort(arr);
        for (Node cur : arr) {
            System.out.println(cur.incr - cur.decr);
        }
        boolean isBecomeNothing = false;
        for (int i = 0; i < n; i++) {
            if (h - arr[i].decr <= 0) {
                isBecomeNothing = true;
                break;
            }
            h += arr[i].incr - arr[i].decr;
        }
        if (isBecomeNothing)
            reader.printStr(-1);
        else {
            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < n; i++) {
                ans.append(arr[i].idx).append(' ');
            }
            reader.printStr(ans);
        }
    }

    private class Node implements Comparable<Node> {
        int decr;
        int incr;
        int idx;

        public Node(int decr, int incr, int idx) {
            this.decr = decr;
            this.incr = incr;
            this.idx = idx;
        }

        @Override
        public int compareTo(Node o) {
            int fst = incr - decr;
            int snd = o.incr - o.decr;
            if (fst < 0) {
                if (snd < 0) {
                    return Integer.compare(o.incr, incr);
                }
                return 1;
            }
            if (snd < 0) {
                return -1;
            }
            return Integer.compare(decr, o.decr);
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new E()).solution();
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
