
#include "Algorytm.h"
#include <iostream>
using namespace std;

int main() {
    /*
    int choice;
    cout<<"Wybierz opcje: ";
    cout<<"1. Wykryj i skoryguj pojedynczy blad bitowy w wiadomosci 8-bitowej"<<endl<<"2. Wykryj i skoryguj podwojny blad bitowy w wiadomosci 8-bitowej"<<endl<<"3. Zad3"<<endl;
    cin>>choice;
     */
    auto *alg = new Algorytm("11111111");
    alg ->setUpParityBits();
    alg->changeBit(3);
    alg->changeBit(4);
    alg->findSingleError();

    delete alg;
    return 0;
}
