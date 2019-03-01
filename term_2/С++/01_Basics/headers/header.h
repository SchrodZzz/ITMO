#include "includes.h"

void checker(std::string name, int attempts);

std::string get_name();


void greet();

void nope();

void builder(int height);

inline std::string &best_name()
{
    static std::string name = "Mark";
    return name;
}

void foo();

void bar();