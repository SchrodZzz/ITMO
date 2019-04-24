#pragma once

#include <iostream>

class Date
{
public:
    Date(int day, int month, int year);

    std::string getInfo() const;

    friend std::ostream& operator<<(std::ostream& os, const Date& at);

    int getDay();
    int getMonth();
    int getYear();
    void setDay(int day);
    void setMounth(int month);
    void setYear(int year);

private:
    int mDay;
    int mMonth;
    int mYear;
};
