#include <iostream>
#include <fstream>
#include <vector>
#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

using namespace std;


/*
 * Реализовать в среде Visual Studio на языке C++ функцию поиска слов в тексте. Текст
 * загружается  из  файла  и  передаётся  в  качестве  параметра  функции.  Найденные  в
 * результате  выполнения  слова  выводятся  в  консоль.  Задание  необходимо  выполнить,
 * используя стандартные функции C++ для работы со строками.
 * В программе необходимо реализовать:
 * 1)  Чтение текста из файла.
 * 2)  Функцию, обрабатывающую текст и возвращающую слова, удовлетворяющие
 * заданию.
 * 3)  Вывод исходного текста и найденных слов на экран.
 *
 * Словом текста является последовательность цифр; между соседними словами – не
 * менее одного пробела. Найти те слова, в которых все нечётные цифры образуют
 * невозрастающую последовательность чисел. Одну цифру не считать
 * последовательностью.
 */

/*
 * Класс наследующий Executor, в котором опреледены методы вывода и открытия файлов,
 * так же в классе определяется метод чтения файла.
 * Плюс этого класса в том что при удалении объекта сборщиком мусора закрываются потоки,
 * да и реализуются принципы ООП
*/
class ExecutorLp3 : public Executor {
public:
    void readFile(vector<string> &text) {
        printAll("---Original text begin---");
        string line;
        while (!fin.eof() && fin) {
            getline(fin, line, EOL);
            printAll(line);
            text.push_back(line);
        }
        printAll("---Original text end---");
    }
};

//Очистка потока при некорректном вводе
void clearInputStream(istream &in) {
    in.clear();
    while (in.peek() != EOL && in.peek() != EOF) {
        in.get();
    }
}

//Поиск начала строки
int seek(istream &in) {
    while (in.peek() != EOL && in.peek() == SPACE) {
        in.get();
    }
    return in.peek();
}

string readFileName(istream &in) {
    string line;
    cout << R"(Enter a name of txt file from C:\Users\Banayaki\Desktop\tests\ )" << endl;
    in >> line;
    while (!in || line == "output.txt") { //При некорректном вводе запрашиваем ввод повторно, перед этим очищая поток
        cout << INCORRECT_INPUT << endl;
        clearInputStream(in);
        in >> line;
    }
    return line;
}

bool wishToContinue(istream &in) {
    cout << "Do you wish to continue? (y or n)" << endl;
    char wish;
    in >> wish;
    while (!in || seek(in) != EOL || wish != YES && wish != NO) {
        clearInputStream(in);
        cout << INCORRECT_INPUT << endl;
        in >> wish;
    }
    return wish == YES;
}

bool isSpace(const char &symbol) {
    return symbol == EOL || symbol == SPACE || symbol == CR;
}

/*
 * Поиск слов в тексте и сохранение их в отдельном контейнере
 * Если слово заканчивается каким-либо знаком (не пробелом) или начинается, содержит
 * То переходим к поиску следующего слова.
*/
void findWordsInText(vector<string> &text, vector<string> &result) {
    for (string &line : text) {
        for (int i = 0; i < line.length(); ++i) {
            string temp;
            while (isSpace(line[i])) ++i;
            while (isdigit(line[i]) && i != line.length()) {
                temp += line[i];
                ++i;
            }
            if (i < line.length() && !isdigit(line[i]) && !isSpace(line[i])) {
                while (line[i] != SPACE) ++i;
                continue;
            } else {
                if (temp.length() > 1) result.push_back(temp);
            }
        }
    }
    text.clear();
}

// Если все числа одинаковыве то вернет true
bool isEquals(vector<long> &result) {
    for (int i = 0; i < result.size() - 1; ++i) {
        if (result[i] != result[i + 1]) return false;
    }
    return true;
}

// Среди слов текста ищет слова подходящие под условие
void findRightWords(vector<string> &result) {
    for (string &word : result) {
        vector<long> numbers;
        bool isRight = false;
        long num = stol(word); // string to long
        if (num < 0) {
            throw runtime_error(INTEGER_OVERFLOW);
        }
        while (num > 0) {
            if ((num % 10) % 2 == 1) {
                numbers.push_back(num % 10);
            }
            num /= 10;
        }
        if (!isEquals(numbers) && numbers.size() != 1) {
            for (unsigned long j = numbers.size() - 1; j > 0; --j) {
                if (numbers[j] > numbers[j - 1]) {
                    isRight = true;
                    break;
                }
            }
            if (!isRight) {
                word.erase();
            }
        }
    }
}

int main() {
    bool isWorking = true;
    ExecutorLp3 executor;
    executor.getFout().open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    while (isWorking) {
        try {
            vector<string> text;
            vector<string> result;

            while (!executor.getFin().is_open()) {
                string fileName = readFileName(cin);
                executor.openFile(fileName);
            }
            executor.readFile(text);

            findWordsInText(text, result);
            executor.printAll("---Intermediate result begin---");
            for (const string &word : result) executor.printAll(word);
            executor.printAll("---Intermediate result end---");

            findRightWords(result);
            executor.printAll("---Final result begin---");
            for (const string &word : result) {
                if (!word.empty()) executor.printAll(word);
            }
            executor.printAll("---Final result end---");

            executor.getFin().close();
            isWorking = wishToContinue(cin);
        } catch (runtime_error &ex) {
            executor.printAll(ex.what());
            exit(1);
        }
    }
}