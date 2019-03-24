#include <iostream>
#include <algorithm>
#include <string>
#include <vector>
#include <sstream>

int main()
{
    using namespace std;

    vector<string> nums;
    vector<int> partitionIndexes;

    int curInt;
    string curStr;
    string str;
    getline(cin, str);
    istringstream stream(str);
    while (stream >> curStr)
    {
        nums.push_back(curStr + " ");
    }
    getline(cin, str);
    istringstream strim(str);
    while (strim >> curInt)
    {
        partitionIndexes.push_back(curInt);
    }
    if (!(find(partitionIndexes.begin(), partitionIndexes.end(), 0) != partitionIndexes.end())) {
        partitionIndexes.insert(partitionIndexes.begin(), 0);
    }

    int pointer = (int)partitionIndexes.size();
    int current = (int)nums.size();
    while (--pointer >= 0)
    {
        for (int i = partitionIndexes[pointer]; i < current; ++i) {
            cout << nums[i];
        }
        current = partitionIndexes[pointer];

    }
    return 0;
}