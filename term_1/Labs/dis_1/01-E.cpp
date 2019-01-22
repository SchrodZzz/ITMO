/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

#include <iostream>
#include <vector>
#include <string>

using namespace std;

int depth = 0;
vector < char > binaryData;
vector < int > funcNums(28);
vector < int > addition;
vector < int > args(28);
vector < vector < int > > an(28, addition);
string funcs[28];
string curFuncs[28];

int morth(string cur) {
    int result = 0, size = (int)cur.length(), squad = 1;
    for (int i = size - 1; i >= 0; i--) {
        if (cur[i] == '0') {
            squad *= 2;
            continue;
        }
        result += squad * ((int)(cur[i]) - 48);
        squad *= 2;
    }
    return result;
}

char calcul(int var, int curDepth = 0) {
    if (curDepth > depth) {
        depth = curDepth;
    }
    if ((int)an[var].size() == 0) {
        return binaryData[funcNums[var]];
    }
    curFuncs[args[var]] = "";
    for (auto to: an[var]) {
        curFuncs[args[var]] += calcul(to, curDepth + 1);
    }
    return funcs[var][morth(curFuncs[args[var]])];
}


int main() {
    int n;
    scanf("%d", &n);
    vector < int > filler;
    int filIdx = 0, funcCounter = 0;
    for (int i = 1; i <= n; i++) {
        int m;
        scanf("%d", &m);
        if (m == 0) {
            filler.push_back(i);
            funcNums[i] = filIdx;
            filIdx++;
            continue;
        }
        args[i] = funcCounter;
        funcCounter++;
        for (int j = 0; j < m; j++) {
            int start;
            scanf("%d", &start);
            an[i].push_back(start);
        }
        string s = "";
        for (int j = 0; j < (1 << m); j++) {
            int x;
            scanf("%d", &x);
            s += (char)(x + 48);
        }
        funcs[i] = s;
    }
    int shift = (int)filler.size();
    string answer = "";
    for (int i = 0; i < (1 << shift); i++) {
        vector < char > UpdatedBin(static_cast<unsigned long>(shift), '0');
        int tmp = i, counter = 1;
        while (tmp) {
            UpdatedBin[shift - counter] = (char)(tmp % 2 + 48);
            tmp /= 2;
            counter++;
        }
        binaryData = UpdatedBin;
        answer += calcul(n);
    }
    printf("%d\n%s", depth, answer.c_str());
    return 0;
}
