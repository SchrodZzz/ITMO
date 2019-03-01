cmake_minimum_required(VERSION 3.12)

project(01_Basics)
set(CMAKE_CXX_STANDARD 17)

add_executable(01_Basics main.cpp source/utilities.cpp headers/header.h headers/includes.h)