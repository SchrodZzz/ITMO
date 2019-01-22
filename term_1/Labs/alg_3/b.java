import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class b {
    private static int[][] moneyMap;
    private static int n = 0, k = 0, u = 0;
    private static int[][] dynamic;
    private static List <String> result = new ArrayList <>();
    private static String str;

    private static void filler() {
        dynamic = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                if (i == 0 && j == 0) {
                    dynamic[i][j] = moneyMap[i][j];
                } else if (i == 0) {
                    dynamic[i][j] = dynamic[i][j - 1] + moneyMap[i][j];
                } else if (j == 0) {
                    dynamic[i][j] = dynamic[i - 1][j] + moneyMap[i][j];
                } else {
                    if (dynamic[i - 1][j] >= dynamic[i][j - 1]) {
                        dynamic[i][j] = dynamic[i - 1][j] + moneyMap[i][j];
                    } else {
                        dynamic[i][j] = dynamic[i][j - 1] + moneyMap[i][j];
                    }
                }
            }
        }
    }

    private static void read() throws IOException {
        int[] inData = new int[2];
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
        str = br.readLine();
        for (String tmp : str.split(" ")) {
            if (!tmp.isEmpty()) {
                inData[u] = Integer.parseInt(tmp);
                u++;
                if (u == 1) {
                    n = inData[0];
                } else {
                    k = inData[1];
                }
            }
        }
        moneyMap = new int[n][k];
        for (int i = 0; i < n; i++) {
            u = 0;
            str = br.readLine();
            for (String tmp : str.split(" ")) {
                if (!tmp.isEmpty()) {
                    moneyMap[i][u] = Integer.parseInt(tmp);
                    u++;
                }
            }
        }
    }

    private static void getOut() {
        int i = n - 1;
        int j = k - 1;
        while (!(i == 0 && j == 0)) {
            if (i == 0 && j > 0) {
                result.add("R");
                j--;
            } else if (j == 0 && i > 0) {
                result.add("D");
                i--;
            } else {
                if (dynamic[i - 1][j] >= dynamic[i][j - 1]) {
                    i--;
                    result.add("D");
                } else {
                    j--;
                    result.add("R");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        read();
        filler();
        getOut();
        BufferedWriter bw = new BufferedWriter(new PrintWriter("output.txt"));
        bw.write(dynamic[n - 1][k - 1] + "\n");
        while (result.size() > 0) {
            bw.write(result.remove(result.size() - 1));
        }
        bw.flush();
    }
}
