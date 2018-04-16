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