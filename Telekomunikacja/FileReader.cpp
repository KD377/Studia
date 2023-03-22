//
// Created by Krzysztof Deka on 22/03/2023.
//

#include "FileReader.h"

#include <utility>

FileReader::FileReader(std::string filename, Algorytm *algorytm) : filename(std::move(filename)), algorytm(algorytm) {}

void FileReader::readfile() {
 std::ifstream infile(filename);
 if(infile.is_open()){
     int i=0;
     while (!infile.eof()){
         buffer.push_back(infile.get());
         i++;
     }
     infile.close();
 }
 else{
     std::cout<<"plik nie został odczytany"<<std::endl;
 }
}

void FileReader::print() {
    for(unsigned char i : buffer){
        std::cout<<i;
    }
}

void FileReader::writeEncodedFile() const {
    std::ofstream offile("../encoded.txt");
    if(offile.is_open()){

    } else{
        std::cout<<"error opening file";
    }
}
