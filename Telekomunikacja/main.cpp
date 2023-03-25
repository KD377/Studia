
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
    int x;
    auto *alg = new Algorytm();
    auto *reader = new FileReader("../test.txt",alg);
    std::cout<<"1-Zakodowanie\n2.Odkodowanie";
    std::cin>>x;
    if( x == 1){
        reader->readfile();
        reader->writeEncodedFile();
    }
    else if(x == 2){
        reader->readEncodedFile();
    }
    delete alg;
    delete reader;
    return 0;
}
