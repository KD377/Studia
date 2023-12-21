//
// Created by Krzysztof Deka on 19/03/2023.
//

#include <iostream>
#include "Algorytm.h"
#include <sstream>
#include <cmath>

Algorytm::Algorytm() = default;

void Algorytm::changeBit(int index) {
    if(index > 15){
        return;
    }
    if(information [index] == 0){
        information[index] = 1;
    }
    else if (information [index] == 1){
        information[index] =0;
    }
}

void Algorytm::setUpParityBits() {
    int suma;
    int iter = 0;
    for(int i = 0; i < 8 ; i++)
    {
        information[i] = message[i];
    }

    for(int i = 0; i < 8; i++){
        suma = 0;
        for(int j = 0; j < 8; j++){
            suma += message[j]*H[i][j];
        }
        if(suma % 2 == 0)
            information[8+iter] = 0;
        else{
            information[8+iter] = 1;
        }
        iter++;
    }

}

std::string Algorytm::getInformation() const {
    std::stringstream string;
    for (int i = 0; i < 16;i++) {
        string<<information[i];
    }
    return string.str();
}


void Algorytm::findError() {
    int E [8] = {};
    bool valid = true;
    bool same = true;
    bool multiple_error = false;
    int error = 16, error1 =16 , error2 =16;
    for(int i = 0; i < 8; i++){
        E[i] =0;
        for (int j = 0 ; j < 16 ; j++){
            E[i] += information[j] * H[i][j];
        }
        E[i] %= 2;
        if(E[i] == 1)
            valid = false;

    }
    if(!valid){
        for(int i = 0; i < 16; i++){
            same = true;
            for (int j = 0 ; j < 8 ; j++){
                if(E[j] != H[j][i]){
                    same = false;
                }
            }
            if(same){
                error = i;
                break;
            }
        }
        if(!same){
            bool correct;
            multiple_error = true;
            for (int i = 0; i < 15; i++){
                if(correct)
                    break;
                for(int k = i+1; k < 16; k++) {
                    correct = true;
                    for (int j = 0; j < 8; j++) {
                        if((H[j][i] + H[j][k])%2 != E[j]){
                            correct = false;
                        }
                    }
                    if(correct){
                        error1 = i;
                        error2 = k;
                        break;
                    }
                }
            }
        }
        if(multiple_error){
            changeBit(error1);
            changeBit(error2);
        }
        else{
            changeBit(error);
        }

    }
}


void Algorytm::resetMessage() {
    for(int i : message){
        message[i]=0;
    }
}

void Algorytm::uploadMessage(unsigned char character) {
    resetMessage();
    int i = 0;
    while (character > 0 && i < 8) {
        message.push_back( character % 2);
        character /= 2;
        i++;
    }
    while (i < 8) {
        message.push_back(0);
        i++;
    }
    std::reverse(message.begin(),message.end());
}

char Algorytm::downloadMessage() {
    char character = 0;
    for(int i = 0; i < 8; i++){
        character += information[i] * std::pow(2, 7 - i);
    }
    return character;
}

void Algorytm::vectorToTable(std::vector<int> vector) {
        for(int i = 0; i < 16; i++){
            information[i] = vector[i]-48;

        }

}



