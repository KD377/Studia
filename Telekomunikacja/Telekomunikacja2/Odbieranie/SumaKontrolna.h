#ifndef WYSYLANIE_SUMAKONTROLNA_H
#define WYSYLANIE_SUMAKONTROLNA_H


class SumaKontrolna {
public:
    int calculateCRC(char *, int);
    char characterCRC(int, int);
    int check(int, int);
    int calculateCheckSum(const char *);
};


#endif //WYSYLANIE_SUMAKONTROLNA_H
