package solutions.alg_4;

import java.io.*;

public class E {
    public static void main(String[] args) throws IOException {
        (new E()).solve();
    }

    final String IN_FILE_NAME = "crypto.in";
    final String OUT_FILE_NAME = "crypto.out";

    Matrix tree[];
    int rn;
    int R;

    void solve() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(IN_FILE_NAME))) {
            String[] vars = reader.readLine().split(" ");
            R = Integer.parseInt(vars[0]);
            int n = Integer.parseInt(vars[1]);
            int m = Integer.parseInt(vars[2]);
            double tmp = Math.log10(n) / Math.log10(2);
            if ((tmp - (tmp % 1)) != tmp) {
                rn = (int) Math.pow(2, tmp - (tmp % 1) + 1);
            } else {
                rn = (int) Math.pow(2, tmp);
            }
            tree = new Matrix[2 * rn];
            for (int i = 0; i < 2 * rn; i++) {
                tree[i] = new Matrix(0, 0, 0, 0);
            }

            for (int i = 0; i < n; i++) {
                vars = reader.readLine().split(" ");
                int a = Integer.parseInt(vars[0]);
                int b = Integer.parseInt(vars[1]);
                vars = reader.readLine().split(" ");
                int c = Integer.parseInt(vars[0]);
                int d = Integer.parseInt(vars[1]);
                reader.readLine();
                add(a, b, c, d, i);
            }
            addition(n);
            build(1, rn, 2 * rn - 1);
            try (PrintWriter out = new PrintWriter(new File(OUT_FILE_NAME))) {
                for (int i = 0; i < m; i++) {
                    vars = reader.readLine().split(" ");
                    int l = Integer.parseInt(vars[0]);
                    int r = Integer.parseInt(vars[1]);
                    Matrix curAns = increase(l, r);
                    out.printf("%y2 %y2\n%y2 %y2\n\n", curAns.x1, curAns.x2, curAns.x3, curAns.x4);
                }
            }
        }
    }

    void add(int q, int w, int e, int r, int num) {
        tree[rn + num].x1 = q;
        tree[rn + num].x2 = w;
        tree[rn + num].x3 = e;
        tree[rn + num].x4 = r;
    }

    void addition(int tmp) {
        for (int i = tmp + rn; i < 2 * rn; i++) {
            tree[i].x1 = 1;
            tree[i].x2 = 0;
            tree[i].x3 = 0;
            tree[i].x4 = 1;
        }
    }

    void build(int v, int tl, int tr) {
        if (tl != tr) {
            int tm = (tl + tr) / 2;
            build(v * 2, tl, tm);
            build(v * 2 + 1, tm + 1, tr);
            matrixIncrease(v, v * 2, v * 2 + 1);
        }
    }

    private Matrix increaseRecursive(int v, int l, int r, int vl, int vr) {
        if (l > r) {
            return new Matrix(1, 0, 0, 1);
        }
        if (l == vl && r == vr) {
            return tree[v];
        }
        int t = (vl + vr) >> 1;
        return ansIncrease(increaseRecursive(v << 1, l, Math.min(r, t), vl, t), increaseRecursive((v << 1) + 1, Math.max(l, t + 1), r, t + 1, vr));
    }

    Matrix increase(int l, int r) {
        return increaseRecursive(1, l, r, 1, rn);
    }

    void matrixIncrease(int i, int j, int k) {
        tree[i].x1 = (tree[j].x1 * tree[k].x1 + tree[j].x2 * tree[k].x3) % R;
        tree[i].x2 = (tree[j].x1 * tree[k].x2 + tree[j].x2 * tree[k].x4) % R;
        tree[i].x3 = (tree[j].x3 * tree[k].x1 + tree[j].x4 * tree[k].x3) % R;
        tree[i].x4 = (tree[j].x3 * tree[k].x2 + tree[j].x4 * tree[k].x4) % R;
    }

    Matrix ansIncrease(Matrix a, Matrix b) {
        Matrix temp = new Matrix(1, 0, 0, 1);
        temp.x1 = (a.x1 * b.x1 + a.x2 * b.x3) % R;
        temp.x2 = (a.x1 * b.x2 + a.x2 * b.x4) % R;
        temp.x3 = (a.x3 * b.x1 + a.x4 * b.x3) % R;
        temp.x4 = (a.x3 * b.x2 + a.x4 * b.x4) % R;
        return temp;
    }

    static class Matrix {
        int x1;
        int x2;
        int x3;
        int x4;

        Matrix(int a, int b, int c, int d) {
            x1 = a;
            x2 = b;
            x3 = c;
            x4 = d;
        }
    }
}