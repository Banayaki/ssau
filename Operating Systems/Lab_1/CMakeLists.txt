cmake_minimum_required(VERSION 3.13)
project(Lab_1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17 -Wall")

set(HEADERS headers/GeneralHeader.h headers/polynom.h headers/PolynomResolver.h headers/Root.h)
set(SOURCE_EXE sources/main.cpp)
set(SOURCE_LIB sources/polynom.cpp helpers/Executor.cpp helpers/LinkedList.cpp helpers/MyVector.cpp sources/PolynomResolver.cpp sources/Root.cpp)

add_library(lib STATIC ${SOURCE_LIB} ${HEADERS})
add_executable(Lab_1 ${SOURCE_EXE})
target_link_libraries(Lab_1 lib)