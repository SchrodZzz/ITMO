#include <algorithm>
#include <iostream>
#include <string>
#include <limits>

void swapMin(int** arr, unsigned int n, unsigned int m);

char* getLine();

template<typename T>
void* resize(T* arr, unsigned int size, unsigned int newSize)
{
    T* newArr = new T[newSize];
    int copySize = std::min(size, newSize);
    for (int i = 0; i < copySize; i++)
    {
        *(newArr + i) = *(arr + i);
    }
    delete [] arr;
    return newArr;
}