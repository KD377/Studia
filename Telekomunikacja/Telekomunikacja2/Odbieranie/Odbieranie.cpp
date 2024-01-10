#include "Odbieranie.h"
#include "Config.h"
#include "SumaKontrolna.h"
#include <iostream>
#include <fstream>
const char SOH = 0x01;
const char EOT = 0x04;
const char ACK = 0x06;
const char NAK = 0x15;
const char CAN = 0x18;
const char C = 0x43;
int NumberOfBytes1 = 1;
unsigned long BufferSize1 = sizeof(char);


int Odbieranie::odbieranie(LPCTSTR port) {
    Config configure;
    HANDLE portHandle = configure.config(port);
    char filePath[255];
    std::cout<<"Podaj nazwe pliku do zapisania\n";
    std::cin >> filePath;
    char buffer;
    std::string checkSum;
    bool transmission = false;
    while(checkSum != "C" && checkSum != "NAK"){
        std::cout<<"Wybierz C lub NAK: ";
        std::cin>>checkSum;
        if(checkSum == "C"){
            buffer = C;
            std::cout<<"\nWybrano CRC";
            break;
        }
        else if(checkSum == "NAK"){
            buffer = NAK;
            std::cout<<"\nWybrano NAK";
            break;
        }
        else{
            std::cout<<"\nBlad, wybierz C lub NAK!!!";
        }

    }

    for(int i = 0; i < 6; i++){
        //wysylamy C lub NAK
        std::cout<<"\nWysylanie ";
        WriteFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
        //pobieramy SOH
        ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
        if(buffer == SOH){
            std::cout<<"\nOtrzymano SOH";
            transmission = true;
            break;
        }
        else if(buffer == C){
            std::cout<<"\nOtrzymano C";
            transmission = true;
            break;
        }
    }
    if(!transmission){
        std::cout<<"\nNie otrzymano SOH";
        return 0;
    }
    std::ofstream file;
    file.open(filePath, std::ios::binary);
    char dataBlock[128];
    int numberOfDataBlock = 1;
    bool rightDataBlock;
    char complement;
    char CRC[2];
    char algebraicCheckSum;
    while(1){
        ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
        numberOfDataBlock = (int) buffer;
        ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
        complement = buffer;
        for(int i = 0; i < 128; i++){
            ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
            dataBlock[i] = buffer;
        }
        if(checkSum == "C"){
            ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
            CRC[0] = buffer;
            ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
            CRC[1] = buffer;
        }
        else if(checkSum == "NAK"){
            ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
            algebraicCheckSum = buffer;

        }

        if(checkSum == "C"){
            SumaKontrolna sumaKontrolna;
            int crc = sumaKontrolna.calculateCRC(dataBlock, 128);
            if(sumaKontrolna.characterCRC(crc, 1) != CRC[0] || sumaKontrolna.characterCRC(crc,2) != CRC[1]){
                std::cout<<"\nZla suma CRC";
                WriteFile(portHandle, &NAK, NumberOfBytes1,  &BufferSize1, NULL);
                rightDataBlock = false;
            }
            else{
                rightDataBlock = true;
            }
        }
        else if(checkSum == "NAK"){
            SumaKontrolna sumaKontrolna1;
            char sum = sumaKontrolna1.calculateCheckSum(dataBlock);
            if(sum != algebraicCheckSum){
                std::cout<<"\nZla suma algebraiczna";
                WriteFile(portHandle, &NAK, NumberOfBytes1, &BufferSize1, NULL);
                rightDataBlock = false;
            }
            else{
                rightDataBlock = true;
            }
        }
        else if((char) 255 - numberOfDataBlock != complement){
            std::cout<<"\nZly numer pakietu";
            WriteFile(portHandle, &NAK, NumberOfBytes1, &BufferSize1, NULL);
            rightDataBlock = false;
        }

        if(rightDataBlock){
            for(int i = 0; i < 128; i++){
                if(dataBlock[i]!=26){
                    file << dataBlock[i];
                }
                WriteFile(portHandle, &ACK, NumberOfBytes1, &BufferSize1, NULL);
            }
        }
        ReadFile(portHandle, &buffer, NumberOfBytes1, &BufferSize1, NULL);
        if(buffer == EOT){
            std::cout<<"\nZakonczono polaczenie";
            break;
        }
        else if(buffer == CAN){
            std::cout<<"\nPrzerwano polaczenie";
            break;
        }
        std::cout<<"\nOdebrano pakiet";
    }
    WriteFile(portHandle, &ACK, NumberOfBytes1, &BufferSize1, NULL);
    file.close();
    CloseHandle(portHandle);
    return 0;
}
