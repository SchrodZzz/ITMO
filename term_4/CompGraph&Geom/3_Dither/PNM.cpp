#include "PNM.hpp"

PNM::PNM() = default;

PNM::PNM(const std::string& inFileName) {
    FILE* rf = fopen(inFileName.c_str(), "rb");
    if (!rf) {
        throw std::runtime_error("File opening failed");
    }

    char magicStr[2];
    fscanf(rf, "%s%d%d%d", magicStr, &this->w, &this->h, &this->maxValue);

    if (magicStr[0] != 'P' || magicStr[1] != '5') {
        throw std::runtime_error("Incorrect magic: Expected \"P5\"");
    }

    bitmap = new uchar[size()];
    fread(bitmap, sizeof(uchar), size(), rf);

    fclose(rf);
}

PNM::~PNM() {
    if (!this->bitmap) {
        delete[] bitmap;
    }
}

void PNM::process(int gradient, int ditherType, int bit, double gamma) {
    if (gradient == 1) {
        drawGradient();
    }
    bitChange(bit);
    gammaCorrection(gamma);

    switch (ditherType) {
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

void PNM::gammaCorrection(double gamma) {
    if (gamma == 0) {
        for (size_t i = 0; i < size(); i++) {
            double value = bitmap[i] / double(maxValue);
            if (value < 0.0031308) {
                bitmap[i] = value * 12.92 * maxValue;
            }
            else {
                bitmap[i] = maxValue * ((211.0 * std::pow (value, 0.4166) - 11.0) / 200.0);
            }
        }
    } else {
        double gammaCorrection = gamma / 1.0;
        for (size_t i = 0; i < size(); i++) {
            bitmap[i] = uchar(maxValue * pow(bitmap[i] / double(maxValue), gammaCorrection));
        }
    }
}

void PNM::drawGradient() {
    double step = double(w) / maxValue;
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            bitmap[i * w + j] = j / step;
        }
    }
}

void PNM::bitChange(int bit) {
    for (size_t i = 0; i < size(); i++) {
        unsigned int val1 = bitmap[i];
        unsigned int shift1 = 8 - bit;
        unsigned int shift2 = bit;
        unsigned int val2 = ((val1 >> shift1) << shift1);
        val1 = val2;
        for (int j = 7; j - bit >= 0; j -= bit) {
            val2 >>= shift2;
            val1 += val2;
        }
        bitmap[i] = val1;
    }
}

void PNM::ordered() {
    double coef = 1.0 / 64.0;
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
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            bitmap[i * w + j] = (bitmap[i * w + j] / (2.0 * maxValue) < coef * map[i % 8][j % 8] - 0.5) ? 0 : maxValue;
        }
    }
}

void PNM::random() {
    for (size_t i = 0; i < size(); i++) {
        uchar val = rand() % (maxValue + 1);
        bitmap[i] = bitmap[i] < val ? 0 : maxValue;
    }
}

void PNM::fs() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = bitmap[i * w + j];
            uchar newVal = (oldVal < (maxValue / 2)) ? 0 : maxValue;
            bitmap[i * w + j] = newVal;
            double err = oldVal - newVal;

            setValue(i, j + 1, err * (7 / 16.0));
            setValue(i + 1, j - 1, err * (3 / 16.0));
            setValue(i + 1, j, err * (5 / 16.0));
            setValue(i + 1, j + 1, err * (1 / 16.0));
        }
    }
}

void PNM::jjn() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = bitmap[i * w + j];
            uchar newVal = (oldVal < (maxValue / 2)) ? 0 : maxValue;
            bitmap[i * w + j] = newVal;
            double err = oldVal - newVal;

            setValue(i, j + 1, err * (7 / 48.0));
            setValue(i, j + 2, err * (5 / 48.0));
            setValue(i + 1, j - 2, err * (1 / 48.0));
            setValue(i + 1, j - 1, err * (5 / 48.0));
            setValue(i + 1, j, err * (7 / 48.0));
            setValue(i + 1, j + 1, err * (5 / 48.0));
            setValue(i + 1, j + 2, err * (1 / 48.0));
            setValue(i + 2, j - 2, err * (1 / 48.0));
            setValue(i + 2, j - 1, err * (1 / 48.0));
            setValue(i + 2, j, err * (5 / 48.0));
            setValue(i + 2, j + 1, err * (1 / 48.0));
            setValue(i + 2, j + 2, err * (1 / 48.0));
        }
    }
}

void PNM::sierra() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = bitmap[i * w + j];
            uchar newVal = (oldVal < (maxValue / 2)) ? 0 : maxValue;
            bitmap[i * w + j] = newVal;
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
        }
    }
}

void PNM::atkinson() {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uchar oldVal = bitmap[i * w + j];
            uchar newVal = (oldVal < (maxValue / 2)) ? 0 : maxValue;
            bitmap[i * w + j] = newVal;
            double err = oldVal - newVal;

            setValue(i, j + 1, err * (1 / 8.0));
            setValue(i, j + 2, err * (1 / 8.0));
            setValue(i + 1, j - 1, err * (1 / 8.0));
            setValue(i + 1, j, err * (1 / 8.0));
            setValue(i + 1, j + 1, err * (1 / 8.0));
            setValue(i + 1, j, err * (1 / 8.0));
        }
    }
}

void PNM::halftone() {
    double coef = 1.0 / 16.0;
    double map[4][4] = {
            {7,  13, 11, 4},
            {12, 16, 14, 8},
            {10, 15, 6,  2},
            {5,  9,  3,  1}
    };
    for (size_t i = 0; i < h; i++) {
        for (size_t j = 0; j < w; j++) {
            bitmap[i * w + j] = bitmap[i * w + j] / (2.0 * maxValue) < (coef * map[i % 4][j % 4] - 0.5) ? 0 : maxValue;
        }
    }
}

void PNM::setValue(int i, int j, double val) {
    if ((0 <= i) && (i < h)) {
        if ((0 <= j) && (j < w)) {
            int cur = bitmap[i * w + j] + int(val);
                bitmap[i * w + j] += val;
        }
    }
}

size_t PNM::size() {
    return w * h;
}
