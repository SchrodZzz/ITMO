#include <iostream>
#include "PNM.hpp"

PNM::PNM() = default;

PNM::PNM(const std::string& inFileName) {
    FILE* rf = fopen(inFileName.c_str(), "rb");
    if (!rf) {
        throw std::runtime_error("File opening failed");
    }

    char magicStr[2];
    fscanf(rf, "%s", magicStr);
    fscanf(rf, "%d", &this->w);
    fscanf(rf, "%d", &this->h);
    fscanf(rf, "%d", &this->maxValue);
    fgetc(rf);

    if (magicStr[0] != 'P' || magicStr[1] != '5') {
        throw std::runtime_error("Incorrect magic: Expected \"P5\"");
    }

    error = new int[size()];
    bitmap = new uchar[size()];
    fread(bitmap, sizeof(uchar), size(), rf);

    fclose(rf);
}

PNM::~PNM() {
    if (!this->bitmap) {
        delete[] bitmap;
    }
    if (!this->error) {
        delete[] error;
    }
}

void PNM::preGamma() {
    for (size_t i = 0; i < size(); ++i) {
        double dPixel = bitmap[i] / 255.0;
        if (gamma > 0) {
            dPixel = std::pow(dPixel, gamma);
        } else {
            if (dPixel <= 0.04045) {
                dPixel = 25.0 * dPixel / 323;
            } else {
                dPixel = pow((200 * dPixel + 11) / 211.0, 12.0 / 5.0);
            }
        }
        bitmap[i] = limitPixel(255 * dPixel);
    }
}

void PNM::process(int gradient, int ditherType, int bit, double gamma) {
    this->bit = bit;
    this->gamma = gamma;

    if (gradient == 1) {
        drawGradient();
    }

    preGamma();

    switch (ditherType) {
        case 0:
            noDither();
            break;
        case 1:
            ordered();
            break;
        case 2:
            random();
            break;
        case 3:
            fs();
            break;
        case 4:
            jjn();
            break;
        case 5:
            sierra();
            break;
        case 6:
            atkinson();
            break;
        case 7:
            halftone();
            break;
        default:;
    }
}

void PNM::save(const std::string& outFileName) {
    FILE* wf = fopen(outFileName.c_str(), "wb");
    if (!wf) {
        throw std::runtime_error("File creating failed");
    }

    fprintf(wf, "%s\n%d %d\n%d\n", "P5", this->w, this->h, this->maxValue);
    fwrite(bitmap, sizeof(uchar), size(), wf);

    fclose(wf);
}

void PNM::drawGradient() {
    double step = double(w) / maxValue;
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            bitmap[i * w + j] = j / step;
        }
    }
}

uchar PNM::changeBit(uchar pixel) {
    uchar tmp = pixel & (((1u << bit) - 1) << (8 - bit));
    pixel = 0;
    for (unsigned i = 0; i < 8 / bit + 1; ++i) {
        pixel = pixel | ((uchar) (tmp >> bit * i));
    }
    return pixel;
}

void PNM::drawPixel(int i, int j, uchar color) {
    double dColor = color / 255.0;
    if (gamma > 0) {
        dColor = std::pow(dColor, 1.0 / gamma);
    } else {
        if (dColor <= 0.0031308) {
            dColor = 323.0 * dColor / 25.0;
        } else {
            dColor = (211 * pow(dColor, 5.0 / 12.0) - 11) / 200.0;
        }
    }
    bitmap[i * w + j] = (uchar) std::min(255.0, std::max(0.0, 255 * dColor));
}

void PNM::noDither() {
    for (size_t i = 0; i < h; ++i) {
        for (int j = 0; j < w; ++j) {
            drawPixel(i, j, changeBit(bitmap[i * w + j]));
        }
    }
}

void PNM::ordered() {
    double map[8][8] = {
            {-0.5,     0.25,     -0.3125,  0.4375,   -0.453125, 0.296875,  -0.265625, 0.484375},
            {0.0,      -0.25,    0.1875,   -0.0625,  0.046875,  -0.203125, 0.234375,  -0.015625},
            {-0.375,   0.375,    -0.4375,  0.3125,   -0.328125, 0.421875,  -0.390625, 0.359375},
            {0.125,    -0.125,   0.0625,   -0.1875,  0.171875,  -0.078125, 0.109375,  -0.140625},
            {-0.46875, 0.28125,  -0.28125, 0.46875,  -0.484375, 0.265625,  -0.296875, 0.453125},
            {0.03125,  -0.21875, 0.21875,  -0.03125, 0.015625,  -0.234375, 0.203125,  -0.046875},
            {-0.34375, 0.40625,  -0.40625, 0.34375,  -0.359375, 0.390625,  -0.421875, 0.328125},
            {0.15625,  -0.09375, 0.09375,  -0.15625, 0.140625,  -0.109375, 0.078125,  -0.171875}
    };
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            drawPixel(i, j, changeBit(limitPixel(bitmap[i * w + j] + map[i % 8][j % 8] * 255 / bit)));
        }
    }
}

