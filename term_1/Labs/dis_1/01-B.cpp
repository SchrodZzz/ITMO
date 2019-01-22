/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

#include <iostream>
#include <vector>

using namespace std;

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> addition, values(static_cast<unsigned long>(n + 1), -1);
    vector<vector<int> > coefficents(static_cast<unsigned long>(k), addition);
    for (int i = 0; i < k; i++) {
        for (int j = 0; j < n; j++) {
            int readedVal;
            cin >> readedVal;
            if (readedVal == 1) {
                coefficents[i].push_back(j + 1);
            } else if (readedVal == 0) {
                coefficents[i].push_back(-j - 1);
            }
        }
    }
    bool dieTime = false;
    while (!dieTime) {
        dieTime = true;
        for (int i = 0; i < k; i++) {
            if ((int) coefficents[i].size() == 1) {
                dieTime = false;
                int curVar = coefficents[i][0];
                if (values[abs(curVar)] == -1) {
                    if (curVar > 0) {
                        values[abs(curVar)] = 1;
                    } else {
                        values[abs(curVar)] = 0;
                    }
                    coefficents[i].clear();
                } else {
                    if (curVar > 0) {
                        if (values[abs(curVar)] == 0) {
                            cout << "YES";
                            exit(0);
                        } else if (values[abs(curVar)] == 1) {
                            cout << "YES";
                            exit(0);
                        }
                    }
                }
                for (int j = 0; j < k; j++) {
                    if (i == j) {
                        continue;
                    }
                    if ((int) coefficents[j].size() == 1 && coefficents[j][0] == curVar) {
                        coefficents[j].clear();
                        continue;
                    }
                    int closRes = 0, f = 0;
                    for (int z : coefficents[j]) {
                        if (values[z] != -1) {
                            f++;
                            if (z > 0 && values[z] == 1) {
                                closRes = 1;
                                break;
                            } else if (z < 0 && values[abs(z)] == 0) {
                                closRes = 1;
                                break;
                            }
                            if (z > 0) {
                                closRes |= values[z];
                            } else {
                                closRes |= (1 - values[abs(z)]);
                            }
                        }
                    }
                    if (closRes == 0 && (int) coefficents[j].size() != 0 && f == (int) coefficents[j].size()) {
                        cout << "YES";
                        exit(0);
                    } else if (closRes == 1) {
                        coefficents[j].clear();
                    } else {
                        int removed = -1;
                        for (int z = 0; z < (int) coefficents[j].size(); z++) {
                            if (abs(coefficents[j][z]) == abs(curVar)) {
                                removed = z;
                                break;
                            }
                        }
                        if (removed != -1) {
                            coefficents[j].erase(coefficents[j].begin() + removed);
                        }
                    }
                }
            }
        }
    }
    cout << "NO";
    return 0;
}
