
#include "Algorytm.h"
#include "FileReader.h"
using namespace std;

int main() {
    int x;
    auto *alg = new Algorytm();
    auto *reader = new FileReader("../test.txt",alg);
    while(x != 3) {
        std::cout << "1-Zakodowanie\n2.Odkodowanie\n3.Zakoncz program\nWybor:";
        std::cin >> x;
        if (x == 1) {
            reader->readfile();
            reader->writeEncodedFile();
            cout<<"Zakodowano!\n\n";
        } else if (x == 2) {
            reader->readEncodedFile();
            cout<<"Odkodowano!\n\n";
        }
    }
    delete alg;
    delete reader;
    return 0;
}
