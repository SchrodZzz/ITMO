#include "../headers/header.h"

void checker(std::string name, int attempts)
{
    for (int i = 0; i < attempts; ++i)
    {
        if (name == best_name())
        {
            greet();
            break;
        }
        else
        {
            nope();
            name = get_name();
        }
    }
}

std::string get_name()
{
    std::string guess;
    printf("Enter the best name in the world: ");
    std::cin >> guess;
    return guess;
}

void greet()
{
    printf("Oh, hi %s!\n", best_name().c_str());
}

void nope()
{
    printf("nope\n");
}

void builder(int height)
{
    while ((height--) > 0)
    {
        for (int i = 0; i < height; ++i)
        {
            printf("#");
        }
        printf("\n");
    }
}

void foo()
{
    int x = 2;
    printf("&x = %p\n", &x);
}

void bar()
{
    int y = 3;
    int z = 5;
    int r = 6;
    printf("&y = %p and &z = %p and &r = %p \n\n &y < &z? %d or &y > &z? %d", &y, &z, &r, &y < &z, &y > &z);
}
