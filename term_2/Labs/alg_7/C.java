package solutions.alg_7;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class C {
    final private String fileName = "printing";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    Node[] prs = new Node[7];

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        for (int i = 0; i < 7; i++) {
            double curPow = Math.pow(10, i);
            int fullPrice = reader.nextInt();
            prs[i] = new Node(fullPrice / curPow, (int) curPow, fullPrice);
        }
        Arrays.sort(prs);
        reader.printStr(getAns(0, n));
    }

    int getAns(int idx, int n) {
        return (n != 0 ?
                (int) (
                        (n / prs[idx].amount) * prs[idx].fullPrice +
                                Integer.min(prs[idx].fullPrice, getAns(idx + 1, n % prs[idx].amount))
                )
                : 0);
    }

    private class Node implements Comparable<Node> {
        double price;
        int amount;
        int fullPrice;

        Node(double price, int amount, int fullPrice) {
            this.price = price;
            this.amount = amount;
            this.fullPrice = fullPrice;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(price, o.price);
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
