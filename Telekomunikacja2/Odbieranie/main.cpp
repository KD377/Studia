#include <iostream>
#include "Odbieranie.h"

int main() {
    LPCSTR receiveFromPort = "COM3";
    Odbieranie odbieranie;
    std::cout<<"Program odbieranie\n";
    odbieranie.odbieranie(receiveFromPort);
    return 0;
}
