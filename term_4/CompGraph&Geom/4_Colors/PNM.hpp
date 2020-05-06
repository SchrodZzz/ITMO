#pragma once

#include <cmath>
#include <cstdio>
#include <stdexcept>
#include <string>

typedef unsigned char uchar;

enum ColorSpace {
    RGB,
    HSL,
    HSV,
    YCbCr_601,
    YCbCr_709,
    YCoCg,
    CMY,
    Err
};

enum Magic {
    P5,
    P6
};

struct PNM {
    PNM();
    explicit PNM(const std::string& inFileName, int iCount, const ColorSpace& colorSpace);
    void convertInto(const ColorSpace& colorSpace);
    ~PNM();


    void save(const std::string& outFileName, int oCount);

private:
    uchar* bitmap;
    int w, h, maxValue;
    Magic magic;

    ColorSpace colorSpace;

    void colorSpaceToRGB();

    uchar* readFile(const std::string& inFileName, const char& magicNum);

    size_t size();
    size_t sizeB();
};