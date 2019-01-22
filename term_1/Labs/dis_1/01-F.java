/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class coding_a {

    public static void main(String args[]) throws IOException {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        int it = (int)Math.pow(2,n);
        ArrayList<String> tab = new ArrayList <>();
        ArrayList<Integer> res = new ArrayList <>();
        ArrayList<ArrayList<Integer>> calculations = new ArrayList <>();
        for (int i = 0; i < it; i++) {
            tab.add(reader.next());
            res.add(reader.nextInt());
        }
        calculations.add(res);
        for (int i = 0; i < it; i++) {
            ArrayList<Integer> helper = new ArrayList <>();
            for (int j = 1; j < it - i; j++) {
                helper.add((calculations.get(i).get(j-1)+calculations.get(i).get(j))%2);
            }
            calculations.add(helper);
        }
        for (int i = 0; i < it; i++) {
            System.out.print(tab.get(i) + " " + calculations.get(i).get(0) + " \n");
        }
    }
}
