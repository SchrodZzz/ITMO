/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.text.DecimalFormat;
import java.util.Scanner;

public class j {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int vp = in.nextInt();
        int vf = in.nextInt();
        double a = in.nextDouble();
        double x = 0;
        double curLoc = x;
        double min = vf * Math.sqrt(2);
        while (x <= 1) {
            if (min > Math.sqrt(Math.pow(x, 2) + Math.pow(1 - a, 2)) / vp
                    + Math.sqrt(Math.pow(a, 2) + Math.pow(1 - x, 2)) / vf) {
                curLoc = x;
                min = Math.sqrt(Math.pow(x, 2) + Math.pow(1 - a, 2)) / vp
                        + Math.sqrt(Math.pow(a, 2) + Math.pow(1 - x, 2)) / vf;
            }
            x += 0.0001;
        }
        DecimalFormat up4 = new DecimalFormat(".0000");
        System.out.println("0" + up4.format(curLoc));
    }
}
