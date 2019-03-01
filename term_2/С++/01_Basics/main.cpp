#include "headers/header.h"

int main(int argc, char **argv)
{
    checker((argc > 0) ? argv[argc / 2] : "Mark", argc);
    builder(argc);

//    foo();
//    bar();
    return 0;
}