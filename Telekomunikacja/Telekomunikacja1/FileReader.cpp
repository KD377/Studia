//
// Created by Krzysztof Deka on 22/03/2023.
//

#include "FileReader.h"

#include <utility>

FileReader::FileReader(std::string filename, Algorytm *algorytm) : filename(std::move(filename)), algorytm(algorytm) {}

void FileReader::readfile() {
 std::ifstream infile(filename);
 if(infile.is_open()){
     while (!infile.eof()){
         buffer.push_back(infile.get());


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
    std::ofstream offile("../encoded.txt",std::ofstream::out);
    if(offile.is_open()){
        for(int i = 0; i < buffer.size() - 1; i++){
            algorytm->uploadMessage(buffer.at(i));
            algorytm->setUpParityBits();
            offile<<algorytm->getInformation();
        }
        offile.close();
    }
    else {
        std::cout<<"error opening file";
    }
}

void FileReader::readEncodedFile() {
    std::ifstream infile("../encoded.txt");
    if(infile.is_open()){
        buffer.clear();
        while (!infile.eof()) {
            buffer.push_back(infile.get());

        }
        infile.close();
    }
    else{
        std::cout<<"plik nie został odczytany"<<std::endl;
    }
    std::vector<int> vector;
    std::vector<char> result;
    for(int k = 0; k < buffer.size()-1; k+=16) {
        for (int i = 0; i < 16; i++) {
            vector.push_back(buffer[i+k]);
        }
        algorytm->vectorToTable(vector);
        algorytm->findError();
        result.push_back(algorytm->downloadMessage());
        writeFinalFile(result);
        vector.clear();
    }

}

void FileReader::writeFinalFile(std::vector<char> result) {
    std::ofstream offile("../final.txt",std::ofstream::out);
    if(offile.is_open()){
        for(int i = 0; i < result.size(); i++) {
            offile << result[i];
        }
            offile.close();
        }
    else {
        std::cout<<"error opening file";
    }
}
