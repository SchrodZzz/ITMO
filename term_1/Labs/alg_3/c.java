import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class c {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());

        int[] dynamic = new int[n + 1];
        int[] link = new int[n + 1];
        int[] seq = new int[n];
        int[] prv = new int[n];

        link[0] = -1;
        int result = 0;

        String[] buffer = in.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            seq[i] = Integer.parseInt(buffer[i]);
        }

        dynamic[0] = Integer.MIN_VALUE / 2;
        for (int i = 1; i <= n; i++) {
            dynamic[i] = Integer.MAX_VALUE / 2;
        }

        for (int i = 0; i < n; i++) {
            int temporary = seq[i];
            int index = Arrays.binarySearch(dynamic, temporary);
            if (index < 0) {
                index = -index - 1;
            }
            if (seq[i] > dynamic[index - 1] && seq[i] < dynamic[index]) {
                dynamic[index] = seq[i];
                result = Math.max(index, result);
                link[index] = i;
                prv[i] = link[index - 1];
            }
        }

        System.out.println(result);
        ArrayList<Integer> ans = new ArrayList<>();
        int pos = link[result];
        while (pos != -1) {
            ans.add(seq[pos]);
            pos = prv[pos];
        }
        for (int i = ans.size() - 1; i >= 0; i--) {
            System.out.print(ans.get(i) + " ");
        }
    }
}
