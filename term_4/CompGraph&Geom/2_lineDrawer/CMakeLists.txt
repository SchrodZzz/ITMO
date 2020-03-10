cmake_minimum_required(VERSION 3.15)
project(2_lineDrawer)

set(CMAKE_CXX_STANDARD 20)

add_compile_definitions(TEST)
add_executable(2_lineDrawer main.cpp PNM.cpp PNM.hpp)