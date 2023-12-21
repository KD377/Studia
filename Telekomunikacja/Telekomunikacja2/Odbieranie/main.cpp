#include <iostream>
#include "Odbieranie.h"

int main() {
    LPCSTR receiveFromPort = "COM2";
    Odbieranie odbieranie;
    std::cout<<"Program odbieranie\n";
    odbieranie.odbieranie(receiveFromPort);
    //"C:\Users\krzys\OneDrive\Pulpit\a.txt"
    return 0;
}
