#include <fstream>
#include <iostream>
#include <sstream>
#include <Windows.h>

using namespace std;

const string FILE_FORMAT = ".txt"; // NOLINT
const string INCORRECT_INPUT = "Incorrect input, try again."; //NOLINT
const string ERROR_IFC = "ERROR. Incorrect file content."; //NOLINT
const string DOUBLE_OVERFLOW = "Overflow double"; //NOLINT
const string INTEGER_OVERFLOW = "Overflow double";
const char EOS = '\0';
const char SPACE = ' ';
const char TAB = '\t';
const char CR = '\r';
const char EOL = '\n';
const char YES = 'y';
const char NO = 'n';

class Executor {
protected:
    double EPS = 1.0;
    ifstream fin;
    ofstream fout;
public:
    Executor() {
        while (1.0 + this->EPS != 1.0) {
            this->EPS /= 2.0;
        }
        //Why he's print 0.00000???
        //printAll("Machine EPS = " + to_string(this->EPS));
    }

    ~Executor() {
        fin.close();
        fout.close();
        ZeroMemory(this, sizeof(Executor));
    }

    //virtual void readFile(vector<string> &text) = 0;

    void printAll(const string &line) {
        cout << line << endl;
        fout << line << endl;
    }

    void printAll(char line) {
        cout << line << endl;
        fout << line << endl;
    }

    void deleteSpaces() {
        while (fin.peek() == CR || fin.peek() == EOL || fin.peek() == TAB || fin.peek() == SPACE) {
            fin.get();
        }
    }

    bool checkFormat(string path) {
        path = path.substr(path.length() - 4, string::npos);
        return path == FILE_FORMAT;
    }

    void openFile(const string &fileName) {
        //cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;
        string path;
        path = R"(C:\Users\Banayaki\Desktop\tests\)";
        path += fileName;
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
        } else cout << "File is opened" << endl;

    }

    ifstream &getFin() {
        return this->fin;
    }

    ofstream &getFout() {
        return this->fout;
    }
};