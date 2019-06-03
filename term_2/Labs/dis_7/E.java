package solutions.dis_6;

import java.io.*;
import java.util.*;

public class E {
    final private String fileName = "cf";
    final private boolean isTest = true;
    final private boolean isSysRead = false;

    int[][][] dp = new int[1001][][];
    Map<Integer, ArrayList<ArrayList<Node>>> dataH = new HashMap<>();

    String word;
    int s;

    void solve(FastScanner reader) {
        int n = reader.nextInt();
        s = reader.next().charAt(0) - 'A';
        for (int i = 0; i < 1001; i++) {
            dp[i] = new int[101][];
            for (int j = 0; j < 101; j++) {
                dp[i][j] = new int[101];
                for (int k = 0; k < 101; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            String a = reader.next();
            int v = a.charAt(0) - 'A';
            reader.next();
            String b = " " +reader.next();
            ArrayList<Node> to = new ArrayList<>();
            for (char u : b.toCharArray()) {
                if (u >= 'A' && u <= 'Z') {
                    to.add(new Node(false, u - 'A'));
                } else if (u >= 'a' && u <= 'z') {
                    to.add(new Node(true, u));
                }
            }
            ArrayList<ArrayList<Node>> tmp_ = new ArrayList<>();
            tmp_.add(to);
            dataH.put(v, tmp_);
        }

        removeBigs();
        remEps();

        word = reader.next();
        reader.printStr(getAns(s, 0, word.length()) ? "yes" : "no");
    }

    void removeBigs() {
        Map<Integer, ArrayList<ArrayList<Node>>> temp_ = new HashMap<>();
        int cnt = 26;
        for (Map.Entry<Integer, ArrayList<ArrayList<Node>>> v : dataH.entrySet()) {
            int i = v.getKey();
            System.out.println(i);
            for (ArrayList<Node> u : dataH.get(i)) {
                if (u.size() <= 2) {
                    ArrayList<ArrayList<Node>> tmp = new ArrayList<>();
                    tmp.add(u);
                    temp_.put(i, tmp);
                } else {
                    int pr = i;
                    for (int j = 0; j < u.size() - 2; ++j) {
                        ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
                        ArrayList<Node> tmp = new ArrayList<>();
                        tmp.add(u.get(j));
                        tmp.add(new Node(false, ++cnt));
                        tmp__.add(tmp);
                        temp_.put(pr, tmp__);
                        pr = cnt;
                    }
                    ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
                    ArrayList<Node> tmp = new ArrayList<>();
                    tmp.add(u.get(u.size() - 2));
                    tmp.add(u.get(u.size() - 1));
                    tmp__.add(tmp);
                    temp_.put(pr, tmp__);
                }
            }
        }
        dataH = temp_;
    }

    Set<Integer> del = new HashSet<>();

    void findEps() {
        int sz = -1;
        while (del.size() > sz) {
            sz = del.size();
            for (Map.Entry<Integer, ArrayList<ArrayList<Node>>> v : dataH.entrySet()) {
                int i = v.getKey();
                boolean f = false;
                for (ArrayList<Node> u : dataH.get(i)) {
                    boolean f1 = true;
                    for (Node to : u) {
                        if (to.t) {
                            f1 = false;
                        } else {
                            f1 &= del.contains(to.to);
                        }
                    }
                    f |= f1;
                }
                if (f) {
                    del.add(i);
                }
            }
        }
    }

    void remEps() {
        findEps();
        Map<Integer, ArrayList<ArrayList<Node>>> temp_ = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<ArrayList<Node>>> v : dataH.entrySet()) {
            int i = v.getKey();
            for (ArrayList<Node> u : dataH.get(i)) {
                if (u.size() == 1) {
                    ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
                    tmp__.add(u);
                    temp_.put(i, tmp__);
                } else if (u.size() == 2) {
                    if (del.contains(u.get(0).to) && del.contains(u.get(1).to)) {
                        ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
                        tmp__.add(u);
                        temp_.put(i, tmp__);
                        tmp__= new ArrayList<>();
                        ArrayList<Node> tmp = new ArrayList<>();
                        tmp.add(u.get(0));
                        tmp__.add(tmp);
                        temp_.put(i, tmp__);
                        tmp__= new ArrayList<>();
                        tmp = new ArrayList<>();
                        tmp.add(u.get(1));
                        tmp__.add(tmp);
                        temp_.put(i, tmp__);
                    } else if (del.contains(u.get(0).to)) {
                        ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
                        ArrayList<Node> tmp = new ArrayList<>();
                        tmp.add(u.get(1));
                        tmp__.add(tmp);
                        temp_.put(i, tmp__);
                        tmp__= new ArrayList<>();
                        tmp = new ArrayList<>();
                        tmp.add(u.get(0));
                        tmp.add(u.get(1));
                        tmp__.add(tmp);
                        temp_.put(i, tmp__);
                    } else if (del.contains(u.get(1).to)) {
                        ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
                        ArrayList<Node> tmp = new ArrayList<>();
                        tmp.add(u.get(0));
                        tmp__.add(tmp);
                        temp_.put(i, tmp__);
                        tmp__= new ArrayList<>();
                        tmp = new ArrayList<>();
                        tmp.add(u.get(0));
                        tmp.add(u.get(1));
                        tmp__.add(tmp);
                        temp_.put(i, tmp__);
                    } else {
                        ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
                        tmp__.add(u);
                        temp_.put(i, tmp__);
                    }
                }
            }
        }
        if (del.contains(s)) {
            ArrayList<ArrayList<Node>> tmp__= new ArrayList<>();
            ArrayList<Node> tmp = new ArrayList<>();
            tmp.add(new Node(false, s));
            tmp__.add(tmp);
            temp_.put(1000, tmp__);
            tmp = new ArrayList<>();
            tmp__= new ArrayList<>();
            tmp__.add(tmp);
            temp_.put(1000, tmp__);
            s = 1000;
        }
        dataH = temp_;
    }

    boolean getAns(int v, int l, int r) {
        if (dp[v][l][r] == -2 || l >= r) {
            return false;
        }
        if (dp[v][l][r] == -1) {
            int res = 0;
            dp[v][l][r] = -2;
            for (ArrayList<Node> u : dataH.get(v)){
                if (u.size() == 1) {
                    if (u.get(0).t) {
                        if (l + 1 == r && word.charAt(l) == u.get(0).to) {
                            res = 1;
                        }
                    } else {
                        res |= getAns(u.get(0).to, l, r) ? 1 : 0;
                    }
                } else if (u.size() == 2) {
                    if (u.get(0).t && u.get(1).t) {
                        if (l + 2 == r && word.charAt(l) == u.get(0).to && word.charAt(l + 1) == u.get(1).to) {
                            res = 1;
                        }
                    } else if (u.get(0).t) {
                        if (word.charAt(l) == u.get(0).to) {
                            res |= getAns(u.get(1).to, l + 1, r) ? 1 : 0;
                        }
                    } else if (u.get(1).t) {
                        if (word.charAt(r - 1) == u.get(1).to) {
                            res |= getAns(u.get(0).to, l, r - 1) ? 1 : 0;
                        }
                    } else {
                        for (int i = l + 1; i < r; ++i) {
                            res |= (getAns(u.get(0).to, l, i) ? 1 : 0) * (getAns(u.get(1).to, i, r) ? 1 : 0);
                        }
                    }
                }
            }
            dp[v][l][r] = res;
        }
        return dp[v][l][r] == 1;
    }

    class Node {
        boolean t;
        int to;

        public Node(boolean t, int to) {
            this.t = t;
            this.to = to;
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
