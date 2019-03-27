#include "../headers/TestUtil.h"

void testSwapMin()
{
    unsigned int testCounter = 0;
    for (unsigned int testN = 0; testN < TESTS_AMOUNT; ++testN)
    {
        unsigned int n = getRandUInt(50) + 1;
        unsigned int m = getRandUInt(50) + 1;

        int** p2pArr = getRandomArray2d(n, m);
        int testArray[n][m];
        for (unsigned int i = 0; i < n; ++i)
        {
            for (unsigned int j = 0; j < m; ++j)
            {
                testArray[i][j] = *(*(p2pArr + i) + j);
            }
        }

        swapMin(p2pArr, n, m);

        int testMin = INT_MAX_VALUE;
        int minElRow = 0;
        for (unsigned int i = 0; i < n; ++i)
        {
            for (unsigned int j = 0; j < m; ++j)
            {
                if (testArray[i][j] < testMin)
                {
                    testMin = testArray[i][j];
                    minElRow = i;
                }
            }
        }

        bool passed = true;
        for (unsigned int i = 0; i < m; ++i)
        {
            if (*(*(p2pArr + 0) + i) != testArray[minElRow][i])
            {
                printf("\ttest №%d failed : %d != %d", testN, *(*(p2pArr + 0) + i), testArray[minElRow][i]);
                std::cout << std::endl;
                passed = false;
                break;
            }
            if (*(*(p2pArr + minElRow) + i) != testArray[0][i])
            {
                printf("\ttest №%d failed : %d != %d", testN, *(*(p2pArr + minElRow) + i), testArray[0][i]);
                std::cout << std::endl;
                passed = false;
                break;
            }
        }
        for (unsigned int i = 0; i < n; ++i) {
            delete [] *(p2pArr + i);
        }
        delete [] p2pArr;
        testCounter += passed ? 1 : 0;
    }
    printTestResult(testCounter, "swapMin");
}