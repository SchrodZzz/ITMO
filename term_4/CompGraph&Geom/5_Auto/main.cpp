#include "PNM.hpp"

int main(int argc, char** argv) {

    std::string prefix;
#ifdef TEST
    prefix = "../tests/";
#endif

    if (argc != 4 && argc != 6) {
        perror("Incorrect amount of args");
        return 1;
    }

    std::string inPath = prefix + argv[1];
    std::string outPath = prefix + argv[2];
    unsigned transform = argv[3][0] - '0';

    int offset = 0;
    double multiplier = 0;
    if (argc == 6) {
        offset = std::stoi(argv[4]);
        multiplier = std::stod(argv[5]);
    }

    if (transform > 5
        || ((transform == 0 || transform == 1) && argc != 6)
        || (transform > 1 && argc == 6)
        || (argc == 6 && (std::abs(offset) > 255
                          || multiplier < 0
                          || multiplier - 1.0l / 255 < 0.001l
                          || multiplier - 255.0l > 0.001l))) {
        perror("Wrong arguments");
        return 1;
    }

    PNM image;
    try {
        image = PNM(inPath);
    } catch (const std::bad_alloc&) {
        perror("Memory allocation error");
        return 1;
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    try {
        image.process(transform, offset, multiplier);
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    try {
        image.save(outPath);
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    return 0;
}