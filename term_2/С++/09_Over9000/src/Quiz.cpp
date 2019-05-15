#include "../lib/Quiz.h"

Quiz::Quiz()
{
    mMod = 0;
    mBonus = 0;
    mRandAnswer = 0;
    mDifficulty = 0;
    mIsTrueValueShowed = false;
    mExpressionResult = Value();
    mParser = ValuePrefixParser(*Value(), *Value(), *Value());
}

void Quiz::start()
{
    std::cout << "Welcome to quiz!!!\nPlease, choose the difficulty level (1..3) : ";
    std::cin >> this->mDifficulty;
    if (mDifficulty > 3 || mDifficulty < 1)
    {
        std::cout << "Incorrect difficulty level : available only 3 levels - 1,2,3" << std::endl;
        return;
    }
    generateValues();
    std::string expressionStr = mExpressionGenerator.getExpression(10 * mDifficulty);
    mExpressionResult = mParser.parse(expressionStr);
    mRandAnswer = mRng.getRandBool()
                  ? mExpressionResult.getTrueValue()
                  : mExpressionResult.getFakeValue();
    mIsTrueValueShowed = mRandAnswer == mExpressionResult.getTrueValue();
    std::cout << "Generated expression is : " << expressionStr << std::endl;
    std::cout << "Is this expression equal to ";
    std::cout << mRandAnswer << " ? (Y/N)" << std::endl;
    while (true)
    {
        std::string cmd;
        std::cin >> cmd;
        if (cmd == "-help")
        {
            std::cout << "\t-stop : to stop the game\n\teval (expr) : to eval expression\n\tans Y/N : to ans\n";
        }
        else if (cmd == "-stop")
        {
            break;
        }
        else if (cmd == "eval")
        {
            std::string expressionToEval;
            getline(std::cin, expressionToEval);
            std::cout << "\tresult : " << mParser.parse(expressionToEval) << std::endl;
        }
        else if (cmd == "ans")
        {
            std::cout << "And .";
            for (size_t i = 0; i < 3; ++i)
            {
                std::cout.flush();
                usleep(1000000);
                std::cout << '.';
            }
            char ans;
            std::cin >> ans;
            if ((mIsTrueValueShowed && ans == 'Y') || (!mIsTrueValueShowed && ans == 'N'))
            {
                std::cout << " !!!YOU WIN!!!" << std::endl;
            }
            else
            {
                std::cout << " you lose, true value is " << mExpressionResult.getTrueValue() << std::endl;
            }
            break;
        }
        else
        {
            std::cout << "Incorrect command : type -help to see list of valid commands" << std::endl;
        }
    }
}


void Quiz::generateValues()
{
    unsigned initialValue = mRng.getRandUnsInt(mDifficulty + 1);
    this->mMod = mRng.getRandUnsInt(3 * mDifficulty + 1) + 3;
    this->mBonus = mRng.getRandUnsInt(10 * mDifficulty + 1);
    this->mValue = Value(initialValue, initialValue);
    this->mParser = ValuePrefixParser(generateValue(), generateValue(), generateValue());
}

Value& Quiz::generateValue()
{
    unsigned tmp1 = mRng.getRandUnsInt(mDifficulty + 1);
    unsigned tmp2 = tmp1;
    if (mRng.getRandBool())
    {
        tmp2 %= mMod;
    }
    else
    {
        tmp2 += mBonus;
    }
    static Value val = Value(tmp1, tmp2);
    return *val;
}