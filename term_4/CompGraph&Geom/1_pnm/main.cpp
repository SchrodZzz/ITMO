#include "PNM.hpp"

int main(int argc, char **argv) {
    std::string prefix;
#ifdef TEST
    prefix = "../tests/";
#endif

    if (argc != 4) {
        std::puts("Incorrect amount of args: Expected <inputFileName> <outputFileName> <transformNum>");
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

    int cmd = std::stoi(argv[3]);
    switch (cmd) {
        case 0:
            image.inverse();
            break;
        case 1:
            image.reverseHorizontally();
            break;
        case 2:
            image.reverseVertically();
            break;
        case 3:
            image.rotateRight();
            break;
        case 4:
            image.rotateLeft();
            break;
        default:
            std::string msg = "Incorrect command. Expected 0-4; found: " + std::to_string(cmd);
            perror(msg.c_str());
    }

    try {
        image.save(outPath);
    } catch (const std::exception& e) {
        perror(e.what());
        return 1;
    }

    return 0;
}