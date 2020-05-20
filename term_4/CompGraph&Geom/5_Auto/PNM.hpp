#pragma once

#include <cmath>
#include <cstdio>
#include <stdexcept>
#include <string>
#include <algorithm>

typedef unsigned char uchar;

enum Magic {
    P5,
    P6
};

struct PNM {
    PNM();
    explicit PNM(const std::string& inFileName);
    void process(unsigned transform, int offset, double multiplier);
    ~PNM();


    void save(const std::string& outFileName);

private:
    uchar* bitmap;
    int w, h, maxValue;
    Magic magic;

    void colorSpaceToRGB();

    uchar* readFile(const std::string& inFileName);

    size_t size();
};