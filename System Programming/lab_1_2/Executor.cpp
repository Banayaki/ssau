#pragma once

#include <iostream>
#include <fstream>
#include <unistd.h>

using namespace std;

const char EOS = 0;
const char SPACE = ' ';
const char TAB = '\t';
const char CR = '\r';
const char EOL = '\n';
const char YES = 'y';
const char NO = 'n';

const string INCORRECT_INPUT = "Incorrect input, try again.";
const string FILE_FORMAT = ".txt";
const string ERROR = "Something going wrong when opens file";


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
        cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;
        cin >> line;
        while (line == "output.txt") { //При некорректном вводе запрашиваем ввод повторно, перед этим очищая поток
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
    }

    void printAll(const char *line) {
        cout << line << endl;
    }

    bool wishToContinue() {
        cout << "Do you wish to continue? (y or n)" << endl;
        char wish;
        cin >> wish;
        while (!cin || seek(cin) != EOL || wish != YES && wish != NO) {
            clearInputStream(cin);
            cout << INCORRECT_INPUT << endl;
            cin >> wish;
        }
        return wish == YES;
    }

    void openFile() {
        string path;
        path = get_current_dir_name();
        string fileName = readFileName();
        path += "/" + fileName;
        fin.open(path);
        deleteSpaces();
        if (!checkFormat(path)) {
            cout << "Incorrect file format. You need .txt file" << endl;
            fin.close();
        } else if (!fin.is_open()) {
            cout << "File does not exist, try again" << endl;
            fin.close();
        } else if (fin.eof()) {
            cout << "Empty file, try again" << endl;
            fin.close();
        } else printAll("File: " + fileName + " is opened");
    }

    void openFile(const string &fileName) {
        if (fin.is_open()) fin.close();
        string path;
        path = "/home/banayaki/PreFire/ssau/System Programming/lab_1_2";
        path += "/" + fileName;
        fin.open(path);
        deleteSpaces();
        if (!checkFormat(path)) {
            cout << "Incorrect file format. You need .txt file" << endl;
            fin.close();
            throw ERROR;
        } else if (!fin.is_open()) {
            cout << "File does not exist" << endl;
            fin.close();
            throw ERROR;
        } else if (fin.eof()) {
            cout << "Empty file" << endl;
            fin.close();
            throw ERROR;
        } else printAll("File: " + fileName + " is opened");
    }

    ifstream &getFin() {
        return this->fin;
    }

    ofstream &getFout() {
        return this->fout;
    }

    double getEps() {
        return this->EPS;
    }
};