#pragma once

#include "Person.h"

class Student : public Person
{
public:
    Student(std::string firstName, std::string secondName, Date birthday, bool isMale, int groupNumber);

    int getStudentNumber();

    void setStudentNumber(int number);

private:
    int mStudentNumber;
    std::string GetInfo() const;
};
