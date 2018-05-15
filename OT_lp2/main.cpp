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

//TODO вход числа в диапазон значений и длина строки максимальная, кек

/*
 * Это класс который выполняет роль синтаксического анализа, на данный момент
 * он находится на стадии разработки и не используется в программе
 */
class SyntacticAnalyzer {
private:
    enum Condition {
        begin, LS, CS, UO, LS1, BO, CO, assigm, OPS, AS, AO, ER, REC
    };

    Executor *executor;
    vector<Lexem> *text;
    Condition condition = Condition::begin;
    Condition prevCondition = Condition::begin;
    unsigned int size;

    const int waitingRoom[11][12] = {
            1, 11, 12, 11, 12, 11, 11, 11, 12, 11, 11, 11,
            11, 11, 0, 11, 0, 11, 11, 11, 0, 11, 11, 11,
            11, 3, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 5, 11, 5, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 9, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 9, 11,
            11, 2, 11, 2, 11, 4, 2, 11, 11, 10, 11, 11,
            11, 2, 8, 2, 8, 4, 2, 11, 7, 10, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 8, 11,
            11, 11, 6, 11, 11, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11
    };

    vector<string> nameOfCondition = {
            "\"Determination of construction\"", //0
            "\"Logical statement\"", //1
            "\"Compare statement\"", //2
            "\"Unary operation\"",
            "\"Logical statement\"", //1
            "\"Binary operation\"",
            "\"Compare operation\"",
            "\"Assignment\"",
            "\"Operators\"", //1
            "\"Arithmetic statement\"", //1
            "\"Arithmetic operation\"", //2
            "\"Error condition\"" //error
    };

    int getTagGroup(const string &tag) {
        if (tag == "do") return 0;
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
        this->size = text.size();
        this->executor = &executor;
        runAnalysis();
    }

    void runAnalysis() {
        unsigned int currentPos = 0;
        while (currentPos < size) {
            analysis(currentPos);
        }
    }

    void analysis(unsigned int &currentPos) {
        executor->printAll("");
        checkDoUntil(currentPos);
        logicalExpr(currentPos);
        operators(currentPos);
    }

    void checkDoUntil(unsigned int &currentPos) {
        unsigned int size = text->size();
        if (currentPos + 1 < size &&
            (text->at(currentPos).getTag() != "do" || text->at(currentPos + 1).getTag() != "until")) {
            executor->printAll(
                    "# I can't define the construction, because: " + text->at(currentPos).getLexem() + ", " +
                    text->at(currentPos + 1).getLexem() + " is unknown lexem's #");
            executor->printAll("# As I know only one construction, I think it would be a \"do until\" #");
            executor->printAll("# I will trying to find that construction!!! #");
        }
        while (currentPos + 1 < size &&
               (text->at(currentPos).getTag() != "do" || text->at(currentPos + 1).getTag() != "until")) {
            ++currentPos;
        }
        if (currentPos >= size - 1) {
            executor->printAll("# I reached the end of the text :C #");
            currentPos = size;
            return;
        }
        executor->printAll("# I found! #");
        executor->printAll("# It's a \"do until\" #");
        ++currentPos;
    }

    void logicalExpr(unsigned int &currentPos) {
        unsigned int beginPos = currentPos;
        condition = Condition::LS;
        prevCondition = Condition::LS;
        string currentTag;

        while (currentPos < size && !(condition == Condition::OPS &&
                                      currentTag == "Identification")) {
            ++currentPos;
            currentTag = text->at(currentPos).getTag();

            prevCondition = condition;
            condition = (Condition) waitingRoom[getTagGroup(currentTag)][condition];

            if (condition == Condition::REC) {
                if (currentPos + 1 < size && text->at(currentPos + 1).getTag() == "until") {
                    executor->printAll("% Begin inserted cycle %");
                    analysis(currentPos);
                    condition = Condition::OPS;
                    currentTag = text->at(currentPos).getTag();
                    executor->printAll("% End inserted cycle %\n");
                } else {
                    condition = Condition::ER;
                }
            }
            if (condition == Condition::ER) {
                printMessage(prevCondition, condition, currentPos);
                errorHandler(prevCondition, currentPos);
                currentTag = text->at(currentPos).getTag();
                executor->printAll("! Logical Expression is incorrect !");
                if (currentTag == "loop") {
                    condition = Condition::begin;
                } else {
                    condition = Condition::OPS;
                }
            } else {
                printMessage(prevCondition, condition, currentPos);
            }
            if (currentTag == "loop") {
                executor->printAll("! Empty body of cycle !");
                ++currentPos;
                return;
            }
        }
        if (currentPos >= size) {
            executor->printAll("# I reached the end of the text :C #");
            return;
        }
        executor->printAll("# Logical Expression was ended #");
        toPosixL(beginPos, currentPos - 1); //Убратb??
    }


