#pragma once
#include "../headers/GeneralHeader.h"

class Executor {
protected:
    double EPS = 1.0;
    ifstream fin;
    ofstream fout;
private:
    void deleteSpaces() {
        while (fin.peek() == CR || fin.peek() == EOL || fin.peek() == TAB || fin.peek() == SPACE) {
            fin.get();
        }
    }

    bool checkFormat(string path) {
        path = path.substr(path.length() - 4, string::npos);
        return path == FILE_FORMAT;
    }

    string readFileName() {
        string line;
        cout << R"(Enter a name of txt file)" << endl;
        cin >> line;
        while (line == "output.txt") {
            cout << INCORRECT_INPUT << endl;
            clearInputStream(cin);
            cin >> line;
        }
        return line;
    }
public:
    Executor() {
        while (1.0 + this->EPS != 1.0) {
            this->EPS /= 2.0;
        }
        fout.open("../out/output.txt");
    }

    ~Executor() {
        this->fin.close();
        this->fout.close();
    }

    template <typename T>
    int seek(T &in) {
        while (in.peek() != EOL && in.peek() == SPACE) {
            in.get();
        }
        return in.peek();
    }

    template <typename T>
    void clearInputStream(T &in) {
        in.clear();
        while (in.peek() != EOL && in.peek() != EOF) {
            in.get();
        }
    }

    void printAll(const string &line) {
        cout << line << endl;
        fout << line << endl;
    }

    void printAll(const char *line) {
        cout << line << endl;
        fout << line << endl;
    }

    bool wishToContinue() {
        cout << "Do you wish to continue? (y or n)" << endl;
        char wish;
        cin >> wish;
        while (!cin || seek(cin) != EOL || (wish != YES && wish != NO)) {
            clearInputStream(cin);
            cout << INCORRECT_INPUT << endl;
            cin >> wish;
        }
        return wish == YES;
    }

    ifstream &getFin() {
        return this->fin;
    }

    ofstream &getFout() {
        return this->fout;
    }
};