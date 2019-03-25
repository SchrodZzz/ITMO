package solutions.alg_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class L {
    public static void main(String[] args) {
        (new L()).solve();
    }

    void solve() {
        FastScanner reader = new FastScanner();
        int n = reader.nextInt();
        BinaryIndexTree3D tree3d = new BinaryIndexTree3D(n);
        int cmd = reader.nextInt();
        while (cmd != 3) {
            switch (cmd) {
                case 1: {
                    int x = reader.nextInt();
                    int y = reader.nextInt();
                    int z = reader.nextInt();
                    long val = reader.nextLong();
                    tree3d.update(x, y, z, val);
                    break;
                }
                case 2: {
                    int x1 = reader.nextInt();
                    int y1 = reader.nextInt();
                    int z1 = reader.nextInt();
                    int x2 = reader.nextInt();
                    int y2 = reader.nextInt();
                    int z2 = reader.nextInt();
                    System.out.println(tree3d.getSum(x1, y1, z1, x2, y2, z2));
                    break;
                }
            }
            cmd = reader.nextInt();
        }
    }

    private class BinaryIndexTree3D {
        final long[][][] tree;

        public BinaryIndexTree3D(int treeSize) {
            tree = new long[treeSize][treeSize][treeSize];
        }

        void update(int x_start, int y_start, int z_start, long val) {
            for (int x = x_start; x < tree.length; x = getIdx(x, false)) {
                for (int y = y_start; y < tree[x].length; y = getIdx(y, false)) {
                    for (int z = z_start; z < tree[x][y].length; z = getIdx(z, false)) {
                        tree[x][y][z] += val;
                    }
                }
            }
        }

        long getSum(int x1, int y1, int z1, int x2, int y2, int z2) {
            return sumHelper(x2, y2, z2)
                    + sumHelper(x1 - 1, y1 - 1, z2)
                    + sumHelper(x1 - 1, y2, z1 - 1)
                    + sumHelper(x2, y1 - 1, z1 - 1)
                    - sumHelper(x1 - 1, y2, z2)
                    - sumHelper(x2, y1 - 1, z2)
                    - sumHelper(x2, y2, z1 - 1)
                    - sumHelper(x1 - 1, y1 - 1, z1 - 1);

        }

        long sumHelper(int xS, int yS, int zS) {
            long result = 0;
            for (int x = xS; x >= 0; x = (getIdx(x, true)) - 1) {
                for (int y = yS; y >= 0; y = (getIdx(y, true)) - 1) {
                    for (int z = zS; z >= 0; z = (getIdx(z, true)) - 1) {
                        result += tree[x][y][z];
                    }
                }
            }
            return result;
        }

        int getIdx(int idx, boolean isPrv) {
            return isPrv ? idx & (idx + 1) : idx | (idx + 1);
        }
    }

    private class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
    }
}
