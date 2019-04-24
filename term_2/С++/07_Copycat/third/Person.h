#pragma once

#include "Date.h"
#include <iostream>

class Person
{

public:
    Person(std::string firstName, std::string secondName, Date birthday, bool isMale);

    friend std::ostream& operator<<(std::ostream& os, const Person& at);

    Date getBirthday();

    std::string getFirstName();
    std::string getSecondName();

    void setBirthDay(Date date);
    void setFirstName(std::string word);
    void setSecondName(std::string word);

protected:
    virtual std::string GetInfo() const;

private:
    Date mBirthday;
    bool mIsMale;
    std::string mFirstName;
    std::string mSecondName;
};
