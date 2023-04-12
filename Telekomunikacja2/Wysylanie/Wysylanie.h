#ifndef WYSYLANIE_WYSYLANIE_H
#define WYSYLANIE_WYSYLANIE_H
#include <windows.h>

class Wysylanie {
public:
    int sending(LPCTSTR);
    //In Windows API, function arguments that require a string
    // are typically defined using the LPCTSTR type.
    // This is because string data in Windows
    // is represented using Unicode characters (wide characters),
    // which are 2 bytes long, rather than ASCII characters, which are 1 byte long.
};


#endif //WYSYLANIE_WYSYLANIE_H
