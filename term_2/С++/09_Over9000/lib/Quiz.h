#pragma once

#include <unistd.h>
#include "Value.h"
#include "Random.h"
#include "ValuePrefixParser.h"
#include "ExpressionGenerator.h"

class Quiz //TODO: exceptions
{
public:
    Quiz();

    void start();

private:
    int mMod;
    int mBonus;

    unsigned mDifficulty;

    unsigned mRandAnswer;
    bool mIsTrueValueShowed;

    Value mExpressionResult;

    Value mValue;

    Random mRng;

    ValuePrefixParser mParser;
    ExpressionGenerator mExpressionGenerator;

    std::string mCurrentExpression;

    void generateValues();
    Value& generateValue();
};
