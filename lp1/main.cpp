#include <iostream>
#include <fstream>
#include <cmath>
#include <windows.h>

/*
Реализовать  на  языке  C++  функции,  вычисляющие  заданное  условное  выражение.
Список  нескольких  различных  наборов  значений  аргументов  функции  загружается  из
файла.
В программе необходимо реализовать:
1)  Чтение наборов аргументов из файла.
2)  Функцию,  вычисляющую  для  каждого  набора  аргументов,  значения  выражения,
соответствующего варианту задания и возвращающую результат.
3)  Вывод значений аргументов и вычисленных значений функций на экран.
4)  Сохранение результатов обработки данных в файл.
*/

using namespace std;

ifstream fin; // NOLINT
ofstream fout; // NOLINT
const string FILE_FORMAT = ".txt"; // NOLINT
const string INCORRECT_INPUT = "Incorrect input, try again."; //NOLINT
const string ERROR_IFC = "ERROR. Incorrect file content."; //NOLINT
const string DIV_BY_ZERO = "Division by zero."; //NOLINT
const char SPACE = ' ';
const char TAB = '\t';
const char CR = '\r';
const char EOL = '\n';
const char YES = 'y';
const char NO = 'n';
double EPS = 1.0;

template<class T>
class Vector {
private:
    T *vector;
    int vectorSize;
public:
    Vector() {
        this->vectorSize = 0;
        this->vector = new T[this->vectorSize];
    }

    ~Vector() = default;

    T &operator[](int i) const {
        return this->vector[i];
    }

    int size() {
        return this->vectorSize;
    }

    void push_back(T number) {
        T *result = new T[++this->vectorSize];
        for (int i = 0; i < this->vectorSize; i++) {
            if (i != this->vectorSize - 1) {
                result[i] = this->vector[i];
            } else {
                result[i] = number;
            }
        }
        delete[] vector;
        vector = result;
    }

    void clear() {
        if (vector != nullptr) {
            ZeroMemory(this->vector, this->vectorSize * sizeof(T));
            this->vectorSize = 0;
            this->vector = new T[this->vectorSize];
        }
    }

};

template<class T>
void clearInputStream(T &in) {
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

void deleteSpaces(ifstream &in) {
    while (in.peek() == CR || in.peek() == EOL || in.peek() == TAB || in.peek() == SPACE) {
        in.get();
    }
}

string readFileName(istream &in) {
    string line;
    in >> line;
    while (!in || line == "output.txt") {
        cout << INCORRECT_INPUT << endl;
        clearInputStream(in);
        in >> line;
    }
    return line;
}

void printAll(const string &line) {
    cout << line << endl;
    fout << line << endl;
}

void readFile(Vector<double> &parameters) {
    double num;
    int i = 0;
    while (!fin.eof()) {
        deleteSpaces(fin);
        fin >> num;
        if (!fin) {
            throw runtime_error(ERROR_IFC);
        }
        deleteSpaces(fin);
        parameters.push_back(num);
        ++i;
    }
    if (i == 0 || i % 2 == 1) {
        throw runtime_error(ERROR_IFC);
    }
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

bool checkFormat(string path) {
    path = path.substr(path.length() - 4, string::npos);
    return path == FILE_FORMAT;
}

void openFile(istream &in) {
    cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;
    string path;
    while (!fin.is_open() || fin.eof()) {
        path = R"(C:\Users\Banayaki\Desktop\tests\)";
        string fileName = readFileName(in);
        path += fileName;
        fin.open(path);
        deleteSpaces(fin);
        if (!checkFormat(path)) {
            cout << "Incorrect file format. You need .txt file" << endl;
            fin.close();
        } else if (!fin.is_open()) {
            cout << "File does not exist, try again" << endl;
            fin.close();
        } else if (fin.eof()) {
            cout << "Empty file, try again" << endl;
            fin.close();
        } else cout << "File is opened" << endl;
    }
}

double machineEps() {
    while (1.0 + EPS != 1.0) {
        EPS /= 2.0;
    }
}

void calc(Vector<double> &parameters) {
    for (int i = 0; i < parameters.size(); i += 2) {
        double a = parameters[i];
        double b = parameters[i + 1];
        printAll("A = " + to_string(a));
        printAll("B = " + to_string(b));
        if (abs(a - b) < EPS) {
            if (-a == -0) printAll("Result = " + to_string(a));
            printAll("Result = " + to_string(-a));
        } else if (a > b) {
            if (abs(a) < EPS) {
                printAll(DIV_BY_ZERO);
            }
            printAll("Result = " + to_string(b / a + 1));
        } else if (b > a) {
            if (abs(b) < EPS) {
                printAll(DIV_BY_ZERO);
            }
            printAll("Result = " + to_string((a - b) / b));
        }
    }
}

int main() {
    machineEps();
    bool isWorking = true;
    fout.open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    fout << "Machine EPS = " << EPS << EOL;
    while (isWorking) {
        try {
            Vector<double> parameters;
            openFile(cin);
            readFile(parameters);
            fin.close();
            calc(parameters);
            parameters.clear();
            isWorking = wishToContinue(cin);
        } catch (runtime_error &ex) {
            printAll(ex.what());
            fin.close();
            fout.close();
            exit(1);
        }
    }
//    fin.close();
    fout.close();
}