#pragma once

#include <vector>
#include "Value.h"
#include "Random.h"

class ExpressionGenerator //TODO: add more vars (in .cpp ~167 row)
{
public:
    ExpressionGenerator();

    std::string getExpression(size_t expressionSize);

    std::string generateConstant();

private:
    std::vector<std::pair<std::string, int>> mOperators;
    std::vector<std::string> mOperands;

    size_t mParenthesesBalance;
    std::string mExpression;
    size_t mIndex;

    bool isLastIteration;

    Random mRng;

    std::pair<std::string, int> generateOperator();
    std::string generateOperand();

    void restoreParenthesesBalance ();

    void pasteOpeningParentheses();
    void pasteClosingParentheses();

    void generateExpression();
    void generateWhiteSpace();

    void pasteString(const std::string& toPasteStr);

    void fillOperatorsContainer();
    void fillOperandsContainer();
};

