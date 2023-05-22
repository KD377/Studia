#include "SumaKontrolna.h"
#include <math.h>
int SumaKontrolna::calculateCRC(char *wsk, int count) {
    int checkSum = 0;
    while(--count >= 0){
        checkSum ^= (int)*wsk++ << 8;
        for (int j = 0; j < 8; ++j)
        {
            if (checkSum & 0x8000)
            { checkSum = checkSum << 1 ^ 0x1021; }
            else
            { checkSum = checkSum << 1; }
        }
    }
    return (checkSum & 0xFFFF);
}

char SumaKontrolna::characterCRC(int n, int number) {
    int x;
    int binary[16] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    for(int i = 0; i < 16; i++)
    {
        x = n % 2;
        if(x == 1){
            n = (n - 1) / 2;
        }
        else if(x == 0){
            n = n / 2;
        }

        binary[15 - i] = x;
    }

    int result = 0;
    int j;
    if(number == 1){
        j = 7;
    }
    else if(number == 2){
        j = 15;
    }
    for(int i = 0; i < 8; i++){
        result += pow(2, i) * binary[j-i];
    }
    return (char)result;
}

int SumaKontrolna::calculateCheckSum(const char * blok) {
    int checksum = 0;
    for(int i = 0; i < 128; i++){
        checksum+=(unsigned char)blok[i];
    }
    checksum%=256;
    return checksum;
}





