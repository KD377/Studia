import funkcje

def wyswietl_wyniki(x1,x2,epsilon,iteracje,funkcja):
    print("\nOtrzymane wyniki wyswietla sie ponizej wraz z wygenerowanymi wykresami, na ktorych zostaly zaznaczone miejsca zerowe "
          "obliczone obiema metoda oraz kolorem zaznaczony jest wybrany przedzial poszukiwan miejsca zerowego\n")
    if funkcja == "1":
        root1, iter1 = funkcje.bisekcja(x1, x2, epsilon, iteracje, funkcje.f)
        root2, iter2 = funkcje.metoda_stycznych(x1, x2, epsilon, iteracje, funkcje.f, funkcje.df,funkcje.d2f)
        print("Miejsce zerowe znalezione metoda bisekcji wynosi: ")
        print(root1)
        if epsilon != 0:
            print("Metoda bisekcji potrzebowala " + str(iter1) + " iteracji\n")
        print("Miejsce zerowe znaleziona metoda stycznych wynosi:")
        print(root2)
        if epsilon != 0:
            print("Metoda stycznych potrzebowala " + str(iter2) + " iteracji")
            if(iter1 > iter2 and iter1 != 0 and iter2 != 0):
                print("By osiagnac zadana dokladnosc metoda stycznych potrzebowala mniej iteracji")
            else:
                print("\nBy osiagnac zadana dokladnosc metoda bisekcji potrzebowala mniej iteracji")
        funkcje.rysuj_wykres(x1, x2, funkcje.f, root1,'3x^3-7x+1 (metoda bisekcji)')
        funkcje.rysuj_wykres(x1, x2, funkcje.f, root2,'3x^3-7x+1 (metoda stycznych)')
    elif funkcja == "2":
        root1, iter1 = funkcje.bisekcja(x1, x2, epsilon, iteracje, funkcje.g)
        root2, iter2 = funkcje.metoda_stycznych(x1, x2, epsilon, iteracje, funkcje.g, funkcje.dg,funkcje.d2g)
        print("Mijesce zerowe znalezione metoda bisekcji wynosi: ")
        print(root1)
        if epsilon != 0:
            print("Metoda bisekcji potrzebowala " + str(iter1) + " iteracji\n")
        print("Miejsce zerowe znaleziona metoda stycznych wynosi:")
        print(root2)
        if epsilon != 0:
            print("Metoda stycznych potrzebowala " + str(iter2) + " iteracji")
            if (iter1 > iter2 and iter1 != 0 and iter2 != 0):
                print("By osiagnac zadana dokladnosc metoda stycznych potrzebowala mniej iteracji")
            else:
                print("\nBy osiagnac zadana dokladnosc metoda bisekcji potrzebowala mniej iteracji")
        funkcje.rysuj_wykres(x1, x2, funkcje.g, root1,'(1/2)*sin(3x) (metoda bisekcji)')
        funkcje.rysuj_wykres(x1, x2, funkcje.g, root2,'(1/2)*sin(3x) (metoda stycznych)')
    elif funkcja == "3":
        root1,iter1=funkcje.bisekcja(x1, x2, epsilon, iteracje, funkcje.h)
        root2,iter2=funkcje.metoda_stycznych(x1, x2, epsilon, iteracje, funkcje.h, funkcje.dh,funkcje.d2h)
        print("Mijesce zerowe znalezione metoda bisekcji wynosi: ")
        print(root1)
        if epsilon != 0:
            print("Metoda bisekcji potrzebowala " + str(iter1) + " iteracji\n")
        print("Miejsce zerowe znaleziona metoda stycznych wynosi:")
        print(root2)
        if epsilon != 0:
            print("Metoda stycznych potrzebowala " + str(iter2) + " iteracji")
            if (iter1 > iter2 and iter1 != 0 and iter2 != 0):
                print("By osiagnac zadana dokladnosc metoda stycznych potrzebowala mniej iteracji")
            else:
                print("\nBy osiagnac zadana dokladnosc metoda bisekcji potrzebowala mniej iteracji")
        funkcje.rysuj_wykres(x1, x2, funkcje.h, root1,'(1/3)^x -5 (metoda bisekcji)')
        funkcje.rysuj_wykres(x1, x2, funkcje.h, root2,'(1/3)^x -5 (metoda stycznych)')
    elif funkcja == "4":
        root1,iter1=funkcje.bisekcja(x1, x2, epsilon, iteracje, funkcje.i)
        root2,iter2=funkcje.metoda_stycznych(x1, x2, epsilon, iteracje, funkcje.i, funkcje.di,funkcje.d2i)
        print("Mijesce zerowe znalezione metoda bisekcji wynosi: ")
        print(root1)
        if epsilon != 0:
            print("Metoda bisekcji potrzebowala " + str(iter1) + " iteracji\n")
        print("Miejsce zerowe znaleziona metoda stycznych wynosi:")
        print(root2)
        if epsilon != 0:
            print("Metoda stycznych potrzebowala " + str(iter2) + " iteracji")
            if (iter1 > iter2 and iter1 != 0 and iter2 != 0):
                print("By osiagnac zadana dokladnosc metoda stycznych potrzebowala mniej iteracji")
            else:
                print("\nBy osiagnac zadana dokladnosc metoda bisekcji potrzebowala mniej iteracji")
        funkcje.rysuj_wykres(x1, x2, funkcje.i, root1,'-x^3 * sin(x)*-3^x (metoda bisekcji)')
        funkcje.rysuj_wykres(x1, x2, funkcje.i, root2,'-x^3 * sin(x)*-3^x (metoda stycznych)')