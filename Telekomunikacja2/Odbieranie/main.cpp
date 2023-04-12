#include <iostream>
#include "windows.h"
#include "Odbieranie.h"

int main() {
    LPCSTR receiveFromPort = "COM3";
    Odbieranie odbieranie;
    std::cout<<"Program odbieranie\n";
    odbieranie.odbieranie(receiveFromPort);
//C:\Users\Kuba\Desktop
    return 0;
}
