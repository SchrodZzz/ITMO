import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.*;

public class a {

    public static void main(String[] args) throws IOException {
        FastScanner reader = new FastScanner();
        String[] s = reader.nextLine().split("[\\s]");
        int n = Integer.parseInt(s[0]);
        int[] minVals = new int[n];
        int pivot = -1;
        for (int i = 0; i < n; i++) {
            String cmdP = reader.nextLine();
            if (cmdP.charAt(0) == '1') {
                int tmp = Integer.parseInt(cmdP.substring(2));
                pivot++;
                if (pivot > 0) {
                    if (tmp > minVals[pivot - 1]) {
                        minVals[pivot] = minVals[pivot - 1];
                    } else {
                        minVals[pivot] = tmp;
                    }
                } else {
                    minVals[pivot] = tmp;
                }
            } else {
                if (cmdP.charAt(0) == '2') {
                    pivot--;
                } else {
                    System.out.print(minVals[pivot] +"\n");
                }
            }
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
