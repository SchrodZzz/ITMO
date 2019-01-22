/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.util.Scanner;

public class g {
    public static void main(String[] args) {
        int n,x,y,xc,yc,t=0;
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        x = in.nextInt();
        y = in.nextInt();
        t+=Math.min(x,y);
        n--;
        xc=yc=0;
        while (n>0) {
            t++;xc++;yc++;
            if (xc==x) {
                n--; xc=0;
            }
            if (yc==y) {
                n--; yc=0;
            }
        }
        System.out.println(t);
    }
}
