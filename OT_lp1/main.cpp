#include <iostream>
#include <vector>
#include <String.h>
#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"


using namespace std;

/*
 *  Словом текста считается любая последовательность цифр и букв русского
алфавита; между соседними словами – не менее одного пробела. Найти те слова,
которые начинаются и заканчиваются на цифру (слово из одной цифры
удовлетворяет условию).
 */
class ExecutorOtLp1 : public Executor {
public:
    void readFile(vector<char *> &text) {
        printAll("---original text begin---");
        char *line;
        while (!fin.eof() && fin) {
            line = nullptr;
            fin.getline(line, fin.gcount(), EOL);
            printAll(line);
            text.push_back(line);
        }
        printAll("---original text end---");
    }
};

class Word {
private:
    bool valid;
    char *str;
public:
    Word() = default;

    Word(char *word) {
        this->str = word;
    }

    bool isValid() const {
        return valid;
    }

    void setValid(bool valid) {
        this->valid = valid;
    }

    char *getStr() const {
        return str;
    }

    void setStr(char *str) {
        this->str = str;
    }
};

class LexicalAnalyzer {
private:
    const int matrix[4][3] = { //  S    G     E
            1, 1, 3,    //А...я    G    G     E
            1, 1, 3,   //0...9     G    G     E
            2, 2, 3,   //space     F    F     E
            3, 3, 3   //other      E    E     E
    };

    enum LexCondition {
        S, A, E, F
    };

public:

    int getRow(const char &currentChar) {
        if (currentChar > 47 && currentChar < 58) return 1;
        else if (currentChar > 127 && currentChar < 176 || currentChar > 223 && currentChar < 242) return 0;
        else if (currentChar == 32) return 2;
        else return 3;
    }

    char *substring(char *line, int begin, int end) {
        int length = end - begin + 1;
        auto *newLine = new char[length];
        for (int i = 0; i < length; ++i) {
            newLine[i] = line[begin + i];
        }
        return newLine;
    }


    vector<Word> WordAnalysis(vector<char *> text) {
        vector<Word> result;
        for (char *line : text) {
            int currentPosition = 0;
            int beginPosition = 0;
            LexCondition condition = LexCondition::S;
            Word word;

            //TODO: Хранить \0
            while (line[currentPosition] != '\0') {
                char currentChar = line[currentPosition];

                if (condition == LexCondition::S && getRow(currentChar) < 2) {
                    beginPosition = currentPosition;
                    word.setValid(true);
                }
                condition = (LexCondition) matrix[getRow(currentChar)][condition];

                if (condition == LexCondition::E) {
                    word.setValid(false);
                }

                if (condition == LexCondition::F) {
                    word.setStr(substring(line, beginPosition, currentPosition));
                    result.push_back(word);
                    condition = LexCondition::S;
                }

                ++currentPosition;
            }
        }

        return result;
    }
};

//Очистка потока при некорректном вводе
void clearInputStream(istream &in) {
    in.clear();
    while (in.peek() != EOL && in.peek() != EOF) {
        in.get();
    }
}

const char* readFileName(istream &in) {
    string line;
    cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;
    in >> line;
    while (!in || line == "output.txt") { //При некорректном вводе запрашиваем ввод повторно, перед этим очищая поток
        cout << INCORRECT_INPUT << endl;
        clearInputStream(in);
        in >> line;
    }
    return line.c_str();
}

int main() {
    bool isWorking = true;
    ExecutorOtLp1 executor;
    executor.getFout().open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    while (isWorking) {
        vector<char*> text;
        LexicalAnalyzer analyzer;

        while (!executor.getFin().is_open()) {
            const char* fileName = readFileName(cin);
            executor.openFile(fileName);
        }
        executor.readFile(text);

        vector<Word> result = analyzer.WordAnalysis(text);

        for (Word word : result) {
            executor.printAll(word.getStr());
        }

    }
}