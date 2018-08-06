/*
 * Возвращает случайное вещественное число в диапазоне от left до right
 */
double random(double left, double right) {
    return (double) (rand()) / RAND_MAX * (right - left) + left;
}

/*
 * Следующие три функции создают файлы и заполняют их значениями:
 * createRandomFiles - случайными значениями
 * createLightRandomFiles - почти отсортированный
 * createReverseFiles - обратно отсортированный
 */
void createRandomFiles() {
    srand((unsigned int) time(0));
    stringstream ss;
    ofstream oneThousandElements(R"(C:\Users\Banayaki\Desktop\tests\1_000_Elements.txt)");
    ofstream fiveThousandElements(R"(C:\Users\Banayaki\Desktop\tests\5_000_Elements.txt)");
    ofstream tenThousandElements(R"(C:\Users\Banayaki\Desktop\tests\10_000_Elements.txt)");
    for (int i = 0; i < 1000; ++i) {
        ss << random(0, 10000) << EOL;
    }
    oneThousandElements << ss.str();
    ss.clear();
    for (int i = 0; i < 4000; ++i) {
        ss << random(0, 10000) << EOL;
    }
    fiveThousandElements << ss.str();
    for (int i = 0; i < 5000; ++i) {
        ss << random(0, 10000) << EOL;
    }
    tenThousandElements << ss.str();
}

void createLightlyRandomFiles() {
    srand((unsigned int) time(0));
    stringstream ss;
    ofstream oneThousandElements(R"(C:\Users\Banayaki\Desktop\tests\L_1_000_Elements.txt)");
    ofstream fiveThousandElements(R"(C:\Users\Banayaki\Desktop\tests\L_5_000_Elements.txt)");
    ofstream tenThousandElements(R"(C:\Users\Banayaki\Desktop\tests\L_10_000_Elements.txt)");
    for (int i = 0; i < 1000; ++i) {
        if (i % 100 == 0) ss << random(0, 1000) << EOL;
        else ss << i << EOL;
    }
    oneThousandElements << ss.str();
    for (int i = 1000; i < 5000; ++i) {
        if (i % 100 == 0) ss << random(0, 5000) << EOL;
        else ss << i << EOL;
    }
    fiveThousandElements << ss.str();

    for (int i = 5000; i < 10000; ++i) {
        if (i % 500 == 0) ss << random(0, 10000) << EOL;
        else ss << i << EOL;
    }
    tenThousandElements << ss.str();
}

void createReverseFiles() {
    stringstream ss;
    ofstream oneThousandElements(R"(C:\Users\Banayaki\Desktop\tests\R_1_000_Elements.txt)");
    ofstream fiveThousandElements(R"(C:\Users\Banayaki\Desktop\tests\R_5_000_Elements.txt)");
    ofstream tenThousandElements(R"(C:\Users\Banayaki\Desktop\tests\R_10_000_Elements.txt)");
    for (int i = 10000; i > 9000; --i) {
        ss << i << EOL;
    }
    oneThousandElements << ss.str();
    for (int i = 9000; i > 5000; --i) {
        ss << i << EOL;
    }
    fiveThousandElements << ss.str();
    for (int i = 5000; i > 0; --i) {
        ss << i << EOL;
    }
    tenThousandElements << ss.str();
}

/*
 * Функция, спрашивающая у пользователя какой тест он хочет провести
 * 0 - пользовательский файл
 * 1 - файлы с 1.000 элементами
 * 2 - файлы с 5.000 элементами
 * 3 - файлы с 10.000 элементами
 */
int chooseTestType(Executor &executor) {
    cout
            << "Print 0 to sort your file\nPrint 1 to sort 1.000 elements\nPrint 2 to sort 5.000 elements\nPrint 3 to sort 10.000 elements"
            << endl;
    int type;
    cin >> type;
    while (!cin || type < 0 || type > 3) {
        cout << INCORRECT_INPUT << endl;
        executor.clearInputStream(cin);
        cin >> type;
    }
    return type;
}