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

//Наследник исполнителя, новый метод - чтение файла
class ExecutorOtLp1 : public Executor {
public:
    char *readFile() {
        printAll("---Original text begin---");
        fin.seekg(0, fin.end);                                  //Изменение текущей позиции
        int length = (int) fin.tellg() + 1;                     //Количество символов в файле + 1, что бы поместился \0
        char *line = (char *) calloc(length, sizeof(char));     //Выделение памяти и инициализация
        fin.seekg(0, fin.beg);
        fin.getline(line, length, EOF);
        printAll(line);
        printAll("---Original text end---");
        return line;
    }
};

//Вывод результата, сначала выводятся слова подходящие под условия, затем слова не прошедьшие проверку
void printResult(const vector<Word> &result, Executor &executor) {
    stringstream ss;
    executor.printAll("---Right words begin---");
    for (Word word : result) {
        if (word.getValid()) executor.printAll(word.getStr());
        else ss << word.getStr() << EOL;
    }
    executor.printAll("---Right words end---");
    executor.printAll("---Wrongs words begin---");
    executor.printAll(ss.str());                        //Будет лишний перевод строки
    executor.printAll("---Wrongs word end---");         //TODO: не выводить лишний '\n'
}

int main() {
    bool isWorking = true;
    ExecutorOtLp1 executor;
    executor.getFout().open(OUTPUT_FILE);
    while (isWorking) {
        char *text;
        LexicalAnalyzer analyzer;

        while (!executor.getFin().is_open()) {
            executor.openFile();
        }
        text = executor.readFile();

        vector<Word> result;
        analyzer.WordAnalysis(text, result);
        printResult(result, executor);
        free(text);
        executor.getFin().close();
        isWorking = executor.wishToContinue();
        if (isWorking) executor.printAll("\n###---Next iteration---###");
    }
}