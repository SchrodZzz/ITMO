import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class g {

    static StringTokenizer st;
    static int[] belonger;
    static int[] minValues;
    static int[] maxValues;
    static int[] rupa;
    static int[] numbers;

    public static void main(String[] args) {
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

            int n = Integer.parseInt(buf.readLine());

            belonger = new int[n + 1];
            minValues = new int[n + 1];
            maxValues = new int[n + 1];
            rupa = new int[n + 1];
            numbers = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                belonger[i] = i;
                minValues[i] = i;
                maxValues[i] = i;
                numbers[i] = 1;
                rupa[i] = 1;
            }

            String curString;
            while ((curString = buf.readLine()) != null) {
                st = new StringTokenizer(curString);
                String input = st.nextToken();
                if (input.equals("union")) {
                    int a = Integer.parseInt(st.nextToken());
                    int b = Integer.parseInt(st.nextToken());
                    union(a, b);
                } else if (input.equals("get")) {
                    int a = Integer.parseInt(st.nextToken());
                    System.out.println(ansValues(a));
                }
            }
        } catch (Exception ex) {
        }
    }

    static String ansValues(int x) {
        StringBuilder sb = new StringBuilder();
        int owner = get(x);
        sb.append(minValues[owner] + " " + maxValues[owner] + " " + numbers[owner]);
        return sb.toString();
    }

    static void union(int a, int b) {
        a = get(a);
        b = get(b);
        if (a == b)
            return;
        if (rupa[a] == rupa[b]) {
            rupa[a]++;
        }
        if (rupa[a] < rupa[b]) {
            minValues[belonger[b]] = min(minValues[belonger[a]], minValues[belonger[b]]);
            maxValues[belonger[b]] = max(maxValues[belonger[a]], maxValues[belonger[b]]);
            numbers[belonger[b]] = numbers[belonger[a]] + numbers[belonger[b]];
            belonger[a] = b;
        } else {
            minValues[belonger[a]] = min(minValues[belonger[a]], minValues[belonger[b]]);
            maxValues[belonger[a]] = max(maxValues[belonger[a]], maxValues[belonger[b]]);
            numbers[belonger[a]] = numbers[belonger[a]] + numbers[belonger[b]];
            belonger[b] = a;
        }
    }

    static int get(int x) {
        if (belonger[x] != x) {
            belonger[x] = get(belonger[x]);
        }
        return belonger[x];
    }

    static int min(int a, int b) {
        if (a < b) return a;
        else return b;
    }

    static int max(int a, int b) {
        if (a > b) return a;
        else return b;
    }
}
