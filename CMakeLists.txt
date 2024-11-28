cmake_minimum_required(VERSION 3.27)
project(ConnectFour)

set(CMAKE_CXX_STANDARD 20)

set(JAVA_HOME "/Users/ania/Library/Java/JavaVirtualMachines/openjdk-22/Contents/Home")
include_directories(${JAVA_HOME}/include)
include_directories(${JAVA_HOME}/include/darwin)

add_library(ConnectFour SHARED library.cpp)
