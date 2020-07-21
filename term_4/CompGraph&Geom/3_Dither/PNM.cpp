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

    error = new double[size()];
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
    if (!this->gradient) {
        delete[] gradient;
    }
}

void PNM::process(int gradient, int ditherType, int bit, double gamma) {
    this->bit = bit;
    this->gamma = gamma;

    if (gradient == 1) {
        drawGradient();
        isGradient = true;
    }

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
    gradient = new double[size()];
    for (size_t j = 0; j < w; j++) {
        double color = 255.0 / w * (double) j;
        for (size_t i = 0; i < h; i++) {
            gradient[i * w + j] = color;
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

double PNM::getPixel(int i, int j) {
    return isGradient ? gradient[i * w + j] : bitmap[i * w + j];
}

void PNM::drawPixel(int i, int j, uchar color) {
    double dColor = color / 255.0;
//    if (gamma > 0) {
//        dColor = std::pow(dColor, 1.0 / gamma);
//    } else {
//        if (dColor <= 0.0031308) {
//            dColor = 323.0 * dColor / 25.0;
//        } else {
//            dColor = (211 * pow(dColor, 5.0 / 12.0) - 11) / 200.0;
//        }
//    }
    bitmap[i * w + j] = (uchar) std::min(255.0, std::max(0.0, 255 * dColor));
}

void PNM::noDither() {
    for (size_t i = 0; i < h; ++i) {
        for (size_t j = 0; j < w; ++j) {
            drawPixel(i, j, changeBit(limitPixel(getPixel(i, j))));
        }
    }
}

void PNM::ordered() {
    double map[8][8] = {
            {0,  48, 12, 60, 3,  51, 15, 63},
            {32, 16, 44, 28, 35, 19, 47, 31},
            {8,  56, 4,  52, 11, 59, 7,  55},
            {40, 24, 36, 20, 43, 27, 39, 23},
            {2,  50, 14, 62, 1,  49, 13, 61},
            {34, 18, 46, 30, 33, 17, 45, 29},
            {10, 58, 6,  54, 9,  57, 5,  53},
            {42, 26, 38, 22, 41, 25, 37, 21}
    };
    for (size_t i = 0; i < 8; i++) {
        for (size_t j = 0; j < 8; j++) {
            map[i][j] = (map[i][j] + 0.5) / 64.0;
        }
    }
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            uchar current = changeBit(getPixel(i, j));
            if (current > getPixel(i, j)) {
                current = changeBit(limitPixel((double) getPixel(i, j) - (1u << (8 - bit))));
            }
            uchar next = changeBit(limitPixel((double) current + (1u << (8 - bit))));
            if (next == current) {
                current = changeBit(limitPixel((double) next - (1u << (8 - bit))));
            }
            double nextGammaValue = gammaCorrection(next / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0;
            double curGammaValue =
                    gammaCorrection(getPixel(i, j) / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0 +
                    (map[i%8][j%8] - 0.5) * nextGammaValue;
            drawPixel(i, j, curGammaValue >= nextGammaValue / 2.0 ? next : current);
        }
    }
}

void PNM::random() {
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            uchar current = changeBit(getPixel(i, j));
            if (current > getPixel(i, j)) {
                current = changeBit(limitPixel((double) getPixel(i, j) - (1u << (8 - bit))));
            }
            uchar next = changeBit(limitPixel((double) current + (1u << (8 - bit))));
            if (next == current) {
                current = changeBit(limitPixel((double) next - (1u << (8 - bit))));
            }
            double nextGammaValue = gammaCorrection(next / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0;
            double curGammaValue =
                    gammaCorrection(getPixel(i, j) / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0 +
                    ((double) rand() / RAND_MAX - 0.5) * nextGammaValue;
            drawPixel(i, j, curGammaValue >= nextGammaValue / 2.0 ? next : current);
        }
    }
}

void PNM::fs() {
    std::fill_n(error, size(), 0.0);
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar current = changeBit(limitPixel(getPixel(i, j)));
            if (current > limitPixel(getPixel(i, j))) {
                current = changeBit(limitPixel((double) limitPixel(getPixel(i, j)) - (1u << (8 - bit))));
            }
            uchar next = changeBit(limitPixel((double) current + (1u << (8 - bit))));
            if (next == current) {
                current = changeBit(limitPixel((double) next - (1u << (8 - bit))));
            }
            double nextGammaValue = gammaCorrection(next / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0;
            double curGammaValue =
                    gammaCorrection(getPixel(i, j) / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0 +
                    error[i * w + j];

            double err = curGammaValue >= nextGammaValue / 2.0 ? (double) (curGammaValue - nextGammaValue)
                                                               : (double) curGammaValue;

            setValue(i, j + 1, err * (7 / 16.0));
            setValue(i + 1, j - 1, err * (3 / 16.0));
            setValue(i + 1, j, err * (5 / 16.0));
            setValue(i + 1, j + 1, err * (1 / 16.0));

            drawPixel(i, j, curGammaValue >= nextGammaValue / 2.0 ? next : current);
        }
    }
}

void PNM::jjn() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar current = changeBit(limitPixel(getPixel(i, j)));
            if (current > limitPixel(getPixel(i, j))) {
                current = changeBit(limitPixel((double) limitPixel(getPixel(i, j)) - (1u << (8 - bit))));
            }
            uchar next = changeBit(limitPixel((double) current + (1u << (8 - bit))));
            if (next == current) {
                current = changeBit(limitPixel((double) next - (1u << (8 - bit))));
            }
            double nextGammaValue = gammaCorrection(next / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0;
            double curGammaValue =
                    gammaCorrection(getPixel(i, j) / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0 +
                    error[i * w + j];

            double err = curGammaValue >= nextGammaValue / 2.0 ? (double) (curGammaValue - nextGammaValue)
                                                               : (double) curGammaValue;

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

            drawPixel(i, j, curGammaValue >= nextGammaValue / 2.0 ? next : current);
        }
    }
}

void PNM::sierra() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar current = changeBit(limitPixel(getPixel(i, j)));
            if (current > limitPixel(getPixel(i, j))) {
                current = changeBit(limitPixel((double) limitPixel(getPixel(i, j)) - (1u << (8 - bit))));
            }
            uchar next = changeBit(limitPixel((double) current + (1u << (8 - bit))));
            if (next == current) {
                current = changeBit(limitPixel((double) next - (1u << (8 - bit))));
            }
            double nextGammaValue = gammaCorrection(next / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0;
            double curGammaValue =
                    gammaCorrection(getPixel(i, j) / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0 +
                    error[i * w + j];

            double err = curGammaValue >= nextGammaValue / 2.0 ? (double) (curGammaValue - nextGammaValue)
                                                               : (double) curGammaValue;

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

            drawPixel(i, j, curGammaValue >= nextGammaValue / 2.0 ? next : current);
        }
    }
}

void PNM::atkinson() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar current = changeBit(limitPixel(getPixel(i, j)));
            if (current > limitPixel(getPixel(i, j))) {
                current = changeBit(limitPixel((double) limitPixel(getPixel(i, j)) - (1u << (8 - bit))));
            }
            uchar next = changeBit(limitPixel((double) current + (1u << (8 - bit))));
            if (next == current) {
                current = changeBit(limitPixel((double) next - (1u << (8 - bit))));
            }
            double nextGammaValue = gammaCorrection(next / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0;
            double curGammaValue =
                    gammaCorrection(getPixel(i, j) / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0 +
                    error[i * w + j];

            double err = curGammaValue >= nextGammaValue / 2.0 ? (double) (curGammaValue - nextGammaValue)
                                                               : (double) curGammaValue;

            setValue(i, j + 1, err * (1 / 8.0));
            setValue(i, j + 2, err * (1 / 8.0));
            setValue(i + 1, j - 1, err * (1 / 8.0));
            setValue(i + 1, j, err * (1 / 8.0));
            setValue(i + 1, j + 1, err * (1 / 8.0));
            setValue(i + 1, j, err * (1 / 8.0));

            drawPixel(i, j, curGammaValue >= nextGammaValue / 2.0 ? next : current);
        }
    }
}

