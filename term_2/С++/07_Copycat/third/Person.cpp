#include <utility>

#include "Person.h"

Person::Person(std::string firstName, std::string secondName, Date birthday, bool isMale) : mBirthday(birthday)
{
    this->mFirstName = std::move(firstName);
    this->mSecondName = std::move(secondName);
    this->mIsMale = isMale;
}

std::string Person::GetInfo() const
{
    return mSecondName + " " + mFirstName + ": " + (mIsMale ? "male" : "female") + " born on " + mBirthday.getInfo();
}

std::ostream& operator<<(std::ostream& os, const Person& person)
{
    os << person.GetInfo();
    return os;
}

Date Person::getBirthday()
{
    return mBirthday;
}

std::string Person::getFirstName()
{
    return mFirstName;
}

std::string Person::getSecondName()
{
    return mSecondName;
}

void Person::setBirthDay(Date date)
{
    mBirthday = date;
}

void Person::setFirstName(std::string word)
{
    mFirstName = std::move(word);
}

void Person::setSecondName(std::string word)
{
    mSecondName = std::move(word);
}

