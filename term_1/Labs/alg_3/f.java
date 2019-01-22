import java.util.ArrayList;
import java.util.Scanner;

public class f {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        if (n > 0) {
            int[] values = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                values[i] = reader.nextInt();
            }
            DinnerActions[][] dynamic = new DinnerActions[n + 1][n + 1];
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    dynamic[i][j] = new DinnerActions();
                }
            }
            for (int j = 0; j <= n; j++) {
                dynamic[1][j].makeCost(1000000);
            }
            if (values[1] > 100) {
                dynamic[1][1].makeCost(values[1]);
            }
            else {
                dynamic[1][0].makeCost(values[1]);
            }
            for (int i = 2; i <= n; i++) {
                if (values[i] > 100) {
                    dynamic[i][n].makeCost(1000000);
                    for (int j = 0; j < n; j++) {
                        if (j > 0) {
                            int min = dynamic[i - 1][j + 1].getCost();
                            int prevBalance = j + 1;
                            boolean used = true;
                            if (dynamic[i - 1][j - 1].getCost() + values[i] <= min) {
                                min = dynamic[i - 1][j - 1].getCost() + values[i];
                                prevBalance = j - 1;
                                used = false;
                            }
                            dynamic[i][j].makeCost(min);
                            dynamic[i][j].makePrvBalance(prevBalance);
                            dynamic[i][j].makeUsed(used);
                        }
                        else {
                            dynamic[i][j].makeCost(dynamic[i - 1][j + 1].getCost());
                            dynamic[i][j].makePrvBalance(j + 1);
                            dynamic[i][j].makeUsed(true);
                        }
                    }
                }
                else {
                    dynamic[i][n].makeCost(1000000);
                    for (int j = 0; j < n; j++) {
                        int min = dynamic[i - 1][j + 1].getCost();
                        int prevBalance = j + 1;
                        boolean used = true;
                        if (dynamic[i - 1][j].getCost() + values[i] <= min) {
                            min = dynamic[i - 1][j].getCost() + values[i];
                            prevBalance = j;
                            used = false;
                        }
                        dynamic[i][j].makeCost(min);
                        dynamic[i][j].makePrvBalance(prevBalance);
                        dynamic[i][j].makeUsed(used);
                    }
                }
            }
            int min = dynamic[n][0].getCost();
            int label = 0;
            for (int j = 1; j <= n; j++) {
                if (dynamic[n][j].getCost() != 0 && dynamic[n][j].getCost() <= min) {
                    min = dynamic[n][j].getCost();
                    label = j;
                }
            }
            System.out.println(min);
            int countWasted = 0;
            ArrayList<Integer> days = new ArrayList<>();
            int prvBalance = label;
            for (int i = n + 1; i > 1; i--) {
                if (dynamic[i - 1][prvBalance].isUsed()) {
                    days.add(i - 1);
                    countWasted++;
                }
                prvBalance = dynamic[i - 1][prvBalance].getPrvBalance();
            }
            System.out.println(label + " " + countWasted);
            for (int i = days.size() - 1; i >= 0; i--) {
                System.out.println(days.get(i));
            }
        }
        else {
            System.out.println(0);
            System.out.printf("%d %d",0,0);
        }
    }

    static class DinnerActions {

        int cost;
        int prevBalance;
        boolean used;


        public DinnerActions() {
            cost = 0;
            prevBalance = 0;
            used = false;
        }

        public boolean isUsed() {
            return used;
        }

        public void makeUsed(boolean used) {
            this.used = used;
        }

        public int getCost() {
            return cost;
        }

        public void makeCost(int cost) {
            this.cost = cost;
        }

        public int getPrvBalance() {
            return prevBalance;
        }

        public void makePrvBalance(int prvBalance) {
            this.prevBalance = prvBalance;
        }
    }
}