    void operators(unsigned int &currentPos) {
        unsigned int beginPos = currentPos;
        bool isAS = false;

        while (currentPos < size && condition != Condition::begin) {
            string currentTag = text->at(currentPos).getTag();

            prevCondition = condition;
            condition = (Condition) waitingRoom[getTagGroup(currentTag)][condition];

            if (condition == Condition::REC) {
                if (currentPos + 1 < size && text->at(currentPos + 1).getTag() == "until") {
                    executor->printAll("% Begin inserted cycle %");
                    Condition inMind = prevCondition;
                    analysis(currentPos);
                    condition = prevCondition;
                    if (currentPos >= size && condition != Condition::begin) {
                        executor->printAll("! I think you are forget \"loop\"");
                        break;
                    }
                    executor->printAll("% End inserted cycle %");
                } else {
                    condition = Condition::ER;
                }
            }
            if (condition == Condition::ER) {
                printMessage(prevCondition, condition, currentPos);
                errorHandler(prevCondition, currentPos);
                if (text->at(currentPos).getTag() == "loop") {
                    ++currentPos;
                    break;
                }
                condition = Condition::OPS;
            } else {
                printMessage(prevCondition, condition, currentPos);
                if (!isAS && condition == Condition::AS) {
                    beginPos = currentPos;
                    isAS = true;
                } else if (currentTag == "Separator" && isAS) {
                    toPosixA(beginPos, currentPos - 1);
                    isAS = false;
                }
            }
            ++currentPos;
        }
        if (condition != Condition::begin) {
            executor->printAll("! I think you are forget \"loop\"");
        } else {
            executor->printAll("# End of body #");
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

    void printMessage(const Condition &prev, const Condition &current, const int &pos) {
        string lex = text->at(pos).getLexem();
        string tag = text->at(pos).getTag();
        if (prev == Condition::LS || (prev == Condition::LS1 && current == Condition::OPS) || prev == Condition::OPS) {
            executor->printAll(
                    "\t# I checked: \"" + lex + "\",with tag: \"" + tag + "\",in position: " + to_string(pos) + " #");
            executor->printAll("\t# From: " + nameOfCondition.at(prev) + " to: " + nameOfCondition.at(current) + " #");
        } else if (prev == Condition::begin) {
            executor->printAll(
                    "# I checked: \"" + lex + "\" ,with tag: " + tag + " ,in position: " + to_string(pos) + " #");
            executor->printAll(
                    "# From: " + nameOfCondition.at(prev) + " to: " + nameOfCondition.at(current) + " #");
        } else {
            executor->printAll(
                    "\t\t# I checked: \"" + lex + "\" ,with tag: " + tag + " ,in position: " + to_string(pos) + " #");
            executor->printAll(
                    "\t\t# From: " + nameOfCondition.at(prev) + " to: " + nameOfCondition.at(current) + " #");
        }
    }

    void errorHandler(const Condition &prev, unsigned int &pos) {
        string lexem = text->at(pos).getLexem();
        unsigned int size = text->size();
        executor->printAll("!!! Syntax error !!!");
        executor->printAll("! Catch the ERROR in this \"" + to_string(pos) + "\" position !");
        if (prev == AS || prev == CO || prev == UO || prev == BO) {
            executor->printAll(
                    "! I was in " + nameOfCondition.at(prev) + " and I was waiting for the number" +
                    " or the Identification !");
        } else if (prev == AO) {
            executor->printAll(
                    "! I was in " + nameOfCondition.at(prev) + " and I was waiting for the " +
                    nameOfCondition.at(prev) +
                    " or ';' !");
        } else if (prev == assigm) {
            executor->printAll(
                    "! I was in " + nameOfCondition.at(prev) + " and I was waiting for the '=' !");
        } else if (prev == OPS) {
            executor->printAll(
                    "! I was in " + nameOfCondition.at(prev) + " and I was waiting for the Identification, or" +
                    " end of construction (loop) !");
        } else if (prev == LS1) {
            executor->printAll(
                    "! I was in " + nameOfCondition.at(prev) + " and I was waiting for the end of construction (loop)" +
                    "or Binary Operation, or Identification !");
            if (lexem == "loop") {
                executor->printAll("! Empty body of cycle !");
            }
        } else if (prev == CS) {
            executor->printAll(
                    "! I was in " + nameOfCondition.at(prev) + " and I was waiting for the Binary Operation, or " +
                    " Compare Operation !");
        } else if (prev == LS) {
            executor->printAll(
                    "! I was in " + nameOfCondition.at(prev) + " and I was waiting for the Unary Operation, or " +
                    " Number, or Identification !");
            if (lexem == "loop") {
                executor->printAll("! Empty logical statement !");
            }

        }
        executor->printAll("! Erroneous lexem: \"" + lexem + "\",  check them !");

        if (pos == size - 1) {
            executor->printAll("# I reached the end of the text :C #");
        }

        if (prev == OPS || prev == assigm || prev == AS || prev == AO) {
            while (text->at(pos).getTag() != "Separator" && text->at(pos).getTag() != "loop") {
                executor->printAll("! Skip the: " + text->at(pos).getLexem() + " !");
                ++pos;
            }
            executor->printAll("! Separator/Loop was founded, move on to the next construction !");
        } else {
            while ((text->at(pos).getTag() != "Identification" || text->at(pos + 1).getTag() != "Assignment") &&
                   text->at(pos).getTag() != "loop") {
                executor->printAll("! Skip the: " + text->at(pos).getLexem() + " !");
                ++pos;
            }
            executor->printAll("! Identification + Assignment/Loop was founded, move on to the next construction !");
        }
    }
};

/*
 * Функция - запускающая главный поток, в котором происходят все манипуляции
 * с объектами и функциями
 * Здесь открывается файл, читается, лексически анализируется
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