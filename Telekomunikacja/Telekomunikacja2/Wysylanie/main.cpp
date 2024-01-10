#include <iostream>
#include "Wysylanie.h"
int main() {
    LPCSTR sendToPort = "COM2";
    Wysylanie wysylanie;
    std::cout<<"Program wysylanie\n";
    wysylanie.sending(sendToPort);
    //C:\Users\krzys\OneDrive\Pulpit\a.txt
    return 0;
}
