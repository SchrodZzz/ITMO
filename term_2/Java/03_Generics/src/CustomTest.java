import tabulator.GenericTabulator;
import tabulator.Tabulator;

import java.util.Scanner;

public class CustomTest {
    public static void main(String[] args) {
        CustomTest tester = new CustomTest();
        tester.test("i", "x + y + z", -4, 0, -5, -3, 1, 3);
        border();
        tester.test("d", "x/y*z", 1, 5, 1, 5, 2, 3);
        border();
        tester.test("i", "2147483647 * x", 1, 2, 1, 1, 1, 1);
        border();
        //        tester.test2();
        while (true) {
            tester.test3();
            border();
        }
    }

    private static void border() {
        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private void test(
            String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        try {
            final Tabulator tabulator = new GenericTabulator();
            Object[][][] res = tabulator.tabulate(mode, expression, x1, x2, y1, y2, z1, z2);

            for (Object[][] cur2d : res) {
                int tmpY = y1;
                System.out.println("\nNew Layer : " + "x = " + x1++);
                for (Object[] cur1d : cur2d) {
                    System.out.print("y = " + y1++ + " : ");
                    for (Object cur : cur1d) {
                        System.out.print(cur + " ");
                    }
                    System.out.println();
                }
                y1 = tmpY;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test2() {
        Scanner reader = new Scanner(System.in);
        System.out.print("mode : ");
        String mode = reader.nextLine();
        System.out.print("expression : ");
        String expression = reader.nextLine();
        int[] values = new int[6];
        System.out.print("args : ");
        for (int i = 0; i < 6; ++i) {
            values[i] = reader.nextInt();
        }
        test(mode, expression, values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    private void test3() {
        Scanner reader = new Scanner(System.in);
        test("i", reader.nextLine(), 1, 1, 1, 1, 1, 1);
    }
}
