#pragma once

#include <cstring>
#include <cstdio>
#include <cstdlib>

class String
{
public:

    String(const char* strData);

    String(const String& copyData);

    String(char ch, size_t len);

    ~String();

    void append(String addString);

    String& operator=(const String& copyData);

    void appendBestPhraseInTheWorld();

    void check();

private:
    size_t mSize;
    char* mStr;
};
