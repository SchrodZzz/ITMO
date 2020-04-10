#pragma once

#include <cmath>
#include <cstdio>
#include <stdexcept>
#include <string>
#include <random>

typedef unsigned char uchar;

struct PNM {
    PNM();
    explicit PNM(const std::string& inFileName);
    ~PNM();

    void process(int gradient, int ditherType, int bit, double gamma);

    void save(const std::string& outFileName);

private:
    uchar* bitmap;
    int w, h, maxValue;

    void gammaCorrection(double gamma);
    void drawGradient();
    void bitChange(int bit);

    void ordered();
    void random();
    void fs();
    void jjn();
    void sierra();
    void atkinson();
    void halftone();

    void setValue(int i, int j, double val);

    size_t size();
};