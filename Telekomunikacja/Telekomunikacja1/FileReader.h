//
// Created by Krzysztof Deka on 22/03/2023.
//

#ifndef TELEKOMUNIKACJA_FILEREADER_H
#define TELEKOMUNIKACJA_FILEREADER_H

#include "Algorytm.h"
#include <vector>
#include <fstream>
#include <iterator>
#include <iostream>

class FileReader {
private:
    std::string filename;

    Algorytm *algorytm;

    std::vector<unsigned char> buffer;

public:
    FileReader(std::string filename, Algorytm *algorytm);

    void readfile();

    void writeEncodedFile() const;

    void print();

    void readEncodedFile();

    void writeFinalFile(std::vector<char> result);
};


#endif //TELEKOMUNIKACJA_FILEREADER_H
