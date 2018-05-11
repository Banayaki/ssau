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

class ErrorMessage {
private:
    bool isError = false;
    string message = "";
public:
    void addMsg(const string &message) {
        this->message += message;
    }

    void itsError(const bool &error) {
        this->isError = error;
    }
};

/*
 * Это класс который выполняет роль синтаксического анализа, на данный момент
 * он находится на стадии разработки и не используется в программе
 */
class SyntacticAnalyzer {
private:
    Executor *executor;
    vector<Lexem> *text;
    bool loggingIsOn = true;

    const int waitingRoom[11][12] = {
            1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 0, 11,
            11, 3, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 5, 11, 5, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 9, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 9, 11,
            11, 2, 11, 2, 11, 4, 2, 11, 11, 10, 11, 11,
            11, 2, 11, 2, 7, 4, 2, 8, 11, 10, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 7, 11,
            11, 11, 6, 11, 11, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11
    };

    enum Condition {
        begin, LS, CS, UO, LS1, BO, CO, OPS, assigm, AS, AS1, ER
    };

    vector<string> nameOfCondition = {
            "\" Determination of construction \"",
            "\" Logical statement \""
            "\" Compare statement \""
            "\" Unary operation \""
            "\" Logical statement \""
            "\" Binary operation \""
            "\" Compare operation \""
            "\" Operators \""
            "\" Assignment \""
            "\" Arithmetic statement \""
            "\" Arithmetic statement \""
            "\" Error condition \""
    };

    int getTagGroup(const string &tag) {
        if (tag == "do" || tag == "until") return 0;
        if (tag == "loop") return 1;
        if (tag == "not") return 2;
        if (tag == "and" || tag == "or") return 3;
        if (tag == "Assignment") return 4;
        if (tag == "Arithmetic operation") return 5;
        if (tag == "Number") return 6;
        if (tag == "Identification") return 7;
        if (tag == "Separator") return 8;
        if (tag == "Compare operation") return 9;
        if (tag == "Wrong") return 10;
    }

    int checkPriorityA(const string &lexem) {
        if (lexem == "*" || lexem == "/") return 2;
        else if (lexem == "+" || lexem == "-") return 1;
    }

    int checkPriorityL(const string &lexem) {
        if (lexem == "Compare operation") return 4;
        else if (lexem == "not") return 3;
        else if (lexem == "and") return 2;
        else if (lexem == "or") return 1;
    }

public:
    SyntacticAnalyzer(vector<Lexem> &text, Executor &executor) {
        this->text = &text;
        this->executor = &executor;
        analyze();
    }

    void analyze() {
        stack<string> condition;
        unsigned int currentPos = 0;
        executor->printAll(to_string(Condition::LS));
        checkDoUntil(currentPos);
        currentPos += 2;
        logicalExpr(currentPos);
        operators(--currentPos);
        printResult(*text, *executor);
    }

    void checkDoUntil(unsigned int &currentPos) {
        if (text->at(currentPos).getTag() != "do" && text->at(currentPos + 1).getTag() != "until") {
            executor->printAll("# Because I know only one construction, I think it would be a \"do until\" #");
            executor->printAll("# I move on to the next lexem #");
        }
    }

    void printError(Condition prev, Condition current, const int &pos) {
        executor->printAll("I was in ");
    }

    void allFine(Condition prev, Condition current, const int &pos) {
        executor->printAll("All fine in " + pos);
    }

    void logicalExpr(unsigned int &currentPos) {
        unsigned int beginPos = currentPos;
        unsigned int size = text->size(); //NOLINT
        Condition condition = Condition::LS;
        Condition prevCondition = Condition::LS;
        string currentTag;

        while (currentPos < size && !(prevCondition == Condition::LS1 && currentTag == "Identification")) {
            currentTag = text->at(currentPos++).getTag();

            prevCondition = condition;
            condition = (Condition) waitingRoom[getTagGroup(currentTag)][condition];

            if (condition == Condition::ER) {
                printError(prevCondition, condition, currentPos);
                condition = (Condition) waitingRoom[getTagGroup(currentTag)][condition];
            } else {
                allFine(prevCondition, condition, currentPos);
            }
        }

        toPosixL(beginPos, currentPos - 1);
        //Todo: if == size
    }

    void operators(unsigned int &currentPos) {
        unsigned int beginPos = currentPos;
        unsigned int size = text->size();
        Condition condition = Condition::OPS;
        Condition prevCondition;
        bool isAS = false;

        while (currentPos < size && condition != Condition::begin) {
            string currentTag = text->at(currentPos++).getTag();

            prevCondition = condition;
            condition = (Condition) waitingRoom[getTagGroup(currentTag)][condition];

            if (condition == Condition::ER) {
                printError(prevCondition, condition, currentPos);
                condition = (Condition) waitingRoom[getTagGroup(currentTag)][condition];
                //Todo: переход какой нибудь нужен
            } else {
                if (!isAS && condition == Condition::AS) {
                    beginPos = currentPos;
                    isAS = true;
                } else if (currentTag == "Separator" && isAS) {
                    toPosixA(beginPos, currentPos - 1);
                    isAS = false;
                }
            }
        }
    }


    /*
     * Алгоритм преобразования инфиксной записи в постфиксную (алгоритм обратной польской записи)
     * Нужен для того что бы при анализе было легче воспринимать приоритет операций
     *
     * @param Integer
     */
    void toPosixA(const unsigned int &left, const unsigned int &right) {
        stack<Lexem> stack;
        vector<Lexem> changed;
        int i;
        for (i = left; i != right; ++i) {
            Lexem tmp = text->at(i);
            string tag = text->at(i).getTag();
            string lex = text->at(i).getLexem();
            if (tag == "Number" || tag == "Identification") {
                changed.push_back(tmp);
            } else if (tag == "Arithmetic operation") {
                while (!stack.empty() && checkPriorityA(stack.top().getLexem()) >= checkPriorityA(lex)) {
                    changed.push_back(stack.top());
                    stack.pop();
                }
                stack.push(tmp);
            }
        }
        while (!stack.empty()) {
            changed.push_back(stack.top());
            stack.pop();
        }
        for (int i = 0; i < changed.size(); ++i) {
            text->at(left + i) = changed[i];
        }
    }

    /*
     * Тоже самое только для булевых выражений
     */
    void toPosixL(const unsigned int &left, const unsigned int &right) {
        stack<Lexem> stack;
        vector<Lexem> changed;
        int i;
        for (i = left; i != right; ++i) {
            Lexem tmp = text->at(i);
            string tag = text->at(i).getTag();
            string lex = text->at(i).getLexem();
            if (tag == "Number" || tag == "Identification") {
                changed.push_back(tmp);
            } else if (tag == "not" || tag == "and" || tag == "or" || tag == "Compare operation") {
                while (!stack.empty() && checkPriorityL(stack.top().getTag()) >= checkPriorityL(tag)) {
                    changed.push_back(stack.top());
                    stack.pop();
                }
                stack.push(tmp);
            }
        }
        while (!stack.empty()) {
            changed.push_back(stack.top());
            stack.pop();
        }
        for (int i = 0; i < changed.size(); ++i) {
            text->at(left + i) = changed[i];
        }
    }

};

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

        SyntacticAnalyzer syntacticAnalyzer(readyToUse, executor);

        isWorking = executor.wishToContinue();
    }

    return 0;
}