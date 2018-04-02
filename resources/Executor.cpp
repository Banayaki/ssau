
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
    }

    ~Executor() {
        this->fin.close();
        this->fout.close();
    }

    void clearInputStream(istream &in) {
        in.clear();
        while (in.peek() != EOL && in.peek() != EOF) {
            in.get();
        }

    }

    void printAll(char *line) {
        cout << line << endl;
        fout << line << endl;
    }

    void printAll(const string &line) {
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

    //Поиск начала строки
    int seek(istream &in) {
        while (in.peek() != EOL && in.peek() == SPACE) {
            in.get();
        }
        return in.peek();
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

    void openFile() {
        string path;
        path = R"(C:\Users\Banayaki\Desktop\tests\)";
        string fileName = readFileName();
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
        } else cout << "File: " + fileName + " is opened" << endl;
    }

    void openFile(const string &fileName) {
        string path = R"(C:\Users\Banayaki\Desktop\tests\)";
        path += fileName;
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
        } else cout << "File: " + fileName + " is opened" << endl;
    }

    ifstream &getFin() {
        return this->fin;
    }

    ofstream &getFout() {
        return this->fout;
    }
};