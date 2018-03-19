
class Executor {
protected:
    double EPS = 1.0;
    wifstream fin;
    wofstream fout;
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

//    virtual void readFile(vector<string> &text) = 0;

    wstring toWString(string str) {
        return wstring(str.begin(), str.end());
    }

    void clearInputStream(istream &in) {
        in.clear();
        while (in.peek() != EOL && in.peek() != EOF) {
            in.get();
        }

    }

    /*void printAll(char *line) {
        cout << line << endl;
        fout << line << endl;
    }*/

    void printAll(wchar_t *line) {
        wcout << line << endl;
        fout << line << endl;
    }

    void printAll(const wstring &line) {
        wcout << line << endl;
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

    const char *readFileName() {
        char *line = (char*) calloc(BUFFERED_SIZE, sizeof(char));
        cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;
        scanf("%255s", line);
        while (line == "output.txt") { //При некорректном вводе запрашиваем ввод повторно, перед этим очищая поток
            cout << INCORRECT_INPUT << endl;
            clearInputStream(cin);
            scanf("%255s", line);
        }
        return line;
    }

    void openFile() {
        string path;
        path = R"(C:\Users\Banayaki\Desktop\tests\)";
        path += readFileName();
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

    wifstream &getFin() {
        return this->fin;
    }

    wofstream &getFout() {
        return this->fout;
    }
};