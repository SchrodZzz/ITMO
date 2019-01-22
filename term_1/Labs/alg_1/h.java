/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.util.Scanner;

public class h {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long w = sc.nextInt();
        long h = sc.nextInt();
        long n = sc.nextInt();
        long res = 1;
        while (!kostya(res, w, h, n)) {
            res *= 2;
        }
        long min = res / 2;
        while (res - min > 1) {
            long mid = (min + res) / 2;
            if (!kostya(mid, w, h, n)) {
                min = mid;
            } else {
                res = mid;
            }
        }
        System.out.println(res);
    }
    static boolean kostya(long a, long b, long c, long barakuda) {
        return (a / b) * (a / c) >= barakuda;
    }
}
