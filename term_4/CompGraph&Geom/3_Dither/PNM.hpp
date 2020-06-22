#pragma once

#include <cmath>
#include <cstdio>
#include <stdexcept>
#include <string>
#include <random>
#include <algorithm>

typedef unsigned char uchar;

struct PNM {
    PNM();
    explicit PNM(const std::string& inFileName);
    ~PNM();

    void process(int gradient, int ditherType, int bit, double gamma);

    void save(const std::string& outFileName);

private:
    double* error;
    uchar* bitmap;
    bool isGradient = false;
    double* gradient;
    int w, h, maxValue;

    uint bit;
    double gamma;

    void drawGradient();

    void noDither();
    void ordered();
    void random();
    void fs();
    void jjn();
    void sierra();
    void atkinson();
    void halftone();

    uchar changeBit(uchar pixel);
    static uchar limitPixel(double pixel);
    double gammaCorrection(double val);
    double getPixel(int i, int j);
    void drawPixel(int i, int j, uchar color = 255);

    void setValue(int i, int j, double val);

    size_t size();
};