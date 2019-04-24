#include <iostream>
#include "String.cpp"

int main()
{
    String s1("Hello,");
    String s2(" world!");
    s1.check();
    s2.check();

    s1.append(s2);
    s1.check();
    s2.check();

    s1.appendBestPhraseInTheWorld();
    s1.check();

    s2.append(s2);
    s2.check();
    return 0;
}