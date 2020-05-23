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

    setMagic(magicStr, inFileName.substr(inFileName.find_last_of('.') + 1));

    bitmap = new uchar[size()];
    fread(bitmap, sizeof(uchar), size(), rf);

    fclose(rf);
}

PNM::~PNM() {
    if (!this->bitmap) {
        delete[] bitmap;
    }
}

void PNM::inverse() {
    uchar* p = bitmap;
    for (size_t i = 0; i < size(); i++) {
        *(p + i) = this->maxValue - *(p + i);
    }
}

void PNM::reverseHorizontally() {
    uchar* p1 = bitmap;
    uchar* p2 = bitmap + w * (magic == P5 ? 1 : 3) - (magic == P5 ? 1 : 3);
    for (size_t i = 0; i < w / 2; ++i) {
        for (size_t j = 0; j < h; ++j) {
            if (magic == P5) {
                std::swap(*(p1 + i + j * w), *(p2 - i + j * w));
            } else {
                for (size_t k = 0; k < 3; ++k) {
                    std::swap(*(p1 + 3 * i + 3 * j * w + k), *(p2 - 3 * i + 3 * j * w + k));
                }
            }
        }
    }
}

void PNM::reverseVertically() {
    uchar* p1 = bitmap;
    uchar* p2 = bitmap + size() - w * (magic == P5 ? 1 : 3);
    for (size_t i = 0; i < h / 2; ++i) {
        for (size_t j = 0; j < w; ++j) {
            if (magic == P5) {
                std::swap(*(p1 + i * w + j), *(p2 - i * w + j));
            } else {
                for (size_t k = 0; k < 3; ++k) {
                    std::swap(*(p1 + 3 * i * w + 3 * j + k), *(p2 - 3 * i * w + 3 * j + k));
                }
            }
        }
    }
}

void PNM::rotateLeft() {
    transpose();
    reverseVertically();
}

void PNM::rotateRight() {
    transpose();
    reverseHorizontally();
}

void PNM::transpose() {
    auto* tmp = new uchar[size()];
    uchar* p1 = tmp;
    uchar* p2 = bitmap;
    for (size_t i = 0; i < w; ++i) {
        for (size_t j = 0; j < h; ++j) {
            if (magic == P5) {
                *p1++ = *(p2 + i + j * w);
            } else {
                for (size_t k = 0; k < 3; ++k) {
                    *p1++ = *(p2 + 3 * i + 3 * j * w + k);
                }
            }
        }
    }

    std::swap(w, h);
    delete[] bitmap;
    bitmap = tmp;
}

void PNM::save(const std::string& outFileName) {
    FILE* wf = fopen(outFileName.c_str(), "wb");
    if (!wf) {
        throw std::runtime_error("File creating failed");
    }

    fprintf(wf, "%s\n%d %d\n%d\n", (this->magic == P5 ? "P5" : "P6"), this->w, this->h, this->maxValue);
    fwrite(bitmap, sizeof(uchar), size(), wf);

    fclose(wf);
}

void PNM::setMagic(const std::string& magicStr, const std::string& inFileExtension) {
    if (magicStr == "P5" && (inFileExtension == "pnm" || inFileExtension == "pgm")) {
        magic = P5;
    } else if (magicStr == "P6" && (inFileExtension == "pnm" || inFileExtension == "ppm")) {
        magic = P6;
    } else {
        if (magicStr == "P5" || magicStr == "P6") {
            throw std::runtime_error("Incorrect file extension. Expected .pnm or "
                                     + std::string(magicStr == "P5" ? ".pgm" : ".ppm")
                                     + "; found: ." + inFileExtension);
        } else {
            throw std::runtime_error(R"(Incorrect file magic. Expected "P5" or "P6"; found: )" + magicStr);
        }
    }
}

size_t PNM::size() {
    return w * h * (magic == P5 ? 1 : 3);
}
