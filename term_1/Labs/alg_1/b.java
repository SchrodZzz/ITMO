/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.util.ArrayList;
import java.util.Scanner;

public class b {
    public static void main(String[] args) {
        ArrayList <Integer> nums = new ArrayList <>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            nums.add(in.nextInt());
        }
        in.close();

        int[] sortedArr = new int[nums.size()];
        int min = nums.get(0);
        int max = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < min) {
                min = nums.get(i);
            } else if (nums.get(i) > max) {
                max = nums.get(i);
            }
        }

        int[] c = new int[max - min + 1];
        for (int i = 0; i < nums.size(); i++) {
            c[nums.get(i) - min]++;
        }
        c[0]--;
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i] + c[i - 1];
        }
        for (int i = nums.size() - 1; i >= 0; i--) {
            sortedArr[c[nums.get(i) - min]--] = nums.get(i);
        }
        for (Integer num : sortedArr) {
            System.out.print(num + " ");
        }
    }
}
