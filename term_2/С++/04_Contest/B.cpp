#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <set>

int main()
{
    unsigned long n;
    unsigned long cnt = 0;
    std::ifstream cin("broken-keyboard.in");
    std::ofstream cout("broken-keyboard.out");
    cin >> n;
    std::vector<std::string> words(n);
    for (unsigned int i = 0; i < n; ++i)
    {
        cin >> words[i];
    }

    for (unsigned int i = 0; i < n; ++i)
    {
        std::set<char> helper;
        unsigned long long listIdx = 0;
        unsigned long long wordIdx = 0;
        for (unsigned int j = 0; j < words[i].length(); ++j)
        {
            if (helper.find(words[i][j]) == helper.end())
            {
                helper.insert(words[i][j]);
                while (true)
                {
                    if (listIdx > i || (wordIdx < words[listIdx].length() && words[listIdx][wordIdx] == words[i][j]))
                    {
                        break;
                    }
                    ++listIdx;
                }
                if (listIdx == i)
                {
                    cout << "YES" << std::endl;
                    ++cnt;
                    break;
                }
                ++wordIdx;
            }
        }
        if (cnt != i+1)
        {
            cout << "NO" << std::endl;
            ++cnt;
        }
    }
    return 0;
}