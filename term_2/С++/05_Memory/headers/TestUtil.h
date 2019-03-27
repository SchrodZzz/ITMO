#include "Util.hpp"

#define INT_MAX_VALUE 2147483647;
#define TESTS_AMOUNT 7071

int getRandInt(unsigned int maxValue);

unsigned int getRandUInt(unsigned int maxValue);

std::string getRandStr(unsigned strLength);


int** getRandomArray2d(unsigned int n, unsigned int m);


void generateSeed();


void printBorder();

void printTestResult(unsigned int testsPassed, const char* testName);


void testSwapMin();

void testGetLine();

void testResize();