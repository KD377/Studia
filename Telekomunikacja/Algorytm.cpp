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
    if(index > 12){
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

std::string Algorytm::printMessage() const {
    std::stringstream string;
    for (int i = 0; i < 13;i++) {
        string<<information[i];
    }
    return string.str();
}


void Algorytm::findSingleError() {
    int E [5] = {};
    bool valid = true;
    bool same = true;
    bool multiple_error = false;
    int error = 13, error1 =13 , error2 =13;
    for(int i = 0; i < 5; i++){
        E[i] =0;
        for (int j = 0 ; j < 13 ; j++){
            E[i] += information[j] * H[i][j];
        }
        E[i] %= 2;
        if(E[i] == 1)
            valid = false;
    }
    for(int i = 0; i < 5; i++){
        std::cout<<E[i]<<" ";
    }
    std::cout<<std::endl;
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
        if(!same){
            bool correct;
            multiple_error = true;
            for (int i = 0; i < 12; i++){
                if(correct)
                    break;
                for(int k = i+1; k < 13; k++) {
                    correct = true;
                    std::cout<<i<<" "<<k<<std::endl;
                    for (int j = 0; j < 5; j++) {
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
            std::cout<<"Otrzymana wiadomosc to:"<<printMessage()<<" zawiera błąd na bicie nr "<<error1<<", "<<error2<<std::endl;
            changeBit(error1);
            changeBit(error2);
            std::cout<<"Oto skorygowana wiadomość: "<<printMessage();
        }
        else{
            std::cout<<"Otrzymana wiadomosc to:"<<printMessage()<<" zawiera błąd na bicie nr "<<error<<std::endl;
            changeBit(error);
            std::cout<<"Oto skorygowana wiadomość: "<<printMessage();
        }

        }
        else{
            std::cout<<"Otrzymana wiadomosc jest poprawna!"<<std::endl;
        }
    }

