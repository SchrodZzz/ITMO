#include "../lib/Random.h"

Random::Random() : rd{}, mt{rd()}, dist{0, 1000}
{}

unsigned Random::getRandUnsInt(unsigned maxValue)
{
    return dist(mt) % maxValue;
}

bool Random::getRandBool()
{
    return getRandUnsInt(2);
}