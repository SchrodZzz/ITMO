package solutions.alg_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        (new B()).solve();
    }

    void solve() {
        FastScanner reader = new FastScanner();
        int n = reader.nextInt();
        int N = 4 * n;
        long[] data = new long[(N << 1) - 1];
        for (int i = N - 1; i < N - 1 + n; i++) {
            data[i] = reader.nextInt();
        }
        for (int i = N - 2; i >= 0; i--) {
            data[i] = data[(i << 1) + 1] + data[(i << 1) + 2];
        }

        String cmd;
        while ((cmd = reader.nextLine()) != null) {
            String[] sepCmd = cmd.split(" ");
            switch (sepCmd[0]) {
                case "setVal":
                    int idx = Integer.parseInt(sepCmd[1]) + N - 2;
                    long val = Integer.parseInt(sepCmd[2]);
                    long d = val - data[idx];
                    data[idx] = val;
                    while (idx != 0) {
                        data[idx - 1 >> 1] += d;
                        idx = idx - 1 >> 1;
                    }
                    break;
                case "sum":
                    int l = Integer.parseInt(sepCmd[1]) + N - 2;
                    int r = Integer.parseInt(sepCmd[2]) + N - 2;
                    if (r - l > 1) {
                        long sum = 0;
                        sum += (l % 2 == 0) ? data[l++] : 0;
                        sum +=  (r % 2 == 1) ? data[r--] : 0;
                        while (((r - 1) >> 1) - ((l - 1) >> 1) > 1) {
                            sum += (((l - 1) >> 1) % 2 == 0) ? data[(l - 1) >> 1] : 0;
                            l = ((l - 1) >> 1) + 1;
                            sum += (((r - 1) >> 1) % 2 == 1) ? data[(r - 1) >> 1] : 0;
                            r = ((r - 1) >> 1) - 1;
                        }
                        if (((r - 1) >> 1) - ((l - 1) >> 1) == 0) {
                            sum += data[(r - 1) >> 1];
                        } else {
                            sum += data[(r - 1) >> 1] + data[(l - 1) >> 1];
                        }
                        System.out.println(sum);
                    } else if (r - l == 0) {
                        System.out.println(data[l]);
                    } else {
                        System.out.println(data[l] + data[r]);
                    }
                    break;
                    default:
                        System.out.println("incorrect command");

            }
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
