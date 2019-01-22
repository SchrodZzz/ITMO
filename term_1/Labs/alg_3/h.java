import java.io.*;

public class h {

    private static StreamTokenizer t;
    private static int n;
    private static int currentPos = 0;
    private static int[][] buffer;
    private static int[][] dp;
    private static int e9 = 1000000000;

    private static int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    private static int search(int i, int mask) {
        if (dp[i][mask] != e9) return dp[i][mask];

        for (int j = 0; j < n; j++) {
            if (((1 << j) & mask) != 0) {
                dp[i][mask] = min(dp[i][mask], search(j, mask ^ (1 << j)) + buffer[i][j]);

            }
        }

        return dp[i][mask];
    }

    private static void getAnswer() {
        int mask = (1 << n) - 1 - (1 << currentPos);
        System.out.print((currentPos + 1) + " ");
        int i = currentPos;
        for(int q = 1; q < n; ++q){
            for(int j = 0; j < n; ++j){
                if(((1 << j) & mask) != 0){
                    if(dp[j][mask ^ (1 << j)] + buffer[i][j] == dp[i][mask]){
                        System.out.print((j + 1) + " ");
                        i = j;
                        mask = (mask ^ (1 << j));
                        break;
                    }
                }
            }
        }
    }

    private static int min(int a, int b) {
        return a<b? a:b;
    }

    public static void main(String[] args) throws IOException {
        t = new StreamTokenizer( new BufferedReader( new InputStreamReader(System.in)));

        n = nextInt();
        buffer = new int[n][n];
        dp = new int[n][(1 << (n))];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buffer[i][j] = nextInt();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < (1 << n); j++) {
                dp[i][j] = e9;
            }
        }
        for (int i = 0; i < n; i++) {
            dp[i][0] = 0;
        }

        int res = e9;
        for (int i = 0; i < n; i++) {
            int z = search(i, (1 << n) - 1 - (1 << i));
            if (z < res) {
                res = z;
                currentPos = i;
            }
        }
        System.out.println(res);

        getAnswer();
    }

}
