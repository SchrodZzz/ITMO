
#include "String.h"

String::String(const char* strData) : mSize(0)
{
    mSize = strlen(strData);
    mStr = new char[mSize + 1];
    strcpy(mStr, strData);
    mStr[mSize] = '\0';
}

String::String(const String& copyData) : mSize(copyData.mSize), mStr(new char[copyData.mSize + 1])
{
    strcpy(mStr, copyData.mStr);
}

String::String(char ch, size_t len) : mSize(len), mStr(new char[len + 1])
{
    for (size_t i = 0; i < mSize; ++i)
    {
        mStr[i] = ch;
    }
    mStr[mSize] = '\0';
}

String::~String()
{
    delete[] mStr;
}

void String::append(String addString)
{
    size_t newSize = mSize + addString.mSize;
    mStr = (char*) realloc(mStr, newSize + 1);
    for (size_t i = mSize; i < newSize; ++i)
    {
        mStr[i] = addString.mStr[i - mSize];
    }
    mStr[newSize] = '\0';
    mSize = newSize;
}

String& String::operator=(const String& copyData)
{
    mSize = copyData.mSize;
    mStr = (char*) realloc(mStr, static_cast<size_t>(mSize));
    for (size_t i = 0; i <= copyData.mSize; ++i)
    {
        mStr[i] = copyData.mStr[i];
    }

    return *this;
}

void String::appendBestPhraseInTheWorld()
{
    append(" : If ours lose, ours will beat yours");
}

void String::check()
{
    printf("-> len: %zu\n", mSize);
    for (size_t i = 0; mStr[i] != '\0'; ++i)
    {
        printf("%c", mStr[i]);
    }
    printf("\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
}