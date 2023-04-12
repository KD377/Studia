#include <iostream>
#include "windows.h"
#include "Wysylanie.h"
int main() {
    LPCSTR sendToPort = "COM2";
    Wysylanie wysylanie;
    std::cout<<"Program wysylanie\n";
    wysylanie.sending(sendToPort);
    return 0;
}
