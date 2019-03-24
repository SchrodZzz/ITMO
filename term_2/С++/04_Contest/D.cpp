#include <iostream>
#include <string>
#include <vector>
#include <fstream>

int main()
{
    std::ifstream cin("crosswords.in");
    std::ofstream cout("crosswords.out");
    std::string words[4];
    for (int i = 0; i < 4; i++)
    {
        cin >> words[i];
    }
    int ans = 0;
    for (int i = 0; i < 4; i++)
    {
        for (int j = i + 1; j < 4; j++)
        {
            std::pair<int, int> one = std::make_pair(i, j);
            std::pair<int, int> rest = std::make_pair(-1, -1);
            for (int k = 0; k < 4; k++)
            {
                if (k != i && k != j)
                {
                    rest.first == -1 ? rest.first = k : rest.second = k;
                }
            }
            int dif[4] = {0, 0, 1, 0};
            int dis[4] = {0, 1, 0, 1};
            for (int k = 0; k < 4; k++)
            {
                if (dif[k])
                {
                    std::swap(one.first, one.second);
                }
                if (dis[k])
                {
                    std::swap(rest.first, rest.second);
                }
                for (int x = 0; static_cast<unsigned int>(x + 1) < words[one.first].size(); x++)
                {
                    for (int y = (x + 1); static_cast<unsigned int>(y) < words[one.first].size(); y++)
                    {
                        for (int shift = static_cast<int>(y - words[one.second].size() + 1); shift <= x; shift++)
                        {
                            char c1 = words[one.second][x - shift];
                            char c2 = words[one.second][y - shift];
                            int tmp, temp;
                            for (int l = 0; static_cast<unsigned int>(l + 1) < std::min(words[rest.first].size(), words[rest.second].size()); l++)
                            {
                                tmp = temp = 0;
                                for (int z = 0; static_cast<unsigned int>(z + l + 1) < words[rest.first].size(); z++)
                                {
                                    if (words[one.first][x] == words[rest.first][z] &&
                                        c1 == words[rest.first][z + l + 1])
                                    {
                                        tmp++;
                                    }
                                }
                                for (int q = 0; static_cast<unsigned int>(q + l + 1) < words[rest.second].size(); q++)
                                {
                                    if (words[one.first][y] == words[rest.second][q] &&
                                        c2 == words[rest.second][q + l + 1])
                                    {
                                        temp++;
                                    }
                                }
                                ans += tmp * temp;
                            }
                        }
                    }
                }
            }
        }
    }
    cout << ans;
    return 0;
}
