#include "PNM.hpp"

ColorSpace getColorSpace(const std::string& buffer) {
    if (buffer == "RGB") {
        return RGB;
    }
    if (buffer == "HSL") {
        return HSL;
    }
    if (buffer == "HSV") {
        return HSV;
    }
    if (buffer == "YCbCr.601") {
        return YCbCr_601;
    }
    if (buffer == "YCbCr.709") {
        return YCbCr_709;
    }
    if (buffer == "YCoCg") {
        return YCoCg;
    }
    if (buffer == "CMY") {
        return CMY;
    }
    return Err;
}

int main(int argc, char **argv) {

    std::string prefix;
#ifdef TEST
    prefix = "../tests/";
#endif

    if (argc != 11) {
        perror("Incorrect amount of args");
        return 1;
    }

    std::string inPath;
    std::string outPath;
    ColorSpace colorSpace;
    ColorSpace newColorSpace;
    int iCount;
    int oCount;
    for (size_t i = 1; i < argc; ++i) {
        std::string curArg = argv[i];
        if (curArg == "-f") {
            ++i;
            colorSpace = getColorSpace(argv[i]);
        } else if (curArg == "-t") {
            ++i;
            newColorSpace = getColorSpace(argv[i]);
        } else if (curArg == "-i") {
            ++i;
            iCount = std::stoi(argv[i]);
            ++i;
            inPath = prefix + argv[i];
        } else if (curArg == "-o") {
            ++i;
            oCount = std::stoi(argv[i]);
            ++i;
            outPath = prefix + argv[i];
        } else {
            perror("Unknown argument");
            return 1;
        }
    }

    if (colorSpace == Err || newColorSpace == Err) {
        perror("Unknown colorSpace");
        return -1;
    }

    PNM image;
    try {
        image = PNM(inPath, iCount, colorSpace);
    } catch (const std::bad_alloc&) {
        perror("Memory allocation error");
        return 1;
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    image.convertInto(newColorSpace);

    try {
        image.save(outPath, oCount);
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    return 0;
}