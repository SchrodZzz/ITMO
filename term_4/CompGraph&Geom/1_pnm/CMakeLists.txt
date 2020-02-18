cmake_minimum_required(VERSION 3.15)
project(1_pnm)

set(CMAKE_CXX_STANDARD 20)

add_compile_definitions(TEST)
add_executable(1_pnmParser main.cpp PNM.cpp PNM.hpp)