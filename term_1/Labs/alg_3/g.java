import java.io.*;
import java.util.Scanner;

public class g {

    public static int[][] dynamic;
    public static int[][] substring;

    public static void AnsSearchRec(int l, int r, String input) {
        if (dynamic[l][r] + l == r + 1) {
            return;
        }
        if (dynamic[l][r] == 0) {
            for (int i = l; i <= r; i++) {
                System.out.print(input.charAt(i));
            }
            return;
        }
        if (substring[l][r] < 0) {
            System.out.print(input.charAt(l));
            AnsSearchRec(l + 1, r - 1, input);
            System.out.print(input.charAt(r));
            return;
        }
        AnsSearchRec(l, substring[l][r], input);
        AnsSearchRec(substring[l][r] + 1, r, input);
    }

    public static boolean isPair(char open, char close) {
        return ((open == '(' && close == ')') || (open == '[' && close == ']') || (open == '{' && close == '}'));
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        String input = reader.nextLine();

        dynamic = new int[input.length()][input.length()];
        substring = new int[input.length()][input.length()];
        int n = input.length();

        for (int i = 0; i < n; i++) {
            dynamic[i][i] = 1;
        }

        int l, r = 0;
        while (++r < n) {
            l = r + 1;
            while (--l >= 0) {
                if (r != l) {
                    int min = 1000000;
                    substring[l][r] = -1;
                    if (isPair(input.charAt(l), input.charAt(r))) {
                        min = dynamic[l + 1][r - 1];
                    }
                    int k = l - 1;
                    while (++k < r) {
                        if (dynamic[l][k] + dynamic[k + 1][r] < min) {
                            min = dynamic[l][k] + dynamic[k + 1][r];
                            substring[l][r] = k;
                        }
                    }
                    dynamic[l][r] = min;
                }
            }
        }

        AnsSearchRec(0, n - 1, input);
    }
}
