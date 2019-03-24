#include <iostream>

FILE* in = fopen("reverse.in", "r");
FILE* out = fopen("reverse.out", "w");

void rev (int n) {
    int cur;
    fscanf(in,"%d", &cur);
    if (--n != 0) {
        rev(n);
    }
    fprintf(out, "%d ", cur);
}

int main()
{
    int n;
    fscanf(in, "%d", &n);
    rev(n);
    return 0;
}