void PNM::halftone() {
    double map[4][4] = {
            {13, 4, 5,  8},
            {11, 3, 0,  6},
            {12, 2, 1,  7},
            {15, 9, 10, 14}
    };
    for (int i = 0; i < 4; i++) {
        for (size_t j = 0; j < 4; j++) {
            map[i][j] = (map[i][j] + 0.5) / 16.0;
        }
    }
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            uchar current = changeBit(getPixel(i, j));
            if (current > getPixel(i, j)) {
                current = changeBit(limitPixel((double) getPixel(i, j) - (1u << (8 - bit))));
            }
            uchar next = changeBit(limitPixel((double) current + (1u << (8 - bit))));
            if (next == current) {
                current = changeBit(limitPixel((double) next - (1u << (8 - bit))));
            }
            double nextGammaValue = gammaCorrection(next / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0;
            double curGammaValue =
                    gammaCorrection(getPixel(i, j) / 255.0) * 255.0 - gammaCorrection(current / 255.0) * 255.0 +
                    (map[i%4][j%4] - 0.5) * nextGammaValue;
            drawPixel(i, j, curGammaValue >= nextGammaValue / 2.0 ? next : current);
        }
    }
}

double PNM::gammaCorrection(double val) {
    if (gamma == 0) {
        if (val <= 0.04045) {
            return val / 12.92;
        } else {
            return std::pow((val + 0.055) / 1.055, 2.4);
        }
    } else {
        return std::pow(val, gamma);
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
