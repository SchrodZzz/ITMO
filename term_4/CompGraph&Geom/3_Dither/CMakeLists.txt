cmake_minimum_required(VERSION 3.15)
project(3_Dither)

set(CMAKE_CXX_STANDARD 20)

add_compile_definitions(TEST)
add_executable(3_Dither main.cpp PNM.cpp PNM.hpp)