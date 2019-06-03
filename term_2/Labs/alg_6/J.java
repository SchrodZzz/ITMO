package solutions.alg_6;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class J {
    final private String fileName = "exam";
    final private boolean isTest = true;
    final private boolean isSysRead = true;

    int[] vecS;
    int[] befC;
    ArrayList<Integer>[] egs;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        n++;
        vecS = new int[n];
        befC = new int[n];
        egs = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            egs[i] = new ArrayList<>();
        }
        n--;
        for (int i = 1; i < n; ++i) {
            int u = reader.nextInt() - 1;
            int v = reader.nextInt() - 1;
            egs[u].add(v);
            egs[v].add(u);
        }
        make(0, -1);
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            ans.append(befC[i] + 1).append(' ');
        }
        reader.printStr(ans);
    }

    int dfs(int p, int v) {
        int res = 1;
        for (int i = 0; i < egs[v].size(); ++i) {
            if (egs[v].get(i) != p) {
                res += dfs(v, egs[v].get(i));
            }
        }
        vecS[v] = res;
        return res;
    }

    void make(int v, int prev) {
        int cur = search(v);
        befC[cur] = prev;
        ArrayList<Integer> nextStep = new ArrayList<>();
        nextStep.addAll(egs[cur]);
        egs[cur].clear();
        for (int i : nextStep) {
            int size = egs[i].size();
            for (int j = 0; j < size; j++) {
                if (egs[i].get(j) == cur) {
                    egs[i].remove(j);
                    break;
                }
            }
            make(i, cur);
        }
    }

    int search(int v) {
        dfs(v, v);
        int size = vecS[v];
        boolean end = false;
        int p = v;
        while (!end) {
            end = true;
            for (int i = 0; i < egs[v].size(); ++i) {
                if (egs[v].get(i) != p && vecS[egs[v].get(i)] > size / 2) {
                    end = false;
                    p = v;
                    v = egs[v].get(i);
                    break;
                }
            }
        }
        return v;
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new J()).solution();
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
