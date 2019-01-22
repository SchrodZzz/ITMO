/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class k {
    static private double s;
    static public void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("kbest.in"));
        FileWriter out = new FileWriter("kbest.out");
        double st;
        long value, weight;
        int n = in.nextInt();
        int k = in.nextInt();
        Jew[] jews = new Jew[n];
        for (int i = 0; i < n; i++) {
            jews[i] = new Jew();
            jews[i].v = in.nextInt();
            jews[i].w = in.nextInt();
            jews[i].idx = i + 1;
        }
        s = 1.0;
        st = 0.0;
        while (Math.abs(s - st) > 0) {
            s = st;
            Arrays.sort(jews, new Comparator <Jew>() {
                public int compare(Jew j1, Jew j2) {
                    return (int) -Math.signum((j1.v - s * j1.w) - (j2.v - s * j2.w));
                }
            });
            value = 0;
            weight = 0;
            for (int i = 0; i < k; i++) {
                value += jews[i].v;
                weight += jews[i].w;
            }
            st = (double) value / (double) weight;
        }
        for (int i = 0; i < k; i++) {
            out.write(String.valueOf(jews[i].idx) + "\n");
        }
        out.close();
    }
    static public class Jew {
        public int v, w, idx;
    }
} 
