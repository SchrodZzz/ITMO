/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

#include <iostream>

using namespace std;

int minny(int *arr, int n, int x){
    int l = -1;
    int r = n;
    while (r - l > 1) {
        int mid = (l + r) / 2;
        if (arr[mid] >= x) {
            r = mid;
        } else {
            l = mid;
        }
    }
    return r;
}

int kostya(int *arr, int l, int r, int n) {
    int i = minny(arr, n, l);
    if (i == n) {
        return 0;
    }
    int j = minny(arr, n, r + 1) - 1;
    if (j == -1) {
        return 0;
    }
    return (j - i + 1);
}

void Qsort( int *arr, int l, int r) {
    int i = l;
    int j = r;
    int pivot = arr[(l + r) / 2];
    while (i <= j) {
        while (arr[i] < pivot)
            i++;
        while (arr[j] > pivot)
            j--;
        if (i <= j) {
            swap(arr[i], arr[j]);
            i++;
            j--;
        }
    }
    if (l < j)
        Qsort(arr, l, j);
    if (i < r)
        Qsort(arr, i, r);
}

int main()
{
    int n, k, l, r;
    cin >> n;
    int inputArr[n];
    for (int i = 0; i < n; i++) {
        cin >> inputArr[i];
    }
    cin >> k;
    Qsort(inputArr, 0, n - 1);
    int result[k];
    for (int i = 0; i < k; i++) {
        cin >> l >> r;
        result[i] = kostya(inputArr, l, r, n);
    }
    for (int i = 0; i < k; i++) {
        cout << result[i] << " ";
    }
    return 0;
}
