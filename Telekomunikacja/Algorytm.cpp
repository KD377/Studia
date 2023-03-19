//
// Created by Krzysztof Deka on 19/03/2023.
//

#include <iostream>
#include "Algorytm.h"
#include <sstream>

Algorytm::Algorytm(std::string message) {
    for(int i = 0; i < message.length(); i++){
        this->message[i] = message[i] - 48;
    }
}

void Algorytm::changeBit(int index) {
    if(information [index-1] == 0){
        information[index-1] = 1;
    }
    else if (information [index-1] == 1){
        information[index-1] =0;
    }
}

void Algorytm::setUpParityBits() {
    int suma = 0;
    int iter = 0;
    for(int i = 0; i < 8 ; i++)
    {
        information[i] = message[i];
    }

    for(int i = 0; i < 5; i++){
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

std::string Algorytm::printMessage(int tab[]) {
    std::stringstream string;
    for (int i = 0; i < 13;i++) {
        string<<tab[i];
    }
    return string.str();
}

void Algorytm::findSingleError() {
    int E [5] = {};
    bool valid = true;
    bool same = true;
    int error;
    for(int i = 0; i < 5; i++){
        E[i] =0;
        for (int j = 0 ; j < 13 ; j++){
            E[i] += information[j] * H[i][j];
        }
        E[i] %= 2;
        if(E[i] == 1)
            valid = false;
    }
    if(!valid){
        for(int i = 0; i < 13; i++){
            same = true;
            for (int j = 0 ; j < 5 ; j++){
                if(E[j] != H[j][i]){
                    same = false;
                }
            }
            if(same){
                error = i;
                break;
            }
        }
        std::cout<<"Otrzymana wiadomosc to:"<<printMessage(information)<<" zawiera błąd na bicie nr "<<error<<std::endl;
        changeBit(error);
        std::cout<<"Oto skorygowana wiadomość: "<<printMessage(information);
        }
        else{
            std::cout<<"Otrzymana wiadomosc jest poprawna!"<<std::endl;
        }
    }
