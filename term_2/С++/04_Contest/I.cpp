#include <iostream>
#include <algorithm>
#include <string>
#include <vector>
#include <sstream>

int main()
{
    std::string num;
    std::getline(std::cin, num);
    std::vector<std::string> ans;
    while ((num.front() == '0' && num.size() > 1) || num.size() > 1)
    {
        //std::cout << num << " ";
        std::string tmp;
        switch (num.front()) {
            case '0':
            {
                num = num.substr(1);
                break;
            }
            case '1':
            {
                num = num.substr(1);
                for (unsigned int i = 0; i < num.size(); i++)
                {
                    tmp += '9';
                }
                ans.push_back(tmp);
                ans.emplace_back("1");
                break;
            }
            default:
            {
                int b = num.front() - 48;
                for (unsigned int i = 0; i < num.size() - 1; i++)
                {
                    tmp += '9';
                }
                ans.push_back(tmp);
                num.erase(0, 1);
                num.insert(0, std::to_string(b - 1));
                ans.emplace_back("1");
                break;
            }
        }
    }
    int n = (num.front() - 48);
    for (int i = 0; i < n; i++)
    {
        ans.emplace_back("1");
    }
    unsigned long long size = ans.size();
    std::cout << size << std::endl;
    std::sort(ans.begin(),ans.end(),std::greater<std::string>());
    for (unsigned long i = 0; i < size; i++)
    {
        std::cout << ans[i] << " ";
    }
    return 0;
}