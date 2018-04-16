#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

//Вариант 2
//Сложеость 3

class ExecutorLp4 : public Executor {
public:
    void readFile(vector<string> &text) {
        while (!fin.eof()) {
            string x;
            getline(this->fin, x, EOL);
            text.push_back(x);
        }
        this->fin.close();
    }
};

enum Tag {
    DO, UNTIL, LOOP, NOT, AND, OR,
    ASSIGM, AO, COMPARE, NUM, INDET, WRONG
};

class Lexem {
public:
    string tag;
    string lexem;

    Lexem(const string &tag, const string &lexem) {
        this->tag = tag;
        this->lexem = lexem;
    }

    Lexem(const int &tag, const string &lexem) {
        if (tag == Tag::DO) this->tag = "do";
        else if (tag == Tag::UNTIL) this->tag = "until";
        else if (tag == Tag::LOOP) this->tag = "loop";
        else if (tag == Tag::NOT) this->tag = "not";
        else if (tag == Tag::AND) this->tag = "and";
        else if (tag == Tag::OR) this->tag = "or";
        this->lexem = lexem;
    }

    string getTag() {
        return this->tag;
    }

    string getLexem() {
        return this->lexem;
    }
};

//Класс - лексический анализатор, анализирует текст используя Конечный Автомат (далее КА)
class LexicalAnalyzer {
private:
    const int matrix1[4][4] = {
            4, 1, 1, 4,
            2, 2, 2, 4,
            0, 4, 3, 4,
            4, 4, 4, 4
    };

    const int matrix[8][6] = {
            2, 5, 2, 5, 5, 5,
            1, 1, 2, 5, 5, 5,
            6, 6, 6, 6, 6, 6,
            4, 5, 5, 5, 5, 5,
            3, 5, 5, 5, 5, 5,
            3, 5, 5, 4, 5, 5,
            5, 5, 5, 5, 5, 5,
            6, 6, 6, 5, 5, 5
    };

    const vector<string> KEY_WORDS = {"do", "until", "loop", "not", "and", "or"};

    enum LexCondition {
        S, A, B, C, D, E, F
    };

public:

//    void printMessage(const std::wstring &line, const AnalyzCondition &condition) {
//        std::wcout << "Error on " << condition << " state. " << "Error called by " << line << EOL;
//    }

    int getSymbolGroup(const char &currentChar) {
        if (currentChar >= 65 && currentChar <= 90 || currentChar >= 97 && currentChar <= 122) return 0;
        else if (currentChar >= NUMBERS_BEGIN && currentChar <= NUMBERS_END) return 1;
        else if (currentChar == SPACE || currentChar == EOL || currentChar == CR || currentChar == TAB) return 2;
        else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') return 3;
        else if (currentChar == '!' || currentChar == '<' || currentChar == '>') return 4;
        else if (currentChar == '=') return 5;
        else if (currentChar == ';') return 7;
        else return 6;
    }

    int find(const string &line) {
        int i = 0;
        for (const string &word : KEY_WORDS) {
            if (line == word) return i;
            ++i;
        }
    }

    void addWord(vector<Lexem> &result, const string &text,
                 const int &beginP, const int &endP) {
        int length = endP - beginP;
        string line = text.substr(beginP, length);
        int tag = find(line);

        if (tag < KEY_WORDS.size()) {
            result.emplace_back(Lexem((Tag) tag, line));
        } else if (line == "=") {
            result.emplace_back(Lexem("Assignment", line));
        } else if (line == "+" || line == "-" || line == "*" || line == "/") {
            result.emplace_back(Lexem("Arithmetic operation", line));
        } else if (line == ">" || line == "<" || line == "!=" || line == "==" || line == "<=" || line == ">=") {
            result.emplace_back(Lexem("Compare operation", line));
        } else {
            int i = 0;
            for (char ch : line) {
                if (isdigit(ch)) ++i;
            }
            if (i == line.size()) result.emplace_back(Lexem("Number", line));
            else result.emplace_back(Lexem("Identification", line));
        }
    }

    void wordAnalysis(const string &text, std::vector<Lexem> &result) {
        int currentPosition = 0;
        int beginPosition = 0;
        char currentChar;
        LexCondition lexCondition = LexCondition::S;

        while (text[currentPosition] != EOS) {
            currentChar = text[currentPosition];

            if (lexCondition == LexCondition::S) {
                beginPosition = currentPosition;
            }
            lexCondition = (LexCondition) matrix[getSymbolGroup(currentChar)][lexCondition];

            if (lexCondition == LexCondition::E) {
                while (getSymbolGroup(currentChar) != 2 && currentChar != 0) {
                    currentChar = text[++currentPosition];
                }
                int length = currentPosition - beginPosition;
                string line = text.substr(beginPosition, length);
                result.emplace_back(Lexem("Wrong", line));
                lexCondition = LexCondition::S;
                beginPosition = currentPosition;
                continue;
            }

            if (lexCondition == LexCondition::F) {
                addWord(result, text, beginPosition, currentPosition);
                if (currentChar == ';') {
                    result.emplace_back("Separator", ";");
                }
                lexCondition = LexCondition::S;
            }
            ++currentPosition;
        }
        if (lexCondition != LexCondition::S) {
            addWord(result, text, beginPosition, currentPosition);
        }
    }
};

void printResult(const vector<Lexem> &list, Executor &executor) {
    for (Lexem lex : list) {
        executor.printAll("It's " + lex.getTag() + ": {" + lex.getLexem() + "}");
    }
}

int main() {
    LexicalAnalyzer lexicalAnalyzer;
    vector<Lexem> zzz;
    vector<string> text;
    ExecutorLp4 executor;
    executor.getFout().open(OUTPUT_FILE);
    executor.openFile();
    executor.readFile(text);
    for (const string &line : text) {
        lexicalAnalyzer.wordAnalysis(line, zzz);
    }
    printResult(zzz, executor);
    return 0;
}