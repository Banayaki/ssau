#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

/*
 *  Словом текста считается любая последовательность цифр и букв русского
алфавита; между соседними словами – не менее одного пробела. Найти те слова,
которые начинаются и заканчиваются на цифру (слово из одной цифры
удовлетворяет условию).
 */

//Наследник исполнителя, новый метод - чтение файла
class ExecutorOtLp1 : public WFStreamExecutor {
public:
    wchar_t *readFile() {
        printAll(toWString("---Original text begin---"));
        fin.seekg(0, fin.end);                                          //Изменение текущей позиции
        size_t length = static_cast<size_t>((int) fin.tellg() + 1);                     //Количество символов в файле + 1, что бы поместился \0
        auto *line = (wchar_t *) calloc(length, sizeof(wchar_t));     //Выделение памяти и инициализация 0
        fin.seekg(0, fin.beg);
        fin.getline(line, length, EOF);
        printAll(line);
        printAll(toWString("---Original text end---"));
        return line;
    }
};

//Вывод результата, сначала выводятся слова подходящие под условия, затем слова не прошедьшие проверку
void printResult(const vector<Word> &result, WFStreamExecutor &executor) {
    wstringstream ss;
    executor.printAll(executor.toWString("---Right words begin---"));
    for (Word word : result) {
        if (word.getValid()) executor.printAll(word.getStr());
        else ss << word.getStr() << EOL;
    }
    executor.printAll(executor.toWString("---Right words end---"));
    executor.printAll(executor.toWString("---Wrongs words begin---"));
    executor.printAll(ss.str());
    executor.printAll(executor.toWString("---Wrongs word end---"));
    ss.clear();
}

void lolFunction(vector<Word> &result) {                    //Сборщик мусора в с++, здарова, очищаем вектор
    for (Word word : result) {
        ZeroMemory(word.getStr(), sizeof(word.getStr()));
    }
}

int main() {
    setlocale(LC_ALL, "ru_RU.UTF-8");           //Открываем, читаем, анализируем файл, закрываем, подчищаем, заново?
    bool isWorking = true;
    ExecutorOtLp1 executor;
    LexicalAnalyzer analyzer;
    executor.getFout().open(OUTPUT_FILE);
    while (isWorking) {
        wchar_t *text;

        while (!executor.getFin().is_open()) {
            executor.openFile();
        }
        text = executor.readFile();

        vector<Word> result;
        analyzer.wordAnalysis(text, result);
        printResult(result, executor);
        ZeroMemory(text, sizeof(text));
        executor.getFin().close();
        lolFunction(result);
        isWorking = executor.wishToContinue();
        if (isWorking) executor.printAll(executor.toWString("\n###---Next iteration---###"));
    }
}