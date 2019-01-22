/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.util.Scanner;

public class c {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];
        int[] tmp = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }
        in.close();
        c msc = new c();
        System.out.print(msc.mergeSortRecursive(nums, tmp, 0, n - 1));
    }

    long merge(int nums[], int tmp[], int l, int m, int r) {
        int i, j, k;
        long count = 0;

        i = l;
        j = m;
        k = l;
        while ((i <= m - 1) && (j <= r)) {
            if (nums[i] <= nums[j]) {
                tmp[k++] = nums[i++];
            } else {
                tmp[k++] = nums[j++];
                count += m - i;
            }
        }

        while (i <= m - 1)
            tmp[k++] = nums[i++];
        while (j <= r)
            tmp[k++] = nums[j++];

        for (i = l; i <= r; i++)
            nums[i] = tmp[i];
        return count;
    }

    long mergeSortRecursive(int nums[], int tmp[], int l, int r) {
        long count = 0;
        if (l < r) {
            int m = (l + r) / 2;
            count += mergeSortRecursive(nums, tmp, l, m);
            count += mergeSortRecursive(nums, tmp, m + 1, r);
            count += merge(nums, tmp, l, m + 1, r);
        }
        return count;
    }
}
