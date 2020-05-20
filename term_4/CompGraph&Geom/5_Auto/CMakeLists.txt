cmake_minimum_required(VERSION 3.15)
project(5_Auto)

add_compile_definitions(TEST)
set(CMAKE_CXX_STANDARD 20)

add_executable(5_Auto main.cpp PNM.cpp PNM.hpp)
