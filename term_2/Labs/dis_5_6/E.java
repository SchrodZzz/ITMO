package solutions.dis_5;

import java.io.*;
import java.util.*;

public class E {
    final private String fileName = "problem5";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        int m = reader.nextInt();
        int k = reader.nextInt();
        int l = reader.nextInt();
        ArrayList<ArrayList<ArrayList<Integer>>> transitions = new ArrayList<>();
        HashMap<BitSet, Integer> number = new HashMap<>();
        ArrayList<Boolean> terms = new ArrayList<>();
        ArrayList<Integer> fromS = new ArrayList<>();
        ArrayList<Integer> toS = new ArrayList<>();
        boolean[] tStates = new boolean[n + 1];
        BitSet set = new BitSet(n + 1);
        for (int i = 0; i < n + 1; i++) {
            transitions.add(new ArrayList<>());
            for (int j = 0; j < 26; j++) {
                transitions.get(i).add(new ArrayList<>());
            }
        }

        for (int i = 0; i < k; i++) {
            tStates[reader.nextInt()] = true;
        }

        for (int i = 0; i < m; i++) {
            int from = reader.nextInt();
            int to = reader.nextInt();
            transitions.get(from).get(reader.next().charAt(0) - 97).add(to);
        }
        terms.add(false);
        terms.add(tStates[1]);
        set.set(1);
        number.put(set, 1);
        LinkedList<BitSet> queue = new LinkedList<>();
        queue.add(set);
        int q = 1;
        while (!queue.isEmpty()) {
            BitSet curr = queue.poll();
            for (int i = 0; i < 26; i++) {
                BitSet can = new BitSet(n + 1);
                boolean flag = false;

                int j = 0;
                while (++j <= n) {
                    if (!curr.get(j)) {
                        continue;
                    }
                    int curSize = transitions.get(j).get(i).size();
                    for (int ii = 0; ii < curSize; ii++) {
                        int v = transitions.get(j).get(i).get(ii);
                        can.set(v);
                        if (tStates[v]) {
                            flag = true;
                        }
                    }
                }
                fromS.add(number.get(curr));
                if (!number.containsKey(can)) {
                    number.put(can, ++q);
                    terms.add(flag);
                    queue.add(can);
                }
                toS.add(number.get(can));
            }
        }

        int[] ans = new int[q + 1];
        for (int i = 0; i < q; i++) {
            if (terms.get(i + 1)) {
                ans[i + 1] = 1;
            }
        }
        for (int i = 0; i < l; i++) {
            int[] result = new int[q + 1];
            for (int j = 0; j < fromS.size(); j++) {
                result[fromS.get(j)] = (result[fromS.get(j)] + ans[toS.get(j)]) % 1000000007;
            }
            if (q + 1 >= 0) System.arraycopy(result, 0, ans, 0, q + 1);
        }
        reader.printStr(ans[number.get(set)]);
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
