#include "PNM.hpp"

PNM::PNM() = default;

PNM::PNM(const std::string& inFileName, int iCount, const ColorSpace& colorSpace) {
    this->colorSpace = colorSpace;
    if (iCount == 1) {
        bitmap = readFile(inFileName, '6');
    } else if (iCount == 3) {
        std::string fileName = inFileName.substr(0, inFileName.find_last_of('.'));
        uchar* buffer1 = readFile(fileName + "_1.pgm", '5');
        uchar* buffer2 = readFile(fileName + "_2.pgm", '5');
        uchar* buffer3 = readFile(fileName + "_3.pgm", '5');
        bitmap = new uchar[sizeB()];
        for (size_t i = 0; i < size(); i++) {
            bitmap[3 * i] = buffer1[i];
            bitmap[3 * i + 1] = buffer2[i];
            bitmap[3 * i + 2] = buffer3[i];
        }
    } else {
        throw std::runtime_error("Unknown iCount");
    }
}

uchar* PNM::readFile(const std::string& inFileName, const char& magicNum) {
    FILE* rf = fopen(inFileName.c_str(), "rb");
    if (!rf) {
        throw std::runtime_error("File opening failed");
    }
    char magicStr[2];
    fscanf(rf, "%s", magicStr);
    fscanf(rf, "%d", &this->w);
    fscanf(rf, "%d", &this->h);
    fscanf(rf, "%d\n", &this->maxValue);

    if (magicStr[0] != 'P' || magicStr[1] != magicNum) {
        throw std::runtime_error("Incorrect magic: Expected \"P6\"");
    }

    magic = (std::string(magicStr) == "P5") ? P5 : P6;

    auto tmp = new uchar[magic == P5 ? size() : sizeB()];
    fread(tmp, sizeof(uchar), magic == P5 ? size() : sizeB(), rf);
    fclose(rf);

    return tmp;
}

PNM::~PNM() {
    if (!this->bitmap) {
        delete[] bitmap;
    }
}

void PNM::convertInto(const ColorSpace& colorSpace) {
    if (this->colorSpace == colorSpace) { return; }
    colorSpaceToRGB();

    double Max, Min, H, S, L, C, V, M, Y, Cb, Cr, Co, Cg, Kr, Kg, Kb, R, G, B;
    switch (colorSpace) {
        case RGB:
            break;
        case HSL:
        case HSV:
            for (int i = 0; i < w * h; ++i) {
                R = bitmap[3 * i] / 255.0;
                G = bitmap[3 * i + 1] / 255.0;
                B = bitmap[3 * i + 2] / 255.0;
                Max = std::max(R, std::max(G, B));
                Min = std::min(R, std::min(G, B));
                V = Max;
                C = Max - Min;
                L = (Max + Min) / 2.0;
                if (C == 0) {
                    H = 0;
                } else if (V == R) {
                    H = (60.0) * ((G - B) / C);
                } else if (V == G) {
                    H = (60.0) * (2 + (B - R) / C);
                } else if (V == B) {
                    H = (60.0) * (4 + (R - G) / C);
                } else {
                    H = 0;
                }

                if (H < 0) {
                    H += 360;
                }

                if (colorSpace == HSV) {
                    S = (V == 0) ? 0 : C / V;
                    bitmap[3 * i + 2] = (uchar) (V * 255.0);
                }
                if (colorSpace == HSL) {
                    S = ((L == 0) || (L == 1)) ? 0 : ((V - L) / std::min(L, 1 - L));
                    bitmap[3 * i + 2] = (uchar) (L * 255.0);
                }
                bitmap[3 * i + 1] = (uchar) (S * 255.0);
                bitmap[3 * i] = (uchar) ((H / 360.0) * 255.0);

            }
            this->colorSpace = colorSpace;
            break;
        case YCbCr_601:
        case YCbCr_709:
            if (colorSpace == YCbCr_601) {
                Kr = 0.299;
                Kg = 0.587;
                Kb = 0.114;
                this->colorSpace = YCbCr_601;
            } else {
                Kr = 0.0722;
                Kg = 0.2126;
                Kb = 0.7152;
                this->colorSpace = YCbCr_709;
            }
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
            break;
        case YCoCg:
            for (int i = 0; i < w * h; ++i) {
                R = bitmap[3 * i] / 255.0;
                G = bitmap[3 * i + 1] / 255.0;
                B = bitmap[3 * i + 2] / 255.0;
                Y = R / 4 + G / 2 + B / 4;
                Co = R / 2 - B / 2;
                Cg = -R / 4 + G / 2 - B / 4;
                bitmap[3 * i] = (uchar) (Y * 255.0);
                bitmap[3 * i + 1] = (uchar) ((Co + 0.5) * 255.0);
                bitmap[3 * i + 2] = (uchar) ((Cg + 0.5) * 255.0);
            }
            this->colorSpace = YCoCg;
            break;
        case CMY:
            for (int i = 0; i < w * h; ++i) {
                R = bitmap[3 * i] / 255.0;
                G = bitmap[3 * i + 1] / 255.0;
                B = bitmap[3 * i + 2] / 255.0;
                C = 1 - R;
                M = 1 - G;
                Y = 1 - B;
                bitmap[3 * i] = (uchar) (C * 255.0);
                bitmap[3 * i + 1] = (uchar) (M * 255.0);
                bitmap[3 * i + 2] = (uchar) (Y * 255.0);
            }
            this->colorSpace = CMY;
            break;
        case Err:
            break;
    }
}

