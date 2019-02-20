cmake_minimum_required(VERSION 3.13)
project(lab_3)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17 -Wall")

set(SOURCE_EXE Checker.cpp Executor.cpp)
add_executable(lab_3 ${SOURCE_EXE})