void PNM::random() {
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            double val = (double) rand() / (RAND_MAX) * 255 / bit - (255 / (2.0 * bit));
            drawPixel(i, j, changeBit(limitPixel(bitmap[i * w + j] + val)));
        }
    }
}

void PNM::fs() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = limitPixel(bitmap[i * w + j] + error[i * w + j]);
            uchar newVal = changeBit(oldVal);
            double err = oldVal - newVal;

            setValue(i, j + 1, err * (7 / 16.0));
            setValue(i + 1, j - 1, err * (3 / 16.0));
            setValue(i + 1, j, err * (5 / 16.0));
            setValue(i + 1, j + 1, err * (1 / 16.0));

            drawPixel(i, j, newVal);
        }
    }
}

void PNM::jjn() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = limitPixel(bitmap[i * w + j] + error[i * w + j]);
            uchar newVal = changeBit(oldVal);
            double err = oldVal - newVal;

            setValue(i, j + 1, err * (7 / 48.0));
            setValue(i, j + 2, err * (5 / 48.0));
            setValue(i + 1, j - 2, err * (3 / 48.0));
            setValue(i + 1, j - 1, err * (5 / 48.0));
            setValue(i + 1, j, err * (7 / 48.0));
            setValue(i + 1, j + 1, err * (5 / 48.0));
            setValue(i + 1, j + 2, err * (3 / 48.0));
            setValue(i + 2, j - 2, err * (1 / 48.0));
            setValue(i + 2, j - 1, err * (3 / 48.0));
            setValue(i + 2, j, err * (5 / 48.0));
            setValue(i + 2, j + 1, err * (3 / 48.0));
            setValue(i + 2, j + 2, err * (1 / 48.0));

            drawPixel(i, j, newVal);
        }
    }
}

void PNM::sierra() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = limitPixel(bitmap[i * w + j] + error[i * w + j]);
            uchar newVal = changeBit(oldVal);
            double err = oldVal - newVal;

            setValue(i, j + 1, err * (5 / 32.0));
            setValue(i, j + 2, err * (3 / 32.0));
            setValue(i + 1, j - 2, err * (1 / 16.0));
            setValue(i + 1, j - 1, err * (1 / 8.0));
            setValue(i + 1, j, err * (5 / 32.0));
            setValue(i + 1, j + 1, err * (1 / 8.0));
            setValue(i + 1, j + 2, err * (1 / 16.0));
            setValue(i + 2, j - 1, err * (1 / 16.0));
            setValue(i + 2, j, err * (3 / 32.0));
            setValue(i + 2, j + 1, err * (1 / 16.0));

            drawPixel(i, j, newVal);
        }
    }
}

void PNM::atkinson() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = limitPixel(bitmap[i * w + j] + error[i * w + j]);
            uchar newVal = changeBit(oldVal);
            double err = oldVal - newVal;

            setValue(i, j + 1, err * (1 / 8.0));
            setValue(i, j + 2, err * (1 / 8.0));
            setValue(i + 1, j - 1, err * (1 / 8.0));
            setValue(i + 1, j, err * (1 / 8.0));
            setValue(i + 1, j + 1, err * (1 / 8.0));
            setValue(i + 1, j, err * (1 / 8.0));

            drawPixel(i, j, newVal);
        }
    }
}

void PNM::halftone() {
    double map[4][4] = {
            {0.375,  0.75,   0.625,  0.1875},
            {0.6875, 0.9375, 0.8125, 0.4375},
            {0.5625, 0.875,  0.3125, 0.0625},
            {0.25,   0.5,    0.125,  0.0}
    };
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            drawPixel(i, j, changeBit(limitPixel(bitmap[i * w + j] + map[i % 4][j % 4] * 255 / bit)));
        }
    }
}

void PNM::setValue(int i, int j, double val) {
    if ((0 <= i) && (i < h)) {
        if ((0 <= j) && (j < w)) {
            error[i * w + j] += val;
        }
    }
}

uchar PNM::limitPixel(double pixel) {
    return (uchar) std::min(255.0, std::max(0.0, pixel));;
}

size_t PNM::size() {
    return w * h;
}
