#include "../headers/TestUtil.h"

int getRandInt(unsigned int maxValue)
{
    return std::rand() % 2 == 0 ? getRandUInt(maxValue) : -getRandUInt(maxValue);
}

unsigned int getRandUInt(unsigned int maxValue)
{
    return std::rand() % maxValue;
}

std::string getRandStr(unsigned int strLength)
{
    std::string chars = "qwe!@#$rt2345yu%^&*iop[]as df()_+gh±§jkl;zx6789cv<>bnm,./10-=";
    std::string randStr(strLength, ' ');
    for (unsigned int i = 0; i < strLength; ++i)
    {
        randStr[i] = chars[getRandUInt((unsigned) chars.length())];
    }
    return randStr;
}


int** getRandomArray2d(unsigned int n, unsigned int m)
{
    int** arr = new int* [n];
    for (unsigned int i = 0; i < n; ++i)
    {
        arr[i] = new int[m];
        for (unsigned int j = 0; j < m; ++j)
        {
            arr[i][j] = getRandInt(500);
        }
    }
    return arr;
}


void generateSeed()
{
    srand(static_cast<unsigned int>(time(nullptr)));
}


void printBorder()
{
    for (unsigned int i = 0; i < 40; ++i)
    {
        printf("=");
    }
    std::cout << std::endl;
}

void printTestResult(unsigned int testsPassed, const char* testName)
{
    printf("%s : passed %d, failed %d", testName, testsPassed, TESTS_AMOUNT - testsPassed);
    std::cout << std::endl;
}