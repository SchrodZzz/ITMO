/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

import java.util.ArrayList;
import java.util.Scanner;

public class coding_a {

    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);

        byte n = reader.nextByte();
        int m = reader.nextInt();

        byte[] arguments = new byte[n];
        ArrayList<Byte> h1 = new ArrayList <>();
        ArrayList<Byte> h2 = new ArrayList <>();

        for (int i = 0; i < m; i++) {
            byte a = reader.nextByte();
            byte b = reader.nextByte();
            boolean gg = true;
            if ((h1.contains(a) && h2.contains(b)) || (h1.contains(b) && h2.contains(a))) {
                for (int j = 0; j < h1.size(); j++) {
                    if ((h1.get(j) == a && h2.get(j) == b) || (h1.get(j) == b && h2.get(j) == a)) {
                        gg = false;
                        break;
                    }
                }
            }
            if (gg) {
                h1.add(a); h2.add(b);
            }
        }

        boolean ok = false;
        int all = (int) Math.pow(2, n);
        //long start = System.nanoTime();
        for (int i = 0; i < all; i++) {

            if (ok) break;

            for (int j = n - 1; j >= 0; j--) {
                arguments[j] = (byte) ((i / (int) Math.pow(2, j)) % 2);
            }

            for (int j = 0; j < h1.size(); j++) {
                int first;
                int second;
                int iz1 = Math.abs(h1.get(j)) - 1;
                int iz2 = Math.abs(h2.get(j)) - 1;

                if (h1.get(j) < 0) first = arguments[iz1] == 1 ? 0 : 1;
                else first = arguments[iz1];

                if (h2.get(j) < 0) second = arguments[iz2] == 1 ? 0 : 1;
                else second = arguments[iz2];

                if ((first | second) == 0) break;
                else if (j == h1.size() - 1) ok = true;
            }
        }
        if (ok) System.out.println("NO");
        else System.out.println("YES");
        //System.out.println((System.nanoTime() - start)/1_000_000_000.0);
    }
}
