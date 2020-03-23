#include "PNM.hpp"

int main(int argc, char **argv) {

    std::string prefix;
#ifdef TEST
    prefix = "../tests/";
#endif

    if (argc != 10) {
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

    int lineBrightness = std::stoi(argv[3]);
    double lineWidth = std::stod(argv[4]);
    int x0 = std::stoi(argv[5]);
    int y0 = std::stoi(argv[6]);
    int x1 = std::stoi(argv[7]);
    int y1 = std::stoi(argv[8]);
    double gamma = std::stod(argv[9]);

    image.drawLine(x0, y0, x1, y1, lineBrightness, lineWidth, gamma);

    try {
        image.save(outPath);
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    return 0;
}
