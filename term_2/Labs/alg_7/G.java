package solutions.alg_7;

import java.io.*;
import java.util.*;

public class G {
    final private String fileName = "beautiful";
    final private boolean isTest = false;
    final private boolean isSysRead = false;

    int n, r;
    int[] arr;
    boolean isFlag = true;

    void solve(FastScanner reader) {
        n = reader.nextInt();
        r = reader.nextInt();
        arr = new int[n];
        for (int i = 1; i <= n; i++) {
            arr[i - 1] = i;
        }
        int ans = 0;
        while (isFlag) {
            if (isOk(getCurSum())) {
                ans++;
            }
            nextPerm();
        }
        reader.printStr(ans);
    }

    void nextPerm() {
        for (int i = n - 2; i >= 0; --i)
            if (arr[i] < arr[i + 1]) {
                int min = i + 1;
                for (int j = i + 1; j < n; ++j)
                    if ((arr[j] < arr[min]) && (arr[j] > arr[i])) {
                        min = j;
                    }
                swap(i, min);
                reverse(i+1, n-1);
                return;
            }
        isFlag = false;
    }

    boolean isOk(int d) {
        int cnt = 0;
        if (d == 0) {
            return true;
        }
        if (d == 1 || d == 2) {
            return false;
        }
        int h = 0;
        while ((d & 1) == 0) {
            ++h;
            d >>= 1;
        }
        if (d == 1) {
            return (h + 1) % 3 == 0;
        }
        else {
            cnt = h + 1;
        }
        for (int i = 3; i*i <= d; i += 2) {
            h = 0;
            while (d % i == 0) {
                ++h;
                d = d/i;
            }
            cnt *= (h + 1);
        }
        if (d > 1) {
            cnt <<= 1;
        }
        return (cnt % 3 == 0);
    }

    int getCurSum() {
        int result = 0;
        for (int i = 0; i < n - 1; ++i) {
            result += arr[i] * arr[i + 1];
        }
        return result % r;
    }

    void reverse(int l, int r){
        while (l < r)
        {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
    }

    void swap(int l, int r){
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }


    /**
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     **/
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        (new G()).solution();
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
