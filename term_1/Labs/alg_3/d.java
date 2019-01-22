import java.util.Scanner;

public class c {

    static long modDiv(long x) {
        return x % 1000000000;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        long[][] dynamic = new long[n + 1][10];
        long result = 0;
        for (int j = 1; j <= 9; j++) {
            if (j != 8) {
                dynamic[1][j] = 1;
            }
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= 9; j++) {
                switch (j) {
                    case 0:
                        dynamic[i][j] = modDiv(dynamic[i - 1][4] + dynamic[i - 1][6]);
                        break;
                    case 1:
                        dynamic[i][j] = modDiv(dynamic[i - 1][6] + dynamic[i - 1][8]);
                        break;
                    case 2:
                        dynamic[i][j] = modDiv(dynamic[i - 1][7] + dynamic[i - 1][9]);
                        break;
                    case 3:
                        dynamic[i][j] = modDiv(dynamic[i - 1][4] + dynamic[i - 1][8]);
                        break;
                    case 4:
                        dynamic[i][j] = modDiv(dynamic[i - 1][0] + dynamic[i - 1][3] + dynamic[i - 1][9]);
                        break;
                    case 5:
                        break;
                    case 6:
                        dynamic[i][j] = modDiv(dynamic[i - 1][0] + dynamic[i - 1][1] + dynamic[i - 1][7]);
                        break;
                    case 7:
                        dynamic[i][j] = modDiv(dynamic[i - 1][2] + dynamic[i - 1][6]);
                        break;
                    case 8:
                        dynamic[i][j] = modDiv(dynamic[i - 1][1] + dynamic[i - 1][3]);
                        break;
                    case 9:
                        dynamic[i][j] = modDiv(dynamic[i - 1][2] + dynamic[i - 1][4]);
                        break;
                }
            }
        }
        for (int j = 0; j <= 9; j++) {
            result = (result + dynamic[n][j]) % 1000000000;
        }
        System.out.println(result);
    }
}
