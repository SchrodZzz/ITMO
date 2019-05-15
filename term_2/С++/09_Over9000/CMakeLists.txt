cmake_minimum_required(VERSION 3.14)
project(09_Over9000)

set(CMAKE_CXX_STANDARD 17)

add_executable(09_Over9000 main.cpp lib/Quiz.h src/Quiz.cpp lib/Value.h src/Value.cpp lib/ValuePrefixParser.h src/ValuePrefixParser.cpp lib/ExpressionGenerator.h src/ExpressionGenerator.cpp lib/Random.h src/Random.cpp)