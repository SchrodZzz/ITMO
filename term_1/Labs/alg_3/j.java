import java.io.*;

public class j {

    public static StreamTokenizer t;

    public static int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public static boolean matcher(String a, String b, int n) {
        a = action(a, n);

        b = action(b, n);

        for (int i = 0; i < n - 1; ++i) {
            if (a.charAt(i) == b.charAt(i) && a.charAt(i + 1) == b.charAt(i + 1) && a.charAt(i) == a.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private static String action(String b, int n) {
        if (b.length() < n) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n - b.length(); i++) {
                sb.append(0);
            }
            b = sb.append(b).toString();
        }
        return b;
    }

    public static int exp(int n) {
        int a = 1;
        for (int i = 0; i < n; i++) {
            a *= 2;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        t = new StreamTokenizer( new BufferedReader( new FileReader("nice.in")));
        PrintWriter writer = new PrintWriter("nice.out");

        int n = nextInt();
        int max = nextInt();
        if (n > max) {
            int c = n;
            n = max;
            max = c;
        }
        int[][] cnt = new int[max][exp(n)];

        int index = -1;
        while (++index < exp(n)) {
            cnt[0][index] = 1;
        }

        int income = 0;
        while (++income < max) {
            int p = -1;
            while (++p < exp(n)) {
                int z = -1;
                while (++z < exp(n)) {
                    if (matcher(Integer.toString(p, 2), Integer.toString(z, 2), n)) {
                        cnt[income][p] += cnt[income - 1][z];
                    }
                }
            }
        }

        int result = 0;

        index = -1;
        while (++index < exp(n)) {
            result += cnt[max - 1][index];
        }

        writer.print(result);
        writer.close();
    }

}
