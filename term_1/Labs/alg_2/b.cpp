#include <iostream>
#include <cmath>

using namespace std;

int main() {
    int n, a, b, tmp, locV, hi, lo, pointer;
    cin >> n;
    int A[n];
    for (int i = 0; i < n; i++) {
        cin >> A[i];
    }
    locV = n;
    hi = 0;
    while (hi < 100) {
        lo = 0;
        pointer = 0;
        for (int i = pointer; i < locV; i++) {
            if (A[i] == A[i + 1]) {
                lo++;
            } else {
                pointer = lo;
                lo = 0;
                if (pointer >= 2) {
                    a = abs(pointer - i);
                    b = pointer + 1;
                    break;
                }
            }
        }
        if (pointer < 2) break;
        tmp = 0;
        while (tmp < b) {
            for (int i = a; i < locV - tmp; i++) {
                A[i] = A[i + 1];
            }
            tmp++;
        }
        locV = locV - tmp;
        hi++;
    }
    cout << n - locV << endl;
    return 0;
}