void PNM::save(const std::string& outFileName, int oCount) {
    if (oCount == 1) {
        FILE* wf = fopen(outFileName.c_str(), "wb");
        if (!wf) {
            throw std::runtime_error("File creating failed");
        }
        fprintf(wf, "%s\n%d %d\n%d\n", "P6", this->w, this->h, this->maxValue);
        fwrite(bitmap, sizeof(uchar), sizeB(), wf);
        fclose(wf);
    } else if (oCount == 3) {
        std::string fileName = outFileName.substr(0, outFileName.find_last_of('.'));
        for (size_t i = 0; i < 3; ++i) {
            FILE* wf = fopen((fileName + "_" + std::to_string(i + 1) + ".pgm").c_str(), "wb");
            auto buffer = new uchar[size()];
            for (size_t j = 0; j < size(); ++j) {
                buffer[j] = bitmap[3 * j + i];
            }
            fprintf(wf, "%s\n%d %d\n%d\n", "P5", this->w, this->h, this->maxValue);
            fwrite(buffer, sizeof(uchar), size(), wf);
            fclose(wf);
            delete[] buffer;
        }
    } else {
        throw std::runtime_error("Unknown oCount");
    }
}

void PNM::colorSpaceToRGB() {
    double H, S, L, C, H_D, X, m, R, G, B, Y, Cb, Cr, Co, Cg, M, Kr, Kg, Kb;
    switch (colorSpace) {
        case RGB:
            break;
        case HSL:
        case HSV:
            for (int i = 0; i < size(); ++i) {
                H = (bitmap[3 * i] / 255.0) * 360.0;
                S = bitmap[3 * i + 1] / 255.0;
                L = bitmap[3 * i + 2] / 255.0;
                H_D = H / 60;
                if (colorSpace == HSL) {
                    C = (1 - std::abs(2 * L - 1)) * S;
                    X = C * (1 - std::abs(fmod(H_D, 2) - 1));
                    m = L - C / 2.0;
                } else {
                    C = S * L;
                    X = C * (1.0 - std::abs(fmod(H_D, 2) - 1.0));
                    m = L - C;
                }

                m *= 255.0;
                if ((H_D >= 0) && (H_D <= 1)) {
                    bitmap[3 * i] = (uchar) (C * 255.0 + m);
                    bitmap[3 * i + 1] = (uchar) (X * 255.0 + m);
                    bitmap[3 * i + 2] = (uchar) m;
                }
                if ((H_D > 1) && (H_D <= 2)) {
                    bitmap[3 * i] = (uchar) (X * 255.0 + m);
                    bitmap[3 * i + 1] = (uchar) (C * 255.0 + m);
                    bitmap[3 * i + 2] = (uchar) m;
                }
                if ((H_D > 2) && (H_D <= 3)) {
                    bitmap[3 * i] = (uchar) m;
                    bitmap[3 * i + 1] = (uchar) (C * 255.0 + m);
                    bitmap[3 * i + 2] = (uchar) (X * 255.0 + m);
                }
                if ((H_D > 3) && (H_D <= 4)) {
                    bitmap[3 * i] = (uchar) m;
                    bitmap[3 * i + 1] = (uchar) (X * 255.0 + m);
                    bitmap[3 * i + 2] = (uchar) (C * 255.0 + m);
                }
                if ((H_D > 4) && (H_D <= 5)) {
                    bitmap[3 * i] = (uchar) (X * 255.0 + m);
                    bitmap[3 * i + 1] = (uchar) m;
                    bitmap[3 * i + 2] = (uchar) (C * 255.0 + m);
                }
                if ((H_D > 5) && (H_D <= 6)) {
                    bitmap[3 * i] = (uchar) (C * 255.0 + m);
                    bitmap[3 * i + 1] = (uchar) m;
                    bitmap[3 * i + 2] = (uchar) (X * 255.0 + m);
                }

            }
            break;
        case YCbCr_601:
        case YCbCr_709:
            if (colorSpace == YCbCr_601) {
                Kr = 0.299;
                Kg = 0.587;
                Kb = 0.114;
            } else {
                Kr = 0.0722;
                Kg = 0.2126;
                Kb = 0.7152;
            }
            for (int i = 0; i < size(); ++i) {
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
            break;
        case YCoCg:
            for (int i = 0; i < size(); ++i) {
                Y = bitmap[3 * i] / 255.0;
                Co = (bitmap[3 * i + 1] / 255.0) - 0.5;
                Cg = (bitmap[3 * i + 2] / 255.0) - 0.5;
                R = Y + Co - Cg;
                G = Y + Cg;
                B = Y - Co - Cg;
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
            break;
        case CMY:
            for (int i = 0; i < size(); ++i) {
                C = bitmap[3 * i] / 255.0;
                M = bitmap[3 * i + 1] / 255.0;
                Y = bitmap[3 * i + 2] / 255.0;
                R = 1 - C;
                G = 1 - M;
                B = 1 - Y;
                bitmap[3 * i] = (uchar) (R * 255.0);
                bitmap[3 * i + 1] = (uchar) (G * 255.0);
                bitmap[3 * i + 2] = (uchar) (B * 255.0);
            }
            break;
        case Err:
            break;
    }
    colorSpace = RGB;
}

size_t PNM::size() {
    return w * h;
}

size_t PNM::sizeB() {
    return w * h * 3;
}
