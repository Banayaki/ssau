#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

//Вариант 2
//Сложеость 3

/*
 * Класс исполнитель для 2-ой лабараторной работы
 * Родительский класс Executor, предназначенный для работы с потокакми ввода, вывода,
 * открытия и закрытия файлов и еще несколько служебных методов и функций
 * ExecutorOtLp2 не переоопределяет методов родительского класса
 * Добавляет новый - чтение файла и определяет его взависимости от специфичности задания
 * 
 * @see Executor
 */
class ExecutorOtLp2 : public Executor {
public:
    /*
     * Выполняет функцию чтения файла и записи данный в коллекцию vector 
     * Аргумент функции - ссылка на контейнер
     * Метод считывает строки из файла
     * 
     * @param vector<string>
     */
    void readFile(vector<string> &text) {
        while (!fin.eof()) {
            string x;
            getline(this->fin, x, EOL);
            text.push_back(x);
        }
        this->fin.close();
    }
};

/*
 * Это класс который выполняет роль синтаксического анализа, на данный момент
 * он находится на стадии разработки и не используется в программе
 */
class SyntacticAnalyzer {
private:
    vector<Lexem> *text;

    int checkPrioritet(const string &lexem) {
        if (lexem == "*" || lexem == "/") return 3;
        else if (lexem == "+" || lexem == "-") return 2;
        else if (lexem == "(") return 1;
    }

public:
    SyntacticAnalyzer(vector<Lexem> &text) {
        this->text = &text;
    }

    /*
     * Алгоритм преобразования инфиксной записи в постфиксную (алгоритм обратной польской записи)
     * Нужен для того что бы при анализе было легче воспринимать приоритет операций
     * 
     * @param Integer
     * @return Integer - позиция конца арифметических операций
     * -1 - если преобразование не получилось
     */
    int toPosixA(const int &left) {
        stack<Lexem> stack;
        vector<Lexem> changed;
        int i;
        for (i = left; ; ++i) {
            if (text->at(i).getLexem() == ";" || text->at(i).getLexem() == "loop") break;
            Lexem tmp = text->at(i);
            if (tmp.getTag() == "Number" || tmp.getTag() == "Identification") {
                changed.push_back(tmp);
            } else if (tmp.getTag() == "Arithmetic operation") {
                while (!stack.empty() && checkPrioritet(stack.top().getLexem()) >= checkPrioritet(tmp.getLexem())) {
                    changed.push_back(stack.top());
                    stack.pop();
                }
                stack.push(tmp);
            } else if (tmp.getLexem() == "(") {
                stack.push(tmp);
            } else if (tmp.getLexem() == ")") {
                while (checkPrioritet(stack.top().getLexem()) != 1) {
                    changed.push_back(stack.top());
                    stack.pop();
                }
                stack.pop();
            }
        }
        while (!stack.empty()) {
            changed.push_back(stack.top());
            stack.pop();
        }
        for (int i = 0; i < changed.size(); ++i) {
            text->at(left + i) = changed[i];
        }

        return i;
    }

};

/*
 * Функция которая выводит на экран и в файл результат работы лексического анализатор
 * Тип лексемы + { текст лексемы }
 * 
 * @param vector<lexem>, Executor
 */
void printResult(const vector<Lexem> &list, Executor &executor) {
    for (Lexem lex : list) {
        executor.printAll("It's " + lex.getTag() + ": {" + lex.getLexem() + "}");
    }
}

/*
 * Функция - запускающая главный поток, в котором происходят все манипуляции
 * с объектами и функциями
 * Здесь открывается файл, читается, лексически анализируется
 * TODO Синтаксический анализ
 *
 */
int main() {
    ExecutorOtLp2 executor;
    LexicalAnalyzer lexicalAnalyzer;
    vector<Lexem> readyToUse;
    vector<string> text;
    bool isWorking = true;

    executor.getFout().open(OUTPUT_FILE);

    while (isWorking) {
        while (!executor.getFin().is_open()) {
            executor.openFile();
        }
        executor.readFile(text);

        for (const string &line : text) {
            lexicalAnalyzer.wordAnalysis(line, readyToUse);
        }
        printResult(readyToUse, executor);

        SyntacticAnalyzer syntacticAnalyzer(readyToUse);
//    syntacticAnalyzer.toPosixA(25);
    }

    return 0;
}