#pragma once

#include <random>
#include "Value.h"

class Random
{
public:
    Random();

    unsigned getRandUnsInt(unsigned maxValue);
    bool getRandBool();

private:
    std::random_device rd;
    std::mt19937 mt;
    std::uniform_int_distribution<int> dist;
};