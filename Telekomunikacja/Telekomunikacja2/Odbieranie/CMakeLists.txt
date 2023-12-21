cmake_minimum_required(VERSION 3.21)
project(Odbieranie)

set(CMAKE_CXX_STANDARD 17)

add_executable(Odbieranie main.cpp SumaKontrolna.cpp SumaKontrolna.h Odbieranie.cpp Odbieranie.h Config.cpp Config.h)
