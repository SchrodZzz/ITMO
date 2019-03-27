#include "../headers/Util.hpp"

void swapMin(int** arr, unsigned int n, unsigned int m)
{
    int minValue = std::numeric_limits<int>::max();
    int minElementRow = 0;
    for (unsigned int i = 0; i < n; ++i)
    {
        for (unsigned int j = 0; j < m; ++j)
        {
            if (arr[i][j] < minValue)
            {
                minValue = arr[i][j];
                minElementRow = i;
            }
        }
    }
    int* tmp = arr[0];
    arr[0] = arr[minElementRow];
    arr[minElementRow] = tmp;
}

char* getLine()
{
    int frameSize = 2;
    int cStringSize = 0;
    int stringResizeValue = frameSize;
    char* cString = (char*) malloc(frameSize * sizeof(char));
    char nextSymbol = (char) std::cin.get();
    while (nextSymbol != '\n')
    {
        *(cString + cStringSize) = nextSymbol;
        nextSymbol = (char) std::cin.get();
        ++cStringSize;
        if (cStringSize == stringResizeValue)
        {
            stringResizeValue += frameSize;
            cString = (char*) realloc(cString, stringResizeValue * sizeof(char));
        }
    }
    cString[cStringSize] = '\0';
    return cString;
}