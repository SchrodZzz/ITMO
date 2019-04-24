#include <utility>

#include "Student.h"

Student::Student(std::string firstName, std::string secondName, Date birthday, bool isMale, int studentNumber)
        : Person(std::move(firstName), std::move(secondName), birthday, isMale)
{
    this->mStudentNumber = studentNumber;
}

std::string Student::GetInfo() const
{
    return Person::GetInfo() + ", student number: " + std::to_string(mStudentNumber);
}

int Student::getStudentNumber()
{
    return mStudentNumber;
}

void Student::setStudentNumber(int number)
{
    mStudentNumber = number;
}
