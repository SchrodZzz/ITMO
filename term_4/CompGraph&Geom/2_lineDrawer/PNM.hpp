#pragma once

#include <cmath>
#include <cstdio>
#include <stdexcept>
#include <string>

typedef unsigned char uchar;

struct PNM {
    PNM();
    explicit PNM(const std::string& inFileName);
    ~PNM();

    void drawLine(int x0, int y0, int x1, int y1, int brightness, double wd, double gamma);

    void save(const std::string& outFileName);

private:
    uchar* bitmap;
    int w, h, maxValue;

    void setPixelColor(int x0, int y0, double intensity, int brightness, double gamma);
    static double getColorIntensity(double err, double ed, double wd);
    static double makePositive(double val);

    size_t size();
};