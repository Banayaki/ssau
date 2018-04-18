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

    int checkPriority(const string &lexem) {
        if (lexem == "*" || lexem == "/") return 3;
        else if (lexem == "+" || lexem == "-") return 2;
        else if (lexem == "(") return 1;
    }

public:
    SyntacticAnalyzer(vector<Lexem> &text) { //NOLINT
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
        for (i = left;; ++i) {
            if (text->at(i).getLexem() == ";" || text->at(i).getLexem() == "loop") break;
            Lexem tmp = text->at(i);
            if (tmp.getTag() == "Number" || tmp.getTag() == "Identification") {
                changed.push_back(tmp);
            } else if (tmp.getTag() == "Arithmetic operation") {
                while (!stack.empty() && checkPriority(stack.top().getLexem()) >= checkPriority(tmp.getLexem())) {
                    changed.push_back(stack.top());
                    stack.pop();
                }
                stack.push(tmp);
            } else if (tmp.getLexem() == "(") {
                stack.push(tmp);
            } else if (tmp.getLexem() == ")") {
                while (checkPriority(stack.top().getLexem()) != 1) {
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

    /*
     * Тоже самое только для булевых выражений
     */
    int toPosixL(const int &left) {

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
    executor.printAll("----****----\n");
}

/*
 * Используется при формировании таблицы
 */
void findByTag(vector<Lexem> &list, string *arr, const int &size, Executor &executor) {
    int j = 0;
    for (j = 0; j < list.size(); ++j) {
        string tag = list[j].getTag();
        if (size == 6){
            if (tag == arr[0] || tag == arr[1] || tag == arr[2] || tag == arr[3] || tag == arr[4] || tag == arr[5]) {
                cout <<  setw(20) << list[j].getLexem();
                executor.getFout() << setw(20) << list[j].getLexem();
                if (j == list.size() - 1) {
                    list.erase(list.begin() + j);
                    return;
                }
                list.erase(list.begin() + j);
                break;
            }
        }
        if (size == 3){
            if (tag == arr[0] || tag == arr[1] || tag == arr[2]) {
                cout <<  setw(20) << list[j].getLexem();
                executor.getFout() << setw(20) << list[j].getLexem();
                if (j == list.size() - 1) {
                    list.erase(list.begin() + j);
                    return;
                }
                list.erase(list.begin() + j);
                break;
            }
        } else if (size == 1) {
            if (tag == arr[0]) {
                cout <<  setw(20) << list[j].getLexem();
                executor.getFout() << setw(20) << list[j].getLexem();
                if (j == list.size() - 1) {
                    list.erase(list.begin() + j);
                    return;
                }
                list.erase(list.begin() + j);
                break;
            }
        }
    }
    if (j == list.size()) {
        cout << left << setw(20) << " ";
        executor.getFout() << setw(20) << " ";
    }
}

/*
 * Выводит таблицу лексем составленную на осонвен лексического анализа текста
 *
 * @param vector<Lexem>, Executor
 */
void printTable(vector<Lexem> list, Executor &executor) {
    cout << left << setw(5) << "№" << setw(20) << "KeyWord"
         << setw(20) << "Operation"
         << setw(20) << "Number"
         << setw(20) << "Identification" << endl;
    executor.getFout() << left << setw(5) << "№" << setw(20) << "KeyWord"
                       << setw(20) << "Operation"
                       << setw(20) << "Number"
                       << setw(20) << "Identification" << endl;
    for (int i = 0; i < list.size(); ++i, cout << endl, executor.getFout() << endl) {

        cout << setw(5) << i + 1 << left;
        executor.getFout() << setw(5) << i + 1 << left;
        string arr[6] = {"do", "until", "loop", "not", "and", "or"};
        findByTag(list, arr, 6, executor);

        string arr1[3] = {"Arithmetic operation", "Compare operation", "Assignment"};
        findByTag(list, arr1, 3, executor);

        string arr2[1] = {"Number"};
        findByTag(list, arr2, 1, executor);

        string arr3[1] = {"Identification"};
        findByTag(list, arr3, 1, executor);
    }

    executor.printAll("******************************************************************************\n\n");
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
    bool isWorking = true;

    executor.getFout().open(OUTPUT_FILE);

    while (isWorking) {
        vector<Lexem> readyToUse;
        vector<string> text;

        while (!executor.getFin().is_open()) {
            executor.openFile();
        }
        executor.readFile(text);

        for (const string &line : text) {
            lexicalAnalyzer.wordAnalysis(line, readyToUse);
        }

        printResult(readyToUse, executor);
        printTable(readyToUse, executor);

        SyntacticAnalyzer syntacticAnalyzer(readyToUse);
        //    syntacticAnalyzer.toPosixA(25);

        isWorking = executor.wishToContinue();
    }

    return 0;
}