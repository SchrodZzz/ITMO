import java.io.*;
import java.util.ArrayList;

public class a {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
             PrintWriter writer = new PrintWriter("output.txt")) {
            String[] buffer = reader.readLine().split(" ");
            int n = Integer.parseInt(buffer[0]);
            int k = Integer.parseInt(buffer[1]);
            buffer = reader.readLine().split(" ");
            int[] values = new int[n + 1];
            values[1] = 0;
            for (int i = 2; i < n; i++) {
                values[i] = (Integer.parseInt(buffer[i - 2]));
            }
            values[n] = 0;
            ArrayList<JumpActions> dump = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                dump.add(new JumpActions());
            }
            for (int i = 2; i <= n; i++) {
                int max = dump.get(i - 1).getCoin();
                int label = i - 1;
                int operationsCounter = Math.min(i - 1, k);
                int j = 1;
                while (j <= operationsCounter) {
                    if (dump.get(i - j).getCoin() >= max) {
                        max = dump.get(i - j).getCoin();
                        label = i - j;
                    }
                    j++;
                }
                int curCoin = values[i] + max;
                dump.get(i).setCoin(curCoin);
                dump.get(i).setPrev(label);
            }
            writer.println(dump.get(dump.size() - 1).getCoin());
            StringBuilder temp = new StringBuilder();
            int last = dump.size() - 1;
            temp.append(dump.size() - 1);
            while (dump.get(last).getPrev() != 0) {
                temp.append(" ").append(dump.get(last).getPrev());
                last = dump.get(last).getPrev();
            }
            buffer = temp.toString().trim().split(" ");
            writer.println(buffer.length - 1);
            for (int i = buffer.length - 1; i >= 0; i--) {
                writer.print(buffer[i] + " ");
            }
        }
    }

    static class JumpActions {

        private int coin;
        private int prev;

        public JumpActions() {
            coin = 0;
            prev = 0;
        }

        public int getCoin() {
            return coin;
        }

        public int getPrev() {
            return prev;
        }

        public void setPrev(int prev) {
            this.prev = prev;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }
    }
}
