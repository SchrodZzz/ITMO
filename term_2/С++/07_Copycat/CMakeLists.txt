cmake_minimum_required(VERSION 3.12)
project(07_Copycat)

set(CMAKE_CXX_STANDARD 17)

add_executable(07_Copycat first/PlaneFigure.h first/Rect.cpp first/Rect.h second/main.cpp first/main.cpp third/Date.cpp third/Date.h third/Person.cpp third/Student.cpp third/Person.h third/Student.h third/main.cpp)