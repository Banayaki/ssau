/*
 * Перечисление возможных тэгов лексемы, используются в программе не все, однако
 * другому пользователю может быть интересно узнать о возможностях анализатор без
 * подробного чтения кода
 * Do --- Or тэги так же используются для быстрого разбиения по группам
 */
enum Tag {
    DO, UNTIL, LOOP, NOT, AND, OR,
    ASSIGM, AO, COMPARE, NUM, INDET, WRONG
};

/*
 * Класс, имеющий два поля:
 * tag - хранит информацию о типе лексемы
 * lexem - содержит сам текст лексемы
 * Так же класс имеет два конструктора инициализации, и геттеры для обоих полей
 */
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

    string getTag() const{
        return this->tag;
    }

    string getLexem() {
        return this->lexem;
    }

    string getLexem() const {
        return this->lexem;
    }
};

/*
 * Класс лексический анализатор, в теории должен быть статическим, однако как в с++
 * это реализовать я не нашел.
 * Имеет три поля - матрицу переходов (КА), контейнер с ключевыми словами и
 * перечисление (содержать информацию о вохможных состояниях)
 * Конструктор стандартный
 */
class LexicalAnalyzer {
private:

    const int matrix[9][7] = {
            1, 1, 7, 8, 8, 8, 8,
            2, 1, 2, 8, 8, 8, 8,
            8, 8, 8, 8, 8, 8, 8,
            3, 8, 8, 8, 8, 8, 8,
            5, 8, 8, 4, 8, 8, 8,
            5, 8, 8, 4, 8, 4, 8,
            6, 8, 8, 8, 8, 8, 8,
            8, 8, 8, 8, 8, 8, 8,
            7, 7, 7, 8, 8, 8, 8,
    };

    const vector<string> KEY_WORDS = {"do", "until", "loop", "not", "and", "or"};

    enum LexCondition {
        S, A, B, C, D, G, H, E, F,
    };

public:
    /*
     * Метод возвращающая число, обозначающее к какой группе символов относится символ
     * Это число является номером строки в матрице переходов
     *
     * @param char
     * @return int
     */
    int getSymbolGroup(const char &currentChar) {
        if (currentChar >= BIG_EN_L_BEGIN && currentChar <= BIG_EN_L_END ||
            currentChar >= SMALL_EN_L_BEGIN && currentChar <= SMALL_EN_L_END) return 0;
        else if (currentChar >= NUMBERS_BEGIN && currentChar <= NUMBERS_END) return 1;
        else if (currentChar == SPACE || currentChar == EOL || currentChar == CR || currentChar == TAB) return 2;
        else if (currentChar == '<') return 3;
        else if (currentChar == '>') return 4;
        else if (currentChar == '=') return 5;
        else if (currentChar == '-' || currentChar == '*' || currentChar == '/' || currentChar == '+') return 6;
        else if (currentChar == ';') return 7;
        else return 8;
    }

    /*
     * Метод проверяющий является ли слово ключевым, если является то возвращает число,
     * по которому мы определяем индекс (какое это ключевое слово)
     *
     * @param string
     * @return int
     */
    int checkForMatch(const string &line) {
        int i = 0;
        for (const string &word : KEY_WORDS) {
            if (line == word) return i;
            ++i;
        }
    }

    /*
     * Функция добавляющее лексему в результирующий контейнер, осонвывается на работе
     * лексического анализатора. Вытаксивает из текста подстроку используя переданные индексы
     * и проверяет к какой группе относится слово
     */
    void addWord(vector<Lexem> &result, const string &text,
                 const int &beginP, const int &endP) {
        int length = endP - beginP;
        if (length == 0)
            return;
        string line = text.substr(beginP, length);
        int tag = checkForMatch(line);

        if (tag < KEY_WORDS.size()) {
            result.emplace_back(Lexem((Tag) tag, line));
        } else if (line == "=") {
            result.emplace_back(Lexem("Assignment", line));
        } else if (line == "+" || line == "-" || line == "*" || line == "/") {
            result.emplace_back(Lexem("Arithmetic operation", line));
        } else if (line == ">" || line == "<" || line == "<>" || line == "==" || line == "<=" || line == ">=") {
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

    /*
     * Функция выполняющая основновое действие анализатора. Анализирует переданный ей
     * текст и составляет перечень лексем (в порядке в котором был передан текст)
     * Предполагается что результат впоследствии будет использоваться синтаксическим анализатором
     * Анализатор работает на осонве автоматной грамматики, так же благодаря его работе
     * на этом этапе мы уже можем найти некоторые ошибки
     *
     * @param string, vector<Lexem>
     */
    void wordAnalysis(const string &text, vector<Lexem> &result) {
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
                int group = getSymbolGroup(currentChar);
                while ((group == 8 || group == 0 || group == 1) && currentChar != EOS) {
                    currentChar = text[++currentPosition];
                    group = getSymbolGroup(currentChar);
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
                if (getSymbolGroup(currentChar) != 2 && currentChar != ';') continue;
            }
            ++currentPosition;
        }
        if (lexCondition != LexCondition::S) {
            addWord(result, text, beginPosition, currentPosition);
        }
    }
};