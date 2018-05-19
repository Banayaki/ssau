cmake_minimum_required(VERSION 3.10)
project(lp5)

set(CMAKE_CXX_STANDARD 17)

add_executable(lp5 main.cpp Polygon.cpp Polygon.h Point.h Line.h Point.cpp Line.cpp)