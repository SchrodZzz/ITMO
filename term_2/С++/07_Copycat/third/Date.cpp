#include "Date.h"
#include <string>

Date::Date(int day, int month, int year)
{
    this->mDay = day;
    this->mMonth = month;
    this->mYear = year;
}

std::string Date::getInfo() const
{
    return std::to_string(this->mDay) + "/" + std::to_string(this->mMonth) + "/" + std::to_string(this->mYear);
}

std::ostream& operator<<(std::ostream& os, const Date& date)
{
    os << date.getInfo();
    return os;
}

int Date::getDay()
{
    return this->mDay;
}

int Date::getMonth()
{
    return this->mMonth;
}

int Date::getYear()
{
    return this->mYear;
}

void Date::setDay(int day)
{
    this->mDay = day;
}

void Date::setMounth(int month)
{
    this->mMonth = month;
}

void Date::setYear(int year)
{
    this->mYear = year;
}
