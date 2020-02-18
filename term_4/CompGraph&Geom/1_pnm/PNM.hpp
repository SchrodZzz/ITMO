#pragma once

#include <cstdio>
#include <stdexcept>
#include <string>

typedef unsigned char uchar;

struct PNM {
    PNM();
    explicit PNM(const std::string& inFileName);
    ~PNM();

    void inverse();
    void reverseHorizontally();
    void reverseVertically();

    void rotateLeft();
    void rotateRight();

    void transpose();

    void save(const std::string& outFileName);

private:
    uchar* bitmap;
    int w, h, maxValue;

    enum {P5, P6} magic;

    size_t size();
    void setMagic(const std::string& magicStr, const std::string& inFileExtension);
};
