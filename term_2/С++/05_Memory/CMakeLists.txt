cmake_minimum_required(VERSION 3.12)
project(05_Memory)

set(CMAKE_CXX_STANDARD 17)

add_executable(05_Memory main.cpp headers/Util.hpp sourses/Util.cpp sourses/Test.cpp headers/Test.h sourses/SwapMinTest.cpp headers/TestUtil.h sourses/TestUtil.cpp sourses/GetLineTest.cpp sourses/ResizeTest.cpp)