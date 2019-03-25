package solutions.alg_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class K {
    public static void main(String[] args) throws Exception {
        (new K()).solve();
    }

    final String IN_FILE_NAME = "parking.in";
    final String OUT_FILE_NAME = "parking.out";

    boolean tree[];
    int n;
    int rn;

    void solve() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(IN_FILE_NAME))) {
            String[] vars = reader.readLine().split(" ");
            n = Integer.parseInt(vars[0]);
            int m = Integer.parseInt(vars[1]);
            double tmp = Math.log10(n) / Math.log10(2);
            if ((tmp - (tmp % 1)) != tmp) {
                rn = (int) Math.pow(2, tmp - (tmp % 1) + 1);
            } else {
                rn = (int) Math.pow(2, tmp);
            }
            tree = new boolean[rn << 1];
            for (int i = n + rn; i < (rn << 1); i++) {
                tree[i] = true;
            }
            build(1, rn, (rn << 1) - 1);
            try (PrintWriter out = new PrintWriter(new File(OUT_FILE_NAME))) {
                for (int i = 0; i < m; i++) {
                    String[] cmd = reader.readLine().split(" ");
                    switch (cmd[0]) {
                        case "enter":
                            int place = (Integer.parseInt(cmd[1]));
                            out.println(add(place) + 1 - rn);
                            break;
                        case "exit":
                            set(rn + Integer.parseInt(cmd[1]) - 1, false);
                    }
                }
            }
        }
    }

    void build(int v, int tl, int tr) {
        if (tl != tr) {
            int tm = (tl + tr) / 2;
            build(v << 1, tl, tm);
            build((v << 1) + 1, tm + 1, tr);

            tree[v] = tree[v << 1] && tree[(v << 1) + 1];
        }
    }

    int down(int v) {
        if ((v << 1) < (rn << 1)) {
            if (!tree[v << 1]) {
                return down(v << 1);
            } else {
                return down((v << 1) + 1);
            }
        } else {
            return v;
        }
    }

    int add(int place) {
        if (!tree[place + rn - 1]) {
            set(place + rn - 1, true);
            return place + rn - 1;
        }
        int t = find(1, rn, (rn << 1) - 1, place + rn - 1);
        if (t != 0) {
            set(t, true);
            return t;
        } else {
            int ans = down(1);
            set(ans, true);
            return ans;
        }
    }

    int find(int v, int tl, int tr, int value) {
        if (tl != tr) {
            int tm = (tl + tr) / 2;
            if (tm > value && !tree[v << 1]) {
                int a = find(v << 1, tl, tm, value);
                if (a != 0)
                    return a;
            }
            if (tr > value && !tree[(v << 1) + 1]) {
                int b = find((v << 1) + 1, tm + 1, tr, value);
                if (b != 0) {
                    return b;
                }
            }
            return 0;
        } else {
            return v;
        }
    }

    void set(int i, boolean value) {
        tree[i] = value;
        while ((i /= 2) != 0) {
            tree[i] = (tree[i << 1] && tree[(i << 1) + 1]);
        }
    }
}
