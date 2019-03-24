#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <map>

int main()
{
    using namespace std;

    ifstream cin("polynomial.in");
    ofstream cout("polynomial.out");

    int t;
    cin >> t;
    for (int i = 0; i < t; ++i)
    {
        string curPolynom;
        cin >> curPolynom;

        map<pair<int, int>, int> data;

        int isPositive = 1;
        for (int j = 0; j < (int) curPolynom.size(); ++j)
        {
            int n = 0, m = 0, k = 1, type = -1;
            while (j < (int) curPolynom.size() && curPolynom[j] != '-' && curPolynom[j] != '+')
            {
                if (curPolynom[j] == '^')
                {
                    if (j)
                    {
                        curPolynom[j - 1] == 'n' ? type = 0 : type = 1;
                    }
                }
                else if (curPolynom[j] >= '0' && curPolynom[j] <= '9')
                {
                    int num = 0;
                    while (j < (int) curPolynom.size() && curPolynom[j] >= '0' && curPolynom[j] <= '9')
                    {
                        num *= 10;
                        num += curPolynom[j] - '0';
                        ++j;
                    }
                    type == -1 ? k *= num : (type == 0 ? n += num : m += num);
                    type = -1;
                    --j;
                }
                else if (j + 1 < (int) curPolynom.size())
                {
                    if (curPolynom[j] == 'n' && (curPolynom[j + 1] != '^' || j == (int) curPolynom.size() - 1))
                    {
                        ++n;
                    }
                    else if (j + 1 < (int) curPolynom.size())
                    {
                        if (curPolynom[j] == 'm' && (curPolynom[j + 1] != '^' || j == (int) curPolynom.size() - 1))
                        {
                            ++m;
                        }
                    }
                    else
                    {
                        if (curPolynom[j] == 'm' && j == (int) curPolynom.size() - 1)
                        {
                            ++m;
                        }
                    }
                }
                else
                {
                    if (curPolynom[j] == 'n' && j == (int) curPolynom.size() - 1)
                    {
                        ++n;
                    }
                    else if (j + 1 < (int) curPolynom.size())
                    {
                        if (curPolynom[j] == 'm' && (curPolynom[j + 1] != '^' || j == (int) curPolynom.size() - 1))
                        {
                            ++m;
                        }
                    }
                    else
                    {
                        if (curPolynom[j] == 'm' && j == (int) curPolynom.size() - 1)
                        {
                            ++m;
                        }
                    }
                }
                ++j;
            }
            if (!(!j && (curPolynom[j] == '+' || curPolynom[j] == '-')))
            {
                data[make_pair(n, m)] += isPositive * k;
            }
            if (j < (int) curPolynom.size())
            {
                curPolynom[j] == '+' ? isPositive = 1 : isPositive = -1;
            }
        }
        int size = 0;
        for (auto cur : data)
        {
            if (cur.second)
            {
                if (abs(cur.second) == 1)
                {
                    if (cur.first.first == 0 && cur.first.second == 0)
                    {
                        if (cur.second == 1)
                        {
                            if (size > 0)
                            {
                                cout << "+";
                            }
                            cout << 1;
                        }
                        else
                        {
                            cout << -1;
                        }
                    }
                    else if (cur.second == 1 && size > 0)
                    {
                        cout << "+";
                    }
                    else if (cur.second == -1)
                    {
                        cout << "-";
                    }
                }
                else
                {
                    if (cur.second > 0 && size > 0)
                    {
                        cout << "+";
                    }
                    cout << cur.second;
                }
                if (cur.first.first)
                {
                    cout << "n";
                    if (cur.first.first > 1)
                    {
                        cout << "^" << cur.first.first;
                    }
                }
                if (cur.first.second)
                {
                    cout << "m";
                    if (cur.first.second > 1)
                    {
                        cout << "^" << cur.first.second;
                    }
                }
                ++size;
            }
        }
        if (!size)
        {
            cout << 0;
        }
        cout << endl;
    }
    return 0;
}