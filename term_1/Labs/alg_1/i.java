/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.text.DecimalFormat;
import java.util.Scanner;

public class i {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        double c = in.nextFloat();
        double x = Math.sqrt(c);
        while (Math.pow(x,2)+Math.sqrt(x) > c) {
            x -= 0.0000001;
        }
        DecimalFormat up6 = new DecimalFormat("#.000000");
        System.out.print(up6.format(x));
    }
}
