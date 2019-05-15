#include "../lib/ExpressionGenerator.h"

ExpressionGenerator::ExpressionGenerator()
{
    fillOperatorsContainer();
    fillOperandsContainer();

    mParenthesesBalance = 0;
    isLastIteration = false;
    mIndex = 0;
}

std::string ExpressionGenerator::getExpression(size_t expressionSize)
{
    mIndex = 0;
    isLastIteration = false;
    mExpression = std::string(2 * expressionSize + 15, ' ');
    generateExpression();
    return mExpression;
}

void ExpressionGenerator::generateExpression()
{
    if ((mIndex + 15 + (mExpression.size() / 2)) > mExpression.size())
    {
        if (isLastIteration)
        {
            return;
        }
        isLastIteration = true;
    }

    pasteOpeningParentheses();

    std::pair<std::string, int> curOperator = generateOperator();
    pasteString(curOperator.first);

    generateWhiteSpace();

    pasteString(generateOperand());

    if (curOperator.second == 1)
    {
        pasteClosingParentheses();
        restoreParenthesesBalance();
        return;
    }

    generateWhiteSpace();
    if (isLastIteration)
    {
        if (curOperator.second == 1)
        {
            pasteClosingParentheses();
        }
        else if (curOperator.second == 2)
        {
            generateWhiteSpace();
            pasteString(generateOperand());
            pasteClosingParentheses();
        }
    }
    generateExpression();
    restoreParenthesesBalance();
}

std::pair<std::string, int> ExpressionGenerator::generateOperator()
{
    return mOperators[mRng.getRandUnsInt(mOperators.size())];
}

std::string ExpressionGenerator::generateConstant()
{
    std::string tmp(6, ' ');
    char tmpChar = mRng.getRandUnsInt(10) + '0';
    tmp[0] = '(';
    tmp[1] = tmpChar;
    tmp[2] = ',';
    tmp[3] = ' ';
    tmp[4] = tmpChar;
    tmp[5] = ')';
    return tmp;
}

std::string ExpressionGenerator::generateOperand()
{
    unsigned tmp = mRng.getRandUnsInt(mOperands.size());
    if (mIndex < 7)
    {
        return mOperands[tmp];
    }
    return tmp ? mOperands[tmp - 1] : generateConstant();
}

void ExpressionGenerator::pasteString(const std::string& toPasteStr)
{
    for (auto curChar : toPasteStr)
    {
        mExpression[mIndex++] = curChar;
    }
}

void ExpressionGenerator::generateWhiteSpace()
{
    ++mIndex;
}

void ExpressionGenerator::pasteOpeningParentheses()
{
    mExpression[mIndex++] = '(';
    ++mParenthesesBalance;
}

void ExpressionGenerator::pasteClosingParentheses()
{
    mExpression[mIndex++] = ')';
    --mParenthesesBalance;
}

void ExpressionGenerator::restoreParenthesesBalance()
{
    while (mParenthesesBalance != 0)
    {
        pasteClosingParentheses();
    }
}

void ExpressionGenerator::fillOperatorsContainer()
{
    mOperators.emplace_back("<<=", 2);
    mOperators.emplace_back(">>=", 2);
    mOperators.emplace_back("+=", 2);
    mOperators.emplace_back("-=", 2);
    mOperators.emplace_back("*=", 2);
    mOperators.emplace_back("/=", 2);
    mOperators.emplace_back("%=", 2);
    mOperators.emplace_back("^=", 2);
    mOperators.emplace_back("&=", 2);
    mOperators.emplace_back("|=", 2);
    mOperators.emplace_back("||", 2);
    mOperators.emplace_back("&&", 2);
    mOperators.emplace_back("!=", 2);
    mOperators.emplace_back("==", 2);
    mOperators.emplace_back(">=", 2);
    mOperators.emplace_back("<=", 2);
    mOperators.emplace_back(">", 2);
    mOperators.emplace_back("<", 2);
    mOperators.emplace_back("=", 2);
    mOperators.emplace_back("+", 2);
    mOperators.emplace_back("-", 2);
    mOperators.emplace_back("=", 2);
    mOperators.emplace_back("/", 2);
    mOperators.emplace_back("%", 2);
    mOperators.emplace_back("^", 2);
    mOperators.emplace_back("|", 2);
    mOperators.emplace_back("&", 2);

    mOperators.emplace_back("[0]", 1);
    mOperators.emplace_back("[1]", 1);
    mOperators.emplace_back("++", 1);
    mOperators.emplace_back("--", 1);
    mOperators.emplace_back("~", 1);
    mOperators.emplace_back("!", 1);
}

void ExpressionGenerator::fillOperandsContainer() //TODO: have to fix pointer, but to lazy for that (-:
{
    mOperands.emplace_back("a");
//    mOperands.emplace_back("b");
//    mOperands.emplace_back("c");
}

