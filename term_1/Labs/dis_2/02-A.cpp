/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

#include <iostream>
#include <vector>
#include <cstring>
#include <algorithm>
#include <fstream>
#include <string>

using namespace std;

long long int codeLength(vector<int> wesi, int n) {
    vector<long long int> holder(n, 1000000001);
    int i = 0, j = 0;
    long long int answer = 0;
    for (int k = 0; k < n - 1; k++) {
        if (i + 1 < n) {
            if ((wesi[i] + wesi[i + 1] <= wesi[i] + holder[j]) && (wesi[i] + wesi[i + 1] <= holder[j] + holder[j + 1])) {
                holder[k] = wesi[i] + wesi[i + 1];
                answer += holder[k];
                i += 2;
                continue;
            }
            if ((wesi[i] + holder[j] <= wesi[i] + wesi[i + 1]) && (wesi[i] + holder[j] <= holder[j] + holder[j + 1])) {
                holder[k] = wesi[i] + holder[j];
                answer += holder[k];
                i++;
                j++;
                continue;
            }
            if ((holder[j] + holder[j + 1] <= wesi[i] + wesi[i + 1]) && (holder[j] + holder[j + 1] <= wesi[i] + holder[j])) {
                holder[k] = holder[j] + holder[j + 1];
                answer += holder[k];
                j += 2;
            }
        } else {
            if (i < n && (wesi[i] + holder[j] <= holder[j] + holder[j + 1])) {
                holder[k] = wesi[i] + holder[j];
                answer += holder[k];
                i++;
                j++;
            } else {
                holder[k] = holder[j] + holder[j + 1];
                answer += holder[k];
                j += 2;
            }
        }
    }
    return answer;
}

int main()
{
    int n, w;
    ifstream ifstream1("huffman.in");
    ofstream ofstream1("huffman.out");
    ifstream1 >> n;
    vector<int> ws;
    for (int i = 0; i < n; i++) {
        ifstream1 >> w;
        ws.push_back(w);
    }
    sort(ws.begin(), ws.end());
    ofstream1 << codeLength(ws, n);
    return 0;
}
