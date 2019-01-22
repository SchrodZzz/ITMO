import java.util.Scanner;

public class e {

    static int min(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        StringBuilder nol = new StringBuilder(" ");
        String fString = new String(nol.append(reader.nextLine()));
        int max = fString.length();
        nol = new StringBuilder(" ");
        String sString = new String(nol.append(reader.nextLine()));
        int n = sString.length();
        int[][] dynamic = new int[max][n];
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dynamic[i][j] = 0;
                    continue;
                }
                if (i > 0 && j == 0) {
                    dynamic[i][j] = i;
                    continue;
                }
                if (i == 0) {
                    dynamic[i][j] = j;
                    continue;
                }
                if (fString.charAt(i) == sString.charAt(j)) {
                    dynamic[i][j] = dynamic[i - 1][j - 1];
                    continue;
                }
                dynamic[i][j] = min(dynamic[i][j - 1], dynamic[i - 1][j], dynamic[i - 1][j - 1]) + 1;
            }
        }
        System.out.println(dynamic[max - 1][n - 1]);
    }
}
