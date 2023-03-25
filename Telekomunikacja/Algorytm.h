//
// Created by Krzysztof Deka on 19/03/2023.
//

#ifndef TELEKOMUNIKACJA_ALGORYTM_H
#define TELEKOMUNIKACJA_ALGORYTM_H

#include <string>
#include <vector>


class Algorytm {
private:
    int H[8][16] = {
            {1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1}
    };
    std::vector<int> message;
    int information [16] = {};
public:
    explicit Algorytm();

    std::string getInformation() const;

    void uploadMessage(unsigned char character);

    void resetMessage();

    void changeBit(int index);

    void setUpParityBits();

    void findError();

    char downloadMessage();

    void vectorToTable(std::vector<int> vector);

};


#endif //TELEKOMUNIKACJA_ALGORYTM_H
