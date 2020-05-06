cmake_minimum_required(VERSION 3.15)
project(4_Colors)

add_compile_definitions(TEST)
set(CMAKE_CXX_STANDARD 20)

add_executable(4_Colors main.cpp PNM.cpp PNM.hpp)