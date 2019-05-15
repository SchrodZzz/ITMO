#pragma once

#include <string>
#include <utility>
#include "Value.h"

class ValuePrefixParser
{
public:
    ValuePrefixParser(Value& a, Value& b, Value& c);
    ValuePrefixParser();

    Value parse(std::string expression);

private:
    Value* valA;
    Value* valB;
    Value* valC;

    Value* curVar;

    std::string mExpression;
    size_t mIndex;

    Value evaluateExpression();
    Value evaluate(const std::string& identifier, Value value);

    void skipWhiteSpaces();

    bool isDigitIdentifier(const std::string& prediction, size_t size, size_t firstDigitIdx);
    std::string getIdentifier();
    Value getValueConstant();
    Value getValue();

    bool curCharIsOpeningParentheses();
    bool curCharIsClosingParentheses();
    bool curCharIsParentheses();
    bool curCharIsVar();
    bool indexIsOutOfRange();
};
