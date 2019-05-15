#include "../lib/ValuePrefixParser.h"

ValuePrefixParser::ValuePrefixParser(Value& a, Value& b, Value& c)
{
    this->valA = &a;
    this->valB = &b;
    this->valC = &c;
    this->mIndex = 0;
    this->curVar = nullptr;
}

ValuePrefixParser::ValuePrefixParser()
{
    static Value tmpA = Value();
    static Value tmpB = Value();
    static Value tmpC = Value();
    this->valA = &tmpA;
    this->valB = &tmpB;
    this->valC = &tmpC;
    this->mIndex = 0;
    this->curVar = nullptr;
}


Value ValuePrefixParser::parse(std::string expression)
{
    this->mExpression = std::move(expression);
    this->mIndex = 0;
    return evaluateExpression();
}


Value ValuePrefixParser::evaluateExpression()
{
    std::string curIdentifier = getIdentifier();
    Value operand;
    skipWhiteSpaces();
    if (!curCharIsVar() && !isdigit(mExpression[mIndex]))
    {
        operand = evaluateExpression();
        skipWhiteSpaces();
    }
    else
    {
        operand = getValue();
    }
    if (curIdentifier.empty())
    {
        return operand;
    }
    return evaluate(curIdentifier, operand);
}

Value ValuePrefixParser::evaluate(const std::string& identifier, Value value)
{
    if (identifier == "+") return value + evaluateExpression(); //macros
    if (identifier == "-") return value - evaluateExpression();
    if (identifier == "*") return value * evaluateExpression();
    if (identifier == "/") return value / evaluateExpression();

    if (identifier == "^") return value ^ evaluateExpression();
    if (identifier == "|") return value | evaluateExpression();
    if (identifier == "&") return value & evaluateExpression();

    if (identifier == "==") return value == evaluateExpression();
    if (identifier == "!=") return value != evaluateExpression();
    if (identifier == ">") return value > evaluateExpression();
    if (identifier == "<") return value < evaluateExpression();
    if (identifier == ">=") return value >= evaluateExpression();
    if (identifier == "<=") return value <= evaluateExpression();
    if (identifier == "||") return value || evaluateExpression();
    if (identifier == "&&") return value && evaluateExpression();

    if (identifier == "~") return ~value;
    if (identifier == "!") return !value;

    if (identifier == "[0]")
    {
        value.setFakeValue(value[0]);
        return value;
    }
    if (identifier == "[1]")
    {
        value.setTrueValue(value[1]);
        return value;
    }

    if (identifier == "++") return ++*curVar;

    if (identifier == "--") return --*curVar;
    if (identifier == "+=")
    {
        Value* tmp = curVar;
        *tmp += evaluateExpression();
        return *tmp;
    }
    if (identifier == "-=")
    {
        Value* tmp = curVar;
        *tmp -= evaluateExpression();
        return *tmp;
    }
    if (identifier == "*=")
    {
        Value* tmp = curVar;
        *tmp *= evaluateExpression();
        return *tmp;
    }
    if (identifier == "/=")
    {
        Value* tmp = curVar;
        *tmp /= evaluateExpression();
        return *tmp;
    }
    if (identifier == "<<=")
    {
        Value* tmp = curVar;
        *tmp <<= evaluateExpression();
        return *tmp;
    }
    if (identifier == ">>=")
    {
        Value* tmp = curVar;
        *tmp >>= evaluateExpression();
        return *tmp;
    }
    if (identifier == "%=")
    {
        Value* tmp = curVar;
        *tmp %= evaluateExpression();
        return *tmp;
    }
    if (identifier == "^=")
    {
        Value* tmp = curVar;
        *tmp ^= evaluateExpression();
        return *tmp;
    }
    if (identifier == "|=")
    {
        Value* tmp = curVar;
        *tmp |= evaluateExpression();
        return *tmp;
    }
    if (identifier == "&=")
    {
        Value* tmp = curVar;
        *tmp &= evaluateExpression();
        return *tmp;
    }
    if (identifier == "=")
    {
        Value* tmp = curVar;
        *tmp = evaluateExpression();
        return *tmp;
    }
    return 0;
}


Value ValuePrefixParser::getValue()
{
    skipWhiteSpaces();
    char name = mExpression[mIndex];
    if (curCharIsVar())
    {
        ++mIndex;
    }
    switch (name)
    {
        case 'a':
        {
            this->curVar = valA;
            return *valA;
        }
        case 'b':
        {
            this->curVar = valB;
            return *valB;
        }
        case 'c':
        {
            this->curVar = valC;
            return *valC;
        }
        default:
        {
            Value tmp = getValueConstant();
            return tmp;
        }
    }
}

Value ValuePrefixParser::getValueConstant()
{
    skipWhiteSpaces();
    size_t initialIndex = mIndex;
    while (isdigit(mExpression[mIndex]) && !indexIsOutOfRange())
    {
        ++mIndex;
    }
    int x = std::stoi(mExpression.substr(initialIndex, mIndex - initialIndex));
    skipWhiteSpaces();
    mIndex++;
    skipWhiteSpaces();
    initialIndex = mIndex;
    while (isdigit(mExpression[mIndex]) && !indexIsOutOfRange())
    {
        ++mIndex;
    }
    int y = std::stoi(mExpression.substr(initialIndex, mIndex - initialIndex));
    return Value(x, y);
}

std::string ValuePrefixParser::getIdentifier()
{
    skipWhiteSpaces();
    size_t initialIndex = mIndex;
    while (!indexIsOutOfRange() && !isspace(mExpression[mIndex]) && !isdigit(mExpression[mIndex])
           && !curCharIsParentheses() && !curCharIsVar())
    {
        ++mIndex;
    }
    if (isDigitIdentifier("[0]", 3, 1)) return "[0]";
    else if (isDigitIdentifier("[1]", 3, 1)) return "[1]";
    else return mExpression.substr(initialIndex, mIndex - initialIndex);
}

bool ValuePrefixParser::isDigitIdentifier(const std::string& prediction, size_t size, size_t firstDigitIdx)
{
    if (mExpression.substr(mIndex - 1, size) == prediction)
    {
        mIndex += size - firstDigitIdx;
        return true;
    }
    return false;
}


bool ValuePrefixParser::curCharIsOpeningParentheses()
{
    return mExpression[mIndex] == '(';
}

bool ValuePrefixParser::curCharIsClosingParentheses()
{
    return mExpression[mIndex] == ')';
}

bool ValuePrefixParser::curCharIsParentheses()
{
    return curCharIsClosingParentheses() || curCharIsOpeningParentheses();
}

bool ValuePrefixParser::curCharIsVar()
{
    return mExpression[mIndex] == 'a' || mExpression[mIndex] == 'b' || mExpression[mIndex] == 'c';
}

bool ValuePrefixParser::indexIsOutOfRange()
{
    return mIndex >= mExpression.size();
}

void ValuePrefixParser::skipWhiteSpaces()
{
    while (!indexIsOutOfRange() && (isspace(mExpression[mIndex]) || curCharIsParentheses()))
    {
        ++mIndex;
    }
}