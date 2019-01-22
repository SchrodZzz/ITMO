/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 10.2018
 */

#include <iostream>

using namespace std;

const int N = 100000;
int heap[N];
int heapSize = 0;

void siftDown(int i) {
    while (2 * i + 1 < heapSize) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if (left < heapSize && heap[left] > heap[largest]) {
            largest = left;
        }
        if (right < heapSize && heap[right] > heap[largest]) {
            largest = right;
        }
        if (largest == i) {
            break;
        }
        swap(heap[i], heap[largest]);
        i = largest;
    }
}

int extract() {
    int result = heap[0];
    heap[0] = heap[heapSize - 1];
    heapSize--;
    siftDown(0);
    return result;
}

void insert(int value) {
    heap[heapSize] = value;
    int i = heapSize;
    heapSize++;
    int parent = (i - 1) / 2;
    while (i > 0 && heap[parent] < heap[i]) {
        swap(heap[i], heap[parent]);
        i = parent;
        parent = (i - 1) / 2;
    }
}

int main(){
    int n, firstAr, secondAr;
    int j = 0;
    cin >> n;
    int extractLog[n];
    for (int i = 0; i < n; i++) {
        cin >> firstAr;
        if (firstAr == 0) {
            cin >> secondAr;
            insert(secondAr);
        }
        else {
            extractLog[j] = extract();
            j++;
        }
    }
    for (int i = 0; i < j; i++) {
        cout << extractLog[i] << endl;
    }
    return 0;
}
