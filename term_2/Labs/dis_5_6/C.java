package solutions.dis_5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class C {
    final private String fileName = "problem3";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    final private long MOD = (long) 10e9 + 7;

    public Node[] nodes;
    public boolean uncor = false;
    public List<List<Integer>> trs;
    public List<List<Integer>> revTrs;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int m = reader.nextInt();
        int k = reader.nextInt();
        nodes = new Node[n];
        revTrs = new ArrayList<>();
        trs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < k; i++) {
            int now = reader.nextInt() - 1;
            nodes[now].ending = true;
            nodes[now].value = 1;
        }
        for (int i = 0; i < n; i++) {
            revTrs.add(new ArrayList<>());
            trs.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = reader.nextInt() - 1;
            int to = reader.nextInt() - 1;
            trs.get(from).add(to);
            revTrs.get(to).add(from);
            reader.next();
        }
        findI(0);
        for (int i = 0; i < n; i++) {
            if (nodes[i].ending) {
                findT(i);
            }
        }
        dfs(0);
        reader.printStr(uncor ? -1: nodes[0].value);
    }

    public void dfs(int v) {
        if (!nodes[v].needTerminal || !nodes[v].needZero) {
            return;
        }
        nodes[v].color = 1;
        for (int i = 0; i < trs.get(v).size(); i++) {
            if (nodes[trs.get(v).get(i)].color == 1) {
                uncor = true;
                return;
            } else {
                if (nodes[trs.get(v).get(i)].color == 0) {
                    dfs(trs.get(v).get(i));
                }
                nodes[v].value = (nodes[v].value + nodes[trs.get(v).get(i)].value) % MOD;
            }
        }
        nodes[v].color = 2;
    }

    private void findI(int v) {
        nodes[v].needZero = true;
        for (int i = 0; i < trs.get(v).size(); i++) {
            if (!nodes[trs.get(v).get(i)].needZero) {
                findI(trs.get(v).get(i));
            }
        }
    }

    private void findT(int v) {
        nodes[v].needTerminal = true;
        for (int i = 0; i < revTrs.get(v).size(); i++) {
            if (!nodes[revTrs.get(v).get(i)].needTerminal) {
                findT(revTrs.get(v).get(i));
            }
        }
    }

    public class Node {
        boolean ending = false ;
        boolean needTerminal = false;
        boolean needZero = false;
        int color = 0;
        int index;
        long value = 0;

        public Node(int index) {
            this.index = index;
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