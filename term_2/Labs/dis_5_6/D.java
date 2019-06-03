package solutions.dis_5;

import java.io.*;
import java.util.*;

public class D {
    final private String fileName = "problem4";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    final private long MOD = (long) 10e9 + 7;

    public Node[] nodes;
    public boolean uncor = false;
    public List<List<Integer>> trs;
    public List<List<Integer>> revTrs;

    int n;

    void solve(FastScanner reader) {
        n = reader.nextInt();
        int m = reader.nextInt();
        int k = reader.nextInt();
        int l = reader.nextInt();
        nodes = new Node[n];
        revTrs = new ArrayList<>();
        trs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < k; i++) {
            int now = reader.nextInt() - 1;
            nodes[now].fState = true;
        }
        for (int i = 0; i < n; i++) {
            revTrs.add(new ArrayList<>());
            trs.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = reader.nextInt() - 1;
            int to = reader.nextInt() - 1;
            nodes[from].trs.put(reader.next().charAt(0), to);
        }
        reader.printStr(getAns(l));
    }

    private long getAns(int l) {
        long[][] dp = new long[l][];
        dp[0] = new long[n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = nodes[i].fState ? 1 : 0;
        }
        for (int i = 1; i < l; i++) {
            dp[i] = new long[n];
            for (int j = 0; j < n; j++) {
                long curSum = 0;
                for (char symbol : nodes[j].trs.keySet()) {
                    curSum = (curSum + dp[i - 1][nodes[j].trs.get(symbol)]) % 1_000_000_007;
                }
                dp[i][j] = curSum;
            }
        }
        long answer = 0;
        for (char symbol : nodes[0].trs.keySet()) {
            answer = (answer + dp[l - 1][nodes[0].trs.get(symbol)]) % 1_000_000_007;
        }
        return answer;
    }

    static class Node {
        int num;
        Map<Character, Integer> trs = new TreeMap<>();
        boolean fState = false;

        Node(int num) {
            this.num = num;
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