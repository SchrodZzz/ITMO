#include <sstream>

#include "../headers/TestUtil.h"

void testGetLine()
{
    unsigned int testCounter = 0;
    for (unsigned int testN = 0; testN < TESTS_AMOUNT; ++testN)
    {
        unsigned int size = getRandUInt(50);
        std::string testStr = getRandStr(size) + '\n';

        std::streambuf* orig = std::cin.rdbuf();
        std::istringstream input(testStr);
        std::cin.rdbuf(input.rdbuf());
        char* fooRes = getLine();
        std::cin.rdbuf(orig);

        bool passed = true;
        for (unsigned int i = 0; i < size; ++i)
        {
            if (fooRes[i] != testStr[i])
            {
                printf("\ttest №%d failed : %s != %s", testN, testStr.substr(0, testStr.size() - 1).c_str(), fooRes);
                std::cout << std::endl;
                passed = false;
                break;
            }
        }
        delete [] fooRes;
        testCounter += passed ? 1 : 0;
    }
    printTestResult(testCounter, "getLine");
}