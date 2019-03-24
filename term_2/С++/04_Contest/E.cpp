#include <iostream>

long long gcd(long long a, long long b)
{
    while (a > 0 && b > 0)
        if (a > b)
            a %= b;
        else
            b %= a;
    return a + b;
}

int main()
{
    FILE* in = fopen("gcd.in", "r");
    FILE* out = fopen("gcd.out", "w");
    long long a, b;
    fscanf(in, "%lld %lld", &a, &b);
    fprintf(out, "%lld", gcd(a, b));
    return 0;
}
