#include <iostream>
#include <fstream>
#include "Wysylanie.h"
#include "SumaKontrolna.h"
#include "Config.h"
const char SOH = 0x01;
const char EOT = 0x04;
const char ACK = 0x06;
const char NAK = 0x15;
const char CAN = 0x18;
const char C = 0x43;
int NumberOfBytes = 1;
unsigned long BufferSize = sizeof(char);




int Wysylanie::sending(LPCTSTR port) {
    Config configure;
    HANDLE portHandle = configure.config(port);
    char filePath[255];
    std::cout<<"Podaj nazwe pliku do wyslania\n";
    std::cin >> filePath;
    std::cout<<"Oczekiwanie na polaczenie\n";
    char buffer;
    std::string checkSum;
    bool transmission = false;
    for(int i = 0; i < 6; i++){
        ReadFile(portHandle, &buffer, NumberOfBytes, &BufferSize, NULL);

        if(buffer == C){
            checkSum = "CRC";
            std::cout<<"Wybrano CRC\n";
            transmission = true;
            break;
        }
        else if(buffer == NAK){
            std::cout<<"wybrano NAK\n";
            checkSum = "Algebraiczna";
            transmission = true;
            break;
        }
    }

    if(!transmission){
        std::cout<<"Blad w transmisji\n";
        return 0;
    }
    std::ifstream file;
    file.open(filePath, std::ios::binary);
    char dataBlock[128];
    int numberOfDataBlock = 1;
    bool rightDataBlock;
    while(!file.eof()){
        //26 w ascii to [substitute]
        for(int i = 0; i < 128; i++){
            dataBlock[i] = (char) 26;
        }
        int j = 0;
        while(j < 128 && !file.eof()){
            dataBlock[j] = file.get();
            if(file.eof()){
                dataBlock[j] = (char) 26; //pozbycie sie znaku konca pliku
            }
            j++;
        }
        rightDataBlock = false;
        while(!rightDataBlock){
            std::cout<<"Wysylanie bloku danych\n";

                //SOH
                WriteFile(portHandle, &SOH, NumberOfBytes, &BufferSize, NULL);
            buffer = (char)numberOfDataBlock;
            //numer pakietu
            WriteFile(portHandle, &buffer, NumberOfBytes, &BufferSize, NULL);
            buffer = (char)(255 - numberOfDataBlock);
            //255-nr pakietu
            WriteFile(portHandle, &buffer, NumberOfBytes, &BufferSize, NULL);

            for(int i = 0; i < 128; i++){
                WriteFile(portHandle, &dataBlock[i], NumberOfBytes, &BufferSize, NULL);
            }
            if(checkSum == "CRC"){
                SumaKontrolna sumaKontrolna;
                int CRC = sumaKontrolna.calculateCRC(dataBlock, 128);
                char characterCRC[2];
                characterCRC [0]= sumaKontrolna.characterCRC(CRC, 1);
                characterCRC [1] = sumaKontrolna.characterCRC(CRC, 2);
                WriteFile(portHandle, &characterCRC[0], NumberOfBytes, &BufferSize, NULL);
                WriteFile(portHandle, &characterCRC[1], NumberOfBytes, &BufferSize, NULL);
            }
            else if(checkSum == "Algebraiczna"){
                SumaKontrolna sumaKontrolna1;
                char algebraic = sumaKontrolna1.calculateCheckSum(dataBlock);
                WriteFile(portHandle, &algebraic, NumberOfBytes, &BufferSize, NULL);
            }

            while(1){
                char result;
                ReadFile(portHandle, &result, NumberOfBytes, &BufferSize, NULL);

                if(result == NAK){
                    rightDataBlock = true;
                    std::cout<<"\nBlad sumy kontrolnej";
                    break;

                }
                else if(result == ACK){
                    rightDataBlock = true;
                    std::cout<<"\nOtrzymano dane";
                    break;
                }
                else if(result == CAN){
                    std::cout<<"\nBlad transmisji";
                    return 0;
                }
            }
        }
        if(numberOfDataBlock < 255){
            numberOfDataBlock++;
        }
        else{
            numberOfDataBlock = 1;
        }

    }
    file.close();

    while(1){
        buffer = EOT;
        WriteFile(portHandle, &buffer, NumberOfBytes, &BufferSize, NULL);
        ReadFile(portHandle, &buffer, NumberOfBytes, &BufferSize, NULL);
        if(buffer == ACK){
            break;
        }
    }
    CloseHandle(portHandle);
    std::cout<<"\nKoniec transmisji, przeslano informacje";
    return 0;
}




