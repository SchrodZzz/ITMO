/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.util.Scanner;

public class f {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = in.nextInt();
        }
        for (int i = 0; i < k; i++) {
            System.out.println(binSearch(sorted, in.nextInt()));
        }

    }

     static int binSearch(int[] arru,int x) {
        if(x < arru[0]) {
            return arru[0];
        }
        if(x > arru[arru.length-1]) {
            return arru[arru.length-1];
        }
        int l = 0;
        int r = arru.length - 1;
        while (l <= r) {
            int m = (r + l) / 2;

            if (x < arru[m]) {
                r = m - 1;
            } else if (x > arru[m]) {
                l = m + 1;
            } else {
                return arru[m];
            }
        }
        return (arru[l] - x) < (x - arru[r]) ? arru[l] : arru[r];
    }
}
