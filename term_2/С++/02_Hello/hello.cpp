#include "mylib.h"

void greet()
{
#ifdef NAME
    printf("Hello, %s!\n", NAME);
#else
    printf("Oh, Hi Mark!!!\nP.S. until you won't define your name,"\
    "I will call you Mark\n");
#endif
}
