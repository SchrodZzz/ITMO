package solutions.alg_7;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class L {
    final private String fileName = "set";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        CustomSet set = new CustomSet();
        String cmd = reader.nextLine();
        while (cmd != null) {
            int num = Integer.parseInt(cmd.split(" ")[1]) + (int) 1e9;
            switch (cmd.split(" ")[0]) {
                case "insert": {
                    set.add(num);
                    break;
                }
                case "delete": {
                    set.remove(num);
                    break;
                }
                case "exists": {
                    reader.printStr(set.contains(num) ? "true" : "false");
                    break;
                }
            }
            cmd = reader.nextLine();
        }
    }

    private class CustomSet {

        Node[] data;

        private class Node {
            int val;
            Node next;

            Node(int val) {
                this.val = val;
                this.next = null;
            }
        }

        CustomSet() {
            this.data = new Node[10007];
        }

        void add(int x) {
            int idx = x % 10007;
            Node cur = data[idx];
            if (cur == null) {
                data[idx] = new Node(x);
                return;
            }
            while (cur.next != null && cur.val != x) {
                cur = cur.next;
            }
            if (cur.val == x) {
                return;
            }
            cur.next = new Node(x);
        }

        void remove(int x) {
            int idx = x % 10007;
            Node cur = data[idx];
            Node prv = null;
            while (cur != null && cur.val != x) {
                prv = cur;
                cur = cur.next;
            }
            if (cur == null) {
                return;
            }
            if (prv == null) {
                data[idx] = cur.next;
                return;
            }
            prv.next = cur.next;
        }

        boolean contains(int x) {
            int idx = x % 10007;
            Node cur = data[idx];
            while (cur != null && cur.val != x) {
                cur = cur.next;
            }
            return cur != null;
        }
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new L()).solution();
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