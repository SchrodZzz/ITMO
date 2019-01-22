/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

import java.util.Scanner;

public class a {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }
        in.close();
        a ms = new a();
        ms.mergeSortRecursive(nums,0,n-1);
        for (Integer num : nums) {
            System.out.print(num + " ");
        }
    }

    void merge(int nums[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
        int left[] = new int [n1];
        int right[] = new int [n2];
        for (int i=0; i<n1; ++i)
            left[i] = nums[l + i];
        for (int j=0; j<n2; ++j)
            right[j] = nums[m + 1+ j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2)
        {
            if (left[i] <= right[j])
            {
                nums[k] = left[i];
                i++;
            }
            else
            {
                nums[k] = right[j];
                j++;
            }
            k++;
        }
        while (i < n1)
        {
            nums[k] = left[i];
            i++;
            k++;
        }
        while (j < n2)
        {
            nums[k] = right[j];
            j++;
            k++;
        }
    }
    void mergeSortRecursive(int nums[], int l, int r)
    {
        if (l < r)
        {
            int m = (l+r)/2;
            mergeSortRecursive(nums, l, m);
            mergeSortRecursive(nums , m+1, r);
            merge(nums, l, m, r);
        }
    }
}
