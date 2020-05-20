#include "PNM.hpp"

PNM::PNM() = default;

PNM::PNM(const std::string& inFileName) {
    bitmap = readFile(inFileName);
}

uchar* PNM::readFile(const std::string& inFileName) {
    FILE* rf = fopen(inFileName.c_str(), "rb");
    if (!rf) {
        throw std::runtime_error("File opening failed");
    }
    char magicStr[2];
    fscanf(rf, "%s", magicStr);
    fscanf(rf, "%d", &this->w);
    fscanf(rf, "%d", &this->h);
    fscanf(rf, "%d\n", &this->maxValue);

    magic = (std::string(magicStr) == "P5") ? P5 : P6;

    auto tmp = new uchar[size()];
    fread(tmp, sizeof(uchar), size(), rf);
    fclose(rf);

    return tmp;
}

PNM::~PNM() {
    if (!this->bitmap) {
        delete[] bitmap;
    }
}

void PNM::process(unsigned transform, int offset, double multiplier) {
    if (magic == P5 && transform % 2 == 1) {
        throw std::runtime_error("Incorrect transform for the P5 file");
    }

    double Max, Min, H, S, L, C, V, M, Y, Cb, Cr, Co, Cg, Kr, Kg, Kb, R, G, B;
    if (transform % 2 == 1) {
        Kr = 0.299;
        Kg = 0.587;
        Kb = 0.114;
        for (int i = 0; i < w * h; ++i) {
            R = bitmap[3 * i] / 255.0;
            G = bitmap[3 * i + 1] / 255.0;
            B = bitmap[3 * i + 2] / 255.0;
            Y = Kr * R + Kg * G + Kb * B;
            Cb = 0.5 * ((B - Y) / (1.0 - Kb));
            Cr = 0.5 * ((R - Y) / (1.0 - Kr));
            bitmap[3 * i] = (uchar) (Y * 255.0);
            bitmap[3 * i + 1] = (uchar) ((Cb + 0.5) * 255.0);
            bitmap[3 * i + 2] = (uchar) ((Cr + 0.5) * 255.0);
        }
    }

    if (transform == 0) {
        for (size_t i = 0; i < size(); i++) {
            bitmap[i] = std::min(255.0, std::max(0.0, (((int) bitmap[i] - offset) * multiplier)));
        }
    } else if (transform == 1) {
        for (size_t i = 0; i < size(); i += 3) {
            bitmap[i] = std::min(255.0, std::max(0.0, (((int) bitmap[i] - offset) * multiplier)));
        }
    } else {
        auto brightness = new long long[256];
        if (transform % 2 == 0) {
            if (magic == P5) {
                for (size_t i = 0; i < size(); ++i) {
                    brightness[bitmap[i]] += 1;
                }
            } else {
                for (size_t i = 0; i < size(); i += 3) {
                    int rLum = (int)(0.2126l * bitmap[i] + 0.7152 * bitmap[i + 1] + 0.0722 * bitmap[i + 2]);
                    brightness[std::min(255, std::max(0, rLum))] += 1;
                }
            }
        } else {
            for (size_t i = 0; i < size(); i += 3) {
                brightness[bitmap[i]] += 1;
            }
        }

        if (transform == 2 || transform == 3) {
            int minIdx = 0;
            int maxIdx = 255;
            while (brightness[minIdx] == 0) minIdx++;
            while (brightness[maxIdx] == 0) maxIdx--;
            offset = minIdx;
            multiplier = 255.0 / (maxIdx - minIdx);

            if (transform == 2) {
                for (size_t i = 0; i < size(); i++) {
                    bitmap[i] = std::min(255.0, std::max(0.0, (((int) bitmap[i] - offset) * multiplier)));
                }
            } else {
                for (size_t i = 0; i < size(); i += 3) {
                    bitmap[i] = std::min(255.0, std::max(0.0, (((int) bitmap[i] - offset) * multiplier)));
                }
            }

        } else {
            auto ignoring_count = (long long) std::round(0.0039l * w * h);
            int minIdx = 0;
            int maxIdx = 255;
            while (minIdx <= 255 && (brightness[minIdx] == 0 || ignoring_count > 0)) {
                if (brightness[minIdx] == 0) {
                    minIdx++;
                    continue;
                }

                if (ignoring_count >= brightness[minIdx]) {
                    ignoring_count -= brightness[minIdx++];
                } else {
                    break;
                }
            }

            ignoring_count = (long long) std::round(0.0039l * w * h);
            while (maxIdx >= 0 && (brightness[maxIdx] == 0 || ignoring_count > 0)) {
                if (brightness[maxIdx] == 0) {
                    maxIdx--;
                    continue;
                }
                if (ignoring_count >= brightness[minIdx]) {
                    ignoring_count -= brightness[minIdx--];
                } else {
                    break;
                }
            }

            offset = minIdx;
            multiplier = 255.0 / (maxIdx - minIdx);

            if (transform == 4) {
                for (size_t i = 0; i < size(); i++) {
                    bitmap[i] = std::min(255.0, std::max(0.0, (((int) bitmap[i] - offset) * multiplier)));
                }
            } else {
                for (size_t i = 0; i < size(); i += 3) {
                    bitmap[i] = std::min(255.0, std::max(0.0, (((int) bitmap[i] - offset) * multiplier)));
                }
            }
        }
    }

    if (transform % 2 == 1) {
        Kr = 0.299;
        Kg = 0.587;
        Kb = 0.114;
        for (size_t i = 0; i < w * h; i++) {
            Y = bitmap[3 * i] / 255.0;
            Cb = (bitmap[3 * i + 1] / 255.0) - 0.5;
            Cr = (bitmap[3 * i + 2] / 255.0) - 0.5;
            R = (Y + Cr * (2.0 - 2.0 * Kr));
            G = (Y - (Kb / Kg) * (2.0 - 2.0 * Kb) * Cb - (Kr / Kg) * (2.0 - 2.0 * Kr) * Cr);
            B = (Y + (2.0 - 2.0 * Kb) * Cb);
            if (R < 0) {
                R = 0;
            }
            if (G < 0) {
                G = 0;
            }
            if (B < 0) {
                B = 0;
            }
            if (R > 1) {
                R = 1;
            }
            if (G > 1) {
                G = 1;
            }
            if (B > 1) {
                B = 1;
            }
            bitmap[3 * i] = (uchar) (R * 255.0);
            bitmap[3 * i + 1] = (uchar) (G * 255.0);
            bitmap[3 * i + 2] = (uchar) (B * 255.0);
        }
    }
}

void PNM::save(const std::string& outFileName) {
    if (this->magic == P6) {
        FILE* wf = fopen(outFileName.c_str(), "wb");
        if (!wf) {
            throw std::runtime_error("File creating failed");
        }
        fprintf(wf, "%s\n%d %d\n%d\n", "P6", this->w, this->h, this->maxValue);
        fwrite(bitmap, sizeof(uchar), size(), wf);
        fclose(wf);
    } else if (this->magic == P5) {
        FILE* wf = fopen(outFileName.c_str(), "wb");
        if (!wf) {
            throw std::runtime_error("File creating failed");
        }
        fprintf(wf, "%s\n%d %d\n%d\n", "P5", this->w, this->h, this->maxValue);
        fwrite(bitmap, sizeof(uchar), size(), wf);
        fclose(wf);
    }
}

size_t PNM::size() {
    return w * h * ((magic == P5) ? 1 : 3);
}
