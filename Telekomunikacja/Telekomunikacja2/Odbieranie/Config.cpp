#include "Config.h"
#include <iostream>
HANDLE Config::config(LPCTSTR port) {

    HANDLE handle = CreateFile(port, GENERIC_READ | GENERIC_WRITE, 0, NULL, OPEN_EXISTING, 0, NULL);
    if (handle != INVALID_HANDLE_VALUE)
    {
        DCB DeviceControlBlock;
        DeviceControlBlock.DCBlength = sizeof(DeviceControlBlock);
        GetCommState(handle, &DeviceControlBlock);
        DeviceControlBlock.BaudRate = CBR_9600;
        DeviceControlBlock.Parity = NOPARITY;
        DeviceControlBlock.StopBits = ONESTOPBIT;
        DeviceControlBlock.ByteSize = 8;
        DeviceControlBlock.fParity = TRUE;
        DeviceControlBlock.fDtrControl = DTR_CONTROL_DISABLE;
        DeviceControlBlock.fRtsControl = RTS_CONTROL_DISABLE;

        COMMTIMEOUTS timeOuts;
        timeOuts.ReadIntervalTimeout = 10000;
        timeOuts.ReadTotalTimeoutMultiplier = 10000;
        timeOuts.ReadTotalTimeoutConstant = 10000;
        timeOuts.WriteTotalTimeoutMultiplier = 100;
        timeOuts.WriteTotalTimeoutConstant = 100;

        COMSTAT comstat;
        DWORD error;
        SetCommState(handle, &DeviceControlBlock);
        SetCommTimeouts(handle, &timeOuts);
        ClearCommError(handle, &error, &comstat);
        return (handle);
    }
    else
    {
        std::cout<< "Blad polaczenia";
        exit (0);
    }
}
