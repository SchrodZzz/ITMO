#include "PNM.hpp"

PNM::PNM() = default;

PNM::PNM(const std::string& inFileName) {
    FILE* rf = fopen(inFileName.c_str(), "rb");
    if (!rf) {
        throw std::runtime_error("File opening failed");
    }

    char magicStr[2];
    fscanf(rf, "%s\n%d %d\n%d\n", magicStr, &this->w, &this->h, &this->maxValue);

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

void PNM::drawLine(int x0, int y0, int x1, int y1, int brightness, double wd, double gamma) {
    int dx = abs(x1 - x0), sx = (x0 <= x1) ? 1 : -1;
    int dy = abs(y1 - y0), sy = (y0 <= y1) ? 1 : -1;
    int err = dx - dy, e2, x2, y2;
    double ed = dx + dy == 0 ? 1 : sqrt((double) (dx * dx) + (double) (dy * dy));

    for (wd = (wd + 1) / 2;;) {
        setPixelColor(x0, y0, getColorIntensity(abs(err - dx + dy), ed, wd), brightness, gamma);
        e2 = err;
        x2 = x0;
        if (2 * e2 >= -dx) {
            for (e2 += dy, y2 = y0; e2 < ed * wd && (y1 != y2 || dx > dy); e2 += dx) {
                setPixelColor(x0, y2 += sy, getColorIntensity(abs(e2), ed, wd), brightness, gamma);
            }
            if (x0 == x1) {
                break;
            }
            e2 = err;
            err -= dy;
            x0 += sx;
        }
        if (2 * e2 <= dy) {
            for (e2 = dx - e2; e2 < ed * wd && (x1 != x2 || dx < dy); e2 += dy) {
                setPixelColor(x2 += sx, y0, getColorIntensity(abs(e2), ed, wd), brightness, gamma);
            }
            if (y0 == y1) {
                break;
            }
            err += dx;
            y0 += sy;
        }
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

void PNM::setPixelColor(int x0, int y0, int intensity, int brightness, double gamma) {
    double bgColor = bitmap[x0 + y0 * this->w] / double(this->maxValue);
    double lineColor = bgColor * intensity / double(this->maxValue);
    double alpha = brightness / double(this->maxValue);
    bitmap[x0 + y0 * this->w] = this->maxValue * pow(
            (alpha * pow(lineColor, gamma)
             + (1 - alpha) * pow(bgColor, gamma)
            ), 1.0 / gamma);
}

int PNM::getColorIntensity(double err, double ed, double wd) {
    return makePositive(this->maxValue * (err / ed - wd + 1));
}

int PNM::makePositive(double val) {
    return (0 < val) ? int(val) : 0;
}

size_t PNM::size() {
    return w * h;
}
