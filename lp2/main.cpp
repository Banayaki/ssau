#include <iostream>
#include <fstream>
#include <cmath>
#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

using namespace std;

/*
1)  Найти сумму квадратов всех положительных элементов массива A={a[i]},
удовлетворяющих условию: a[i] >= b.
Реализовать  в  среде  Visual  Studio  на  языке  C++  функцию  обработки  элементов
массива.  Массив  загружается  из  файла  и  передаётся  в  качестве  параметра  функции.
Также из файла загружаются необходимые параметры обработки.
В программе необходимо реализовать:
1)  Создание массива из файла.
2)  Функцию, обрабатывающую массив.
3)  Вывод массива и результата его обработки на экран.
4)  Сохранение результатов обработки данных в файл.
*/

class ExecutorLp2 : public Executor {
public:
    void readFile(MyVector<double> &numbers) {
        stringstream ss;
        ss << "---changed massive begin---" << EOL;
        double num = 0;
        double compare = 0;
        deleteSpaces();
        if (fin && !fin.eof()) {
            fin >> compare;
        }
        if (!fin || fin.eof()) {
            throw runtime_error(ERROR_IFC);
        }
        printAll("Parameter b = " + to_string(compare));
        printAll("---original massive begin---");
        while (!fin.eof()) {
            fin >> num;
            if (!fin) {
                throw runtime_error(ERROR_IFC);
            }
            printAll(to_string(num));
            if (abs(num - compare) >= EPS && num >= EPS) {
                ss << num << EOL;
                numbers.push_back(num);
            }
        }
        printAll("---original massive end---");
        if (numbers.empty()) ss << "Massive is empty" << EOL;
        ss << "---changed massive end---";
        printAll(ss.str());
    }
};

void clearInputStream(istream &in) {
    in.clear();
    while (in.peek() != EOL && in.peek() != EOF) {
        in.get();
    }
}

int seek(istream &in) {
    while (in.peek() != EOL && in.peek() == SPACE) {
        in.get();
    }
    return in.peek();
}

bool wishToContinue(istream &in) {
    cout << "Do you wish to continue? (y or n)" << endl;
    char wish;
    in >> wish;
    while (!in || seek(in) != EOL || wish != YES && wish != NO) {
        clearInputStream(in);
        cout << INCORRECT_INPUT << endl;
        in >> wish;
    }
    return wish == YES;
}

string readFileName(istream &in) {
    string line;
    cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;
    in >> line;
    while (!in || line == "output.txt") {
        cout << INCORRECT_INPUT << endl;
        clearInputStream(in);
        in >> line;
    }
    return line;
}

double calcResult(MyVector<double> &numbers) {
    double result = 0;
    for (int i = 0; i < numbers.size(); i++) {
        result += (numbers[i] * numbers[i]);
        if (result == infinity()) {
            throw runtime_error(DOUBLE_OVERFLOW);
        }
    }
    return result;
}

int main() {
    bool isWorking = true;
    ExecutorLp2 executor;
    executor.getFout().open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    while (isWorking) {
        try {
            MyVector<double> numbers;
            while (!executor.getFin().is_open()) {
                string name = readFileName(cin);
                executor.openFile(name);
            }
            executor.readFile(numbers);
            executor.printAll("Result = " + to_string(calcResult(numbers)));
            executor.getFin().close();
            numbers.clear();
            isWorking = wishToContinue(cin);
        } catch (runtime_error &ex) {
            executor.printAll(ex.what());
            exit(1);
        }
    }
}