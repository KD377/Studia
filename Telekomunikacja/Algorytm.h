//
// Created by Krzysztof Deka on 19/03/2023.
//

#ifndef TELEKOMUNIKACJA_ALGORYTM_H
#define TELEKOMUNIKACJA_ALGORYTM_H

#include <string>


class Algorytm {
private:
    int H[8][16] = {
            {0, 1, 1, 1, 1, 1, 1, 1,/**/  1, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 1, 1, 1, 1, 1,/**/  0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 1,/**/  0, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 1,/**/  0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 1, 1, 1,/**/  0, 0, 0, 0, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 0, 1, 1,/**/  0, 0, 0, 0, 0, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 0, 1,/**/  0, 0, 0, 0, 0, 0, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 0,/**/  0, 0, 0, 0, 0, 0, 0, 1},
    };
    int message [8] = {};
    int information [16] = {};
public:
    explicit Algorytm(std::string message);

    std::string printMessage() const;

    void changeBit(int index);

    void setUpParityBits();

    void findSingleError();

};


#endif //TELEKOMUNIKACJA_ALGORYTM_H
