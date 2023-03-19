//
// Created by Krzysztof Deka on 19/03/2023.
//

#ifndef TELEKOMUNIKACJA_ALGORYTM_H
#define TELEKOMUNIKACJA_ALGORYTM_H

#include <string>


class Algorytm {
private:
    int H[5][13] = {
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
            {1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0}
    };
    int message [8] = {};
    int information [13] = {};
    std::string printMessage(int tab[]);
public:
    explicit Algorytm(std::string message);

    void changeBit(int index);

    void setUpParityBits();

    void findSingleError();

};


#endif //TELEKOMUNIKACJA_ALGORYTM_H
