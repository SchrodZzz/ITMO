#include "PNM.hpp"

int main(int argc, char** argv) {

    std::string prefix;
#ifdef TEST
    prefix = "../tests/";
#endif

    if (argc != 7) {
        perror("Incorrect amount of args");
        return 1;
    }

    std::string inPath = prefix + argv[1];
    std::string outPath = prefix + argv[2];

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

    int gradient = std::stoi(argv[3]);
    int ditherType = std::stoi(argv[4]);
    int bit = std::stoi(argv[5]);
    double gamma = std::stod(argv[6]);

    image.process(gradient, ditherType, bit, gamma);

    try {
        image.save(outPath);
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    return 0;
}
