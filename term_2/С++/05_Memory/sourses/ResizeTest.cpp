#include "../headers/TestUtil.h"

void testResize()
{
    unsigned int testCounter = 0;
    for (unsigned int testN = 0; testN < TESTS_AMOUNT; ++testN)
    {
        unsigned int n = getRandUInt(50) + 1;
        unsigned int m = getRandUInt(50) + 1;

        int* pArr = new int[n];
        int testArray[n];
        for (unsigned int i = 0; i < n; ++i)
        {
            *(pArr + i) = getRandInt(100);
            testArray[i] = *(pArr + i);
        }

        int* resizedArr = static_cast<int*>(resize(pArr, n, m));

        bool passed = true;
        int checkSize = std::min(n,m);
        for (unsigned int i = 0; i < checkSize; ++i)
        {
            if (resizedArr[i] != testArray[i])
            {
                printf("\ttest №%d failed : %d != %d", testN, resizedArr[i], testArray[i]);
                std::cout << std::endl;
                passed = false;
                break;
            }
        }
        delete [] resizedArr;
        testCounter += passed ? 1 : 0;
    }
    printTestResult(testCounter, "resize ");
}