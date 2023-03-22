
#include "Algorytm.h"
#include "FileReader.h"
using namespace std;

int main() {
    /*
    int choice;
    cout<<"Wybierz opcje: ";
    cout<<"1. Wykryj i skoryguj pojedynczy blad bitowy w wiadomosci 8-bitowej"<<endl<<"2. Wykryj i skoryguj podwojny blad bitowy w wiadomosci 8-bitowej"<<endl<<"3. Zad3"<<endl;
    cin>>choice;
     */
    auto *alg = new Algorytm();
    auto *reader = new FileReader("../test.txt",alg);
    reader->readfile();
    reader->writeEncodedFile();
    delete alg;
    delete reader;
    return 0;
}
