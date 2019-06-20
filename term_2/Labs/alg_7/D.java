package solutions.alg_7;

import java.io.*;
import java.util.*;

public class D {
    final private String fileName = "sequence";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        Node[] arr = new Node[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Node(reader.nextInt(), i);
        }
        Arrays.sort(arr);
        List<Node> ansArr = new ArrayList<>();
        int sumFst = 0;
        int sumSnd = 0;
        for (int i = 0; i < n; ++i) {
            if (sumFst <= sumSnd) {
                ansArr.add(arr[i]);
                sumFst += arr[i].a;
            } else {
                sumSnd += arr[i].a;
            }
        }
        if (sumFst != sumSnd) {
            reader.printStr(-1);
            return;
        }
        reader.printStr(ansArr.size());
        List<Integer> ans = new ArrayList<>();
        for (Node cur : ansArr) {
            ans.add(cur.b + 1);
        }
        Collections.sort(ans);
        StringBuilder ansStr = new StringBuilder();
        for (int cur : ans) {
            ansStr.append(cur).append(" ");
        }
        reader.printStr(ansStr);
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
            return Integer.compare(o.a, a);
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
