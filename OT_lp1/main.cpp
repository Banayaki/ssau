#include <iostream>
#include <vector>
#include <Windows.h>
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
    char *readFile() {
        printAll("---original text begin---");
        fin.seekg(0, fin.end);
        int length = (int) fin.tellg() + 1;
        char *line = new char[length];
        fin.seekg(0, fin.beg);
        fin.getline(line, length, '\0');
        //fin.read(line, length);
        printAll(line);
        printAll("---original text end---");
        return line;
    }
};

class Word {
private:
    bool valid{};
    char* str;

public:
    Word(char* str, bool valid) {
        this->str = str;
        this->valid = valid;
    }

    char* getStr() {
        return this->str;
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
        S = 0, G = 1, F = 2, E = 3
    };

public:

    int getRow(const char &currentChar) {
        if (currentChar < 0) return 0;
        else if (currentChar > 47 && currentChar < 58) return 1;
        else if (currentChar == 32) return 2;
        else return 3;
    }


    void WordAnalysis(char *text, vector<Word>& result) {
        int currentPosition = 0;
        int beginPosition = 0;
        LexCondition condition = LexCondition::S;
        bool valid = true;

        while (text[currentPosition] != EOS) {
            char currentChar = text[currentPosition];

            if (condition == LexCondition::S && getRow(currentChar) < 2) {
                beginPosition = currentPosition;
                valid = true;
            }
            condition = (LexCondition) matrix[getRow(currentChar)][condition];

            if (condition == LexCondition::E) {
                valid = false;
            }

            if (condition == LexCondition::F) {
                int length = currentPosition - beginPosition;
                char* line = (char*) calloc(length + 1, sizeof(char));
                for (int i = 0; i < length; ++i) {
                    line[i] = text[beginPosition + i];
                }
                result.emplace_back(Word(line, valid));
                condition = LexCondition::S;
                valid = true;
            }

            ++currentPosition;
        }
    }
};

//Очистка потока при некорректном вводе
void clearInputStream(istream &in) {
    in.clear();
    while (in.peek() != EOL && in.peek() != EOF) {
        in.get();
    }
}

const char *readFileName(istream &in) {
    char* line;
    cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;

    while (!in || line == "output.txt") { //При некорректном вводе запрашиваем ввод повторно, перед этим очищая поток
        cout << INCORRECT_INPUT << endl;
        clearInputStream(in);
        in >> line;
    }
    return line;
}

int main() {
    bool isWorking = true;
    setlocale(LC_ALL, "ru_RU.UTF-8");
    ExecutorOtLp1 executor;
    executor.getFout().open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    while (isWorking) {
        char *text;
        LexicalAnalyzer analyzer;

        while (!executor.getFin().is_open()) {
            const char *fileName = readFileName(cin);
            //executor.openFile("input.txt"/*fileName*/);
        }
        text = executor.readFile();

        //TODO: ИЗбавится от ебучих указателей, передавать все по ссылкам
        vector<Word> result;
        analyzer.WordAnalysis(text, result);
            for (Word word : result) {
            executor.printAll(word.getStr());
        }
        isWorking = false;
        //TODO: выбрасывает исключение непроверяемое
    }
}