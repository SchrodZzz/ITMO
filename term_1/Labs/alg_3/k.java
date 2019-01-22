import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class k {

    private static int max;
    private static int[] weight;
    private static int[] index;

    private static PrintWriter writer;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("skyscraper.in"));
        StringTokenizer inputData = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(inputData.nextToken());
        max = Integer.parseInt(inputData.nextToken());
        weight = new int[n];
        index = new int[n];
        for (int i = 0; i < n; i++) {
            weight[n - 1 - i] = Integer.parseInt(reader.readLine());
            index[n - 1 - i] = i + 1;
        }
        reader.close();
        writer = new PrintWriter(new BufferedWriter(new FileWriter("skyscraper.out")));
        nextTrip(n, true);
        writer.flush();
    }

    private static void nextTrip(int a, boolean norm) {
        if (a == 0) {
            return;
        }

        int[] sum = new int[1 << a];
        for (int i = 0; i < (1 << a); i++) {
            for (int j = 0; j < a; j++) {
                if ((i & (1 << j)) != 0) {
                    sum[i] += weight[j];
                }
            }
        }

        int[] pret = new int[1 << a];
        int all = (1 << a) - 1;
        int cnt = 1;
        while (true) {
            if (cnt > 1) {
                for (int i = 0; i < (1 << a); i++) {
                    for (int j = 0; j < a; j++) {
                        if ((i & (1 << j)) == 0) {
                            pret[i | (1 << j)] = Math.max(pret[i | (1 << j)], pret[i]);
                        }
                    }
                }
            }

            if (sum[all] - pret[all] <= max) {
                if (norm) {
                    writer.println(cnt);
                }

                for (int pPreT = 0; pPreT < (1 << a); pPreT++) {
                    if (sum[pPreT] == pret[pPreT] && sum[all] - pret[pPreT] <= max) {
                        int cT = all & (~pPreT);
                        writer.print(Integer.bitCount(cT));
                        for (int i = a - 1; i >= 0; i--) {
                            if ((cT & (1 << i)) != 0) {
                                writer.printf(" %d", index[i]);
                            }
                        }
                        writer.println();
                        int res = 0;
                        for (int i = 0; i < a; i++) {
                            if ((pPreT & (1 << i)) != 0) {
                                weight[res] = weight[i];
                                index[res] = index[i];
                                res++;
                            }
                        }
                        nextTrip(res, false);
                        return;
                    }
                }
            }
            for (int i = 0; i < (1 << a); i++) {
                pret[i] = sum[i] - pret[i] <= max ? sum[i] : 0;
            }
            cnt++;
        }
    }

}
