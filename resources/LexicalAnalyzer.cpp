class Word {
private:
    bool valid;
    char *str;

public:
    Word(char *str, bool valid) {
        this->str = str;
        this->valid = valid;
    }

    char *getStr() {
        return this->str;
    }

    bool getValid() {
        return this->valid;
    }
};

//Класс - лексический анализатор, анализирует текст используя Конечный Автомат (далее КА)
class LexicalAnalyzer {
private:                        //Матрица переходов состояний КА
    const int matrix[4][3] = {  //      S    G    E
            1, 1, 3,            //  А...я    G    G     E
            1, 1, 3,            //  0...9    G    G     E
            2, 2, 3,            //  space    F    F     E
            3, 3, 3             //  other    E    E     E
    };

    enum LexCondition {         //Возможные состояния КА
        S, G, F, E              //S - начальное, G - корректно, F - заключительное, Е - некорректно
    };

public:

    int getSymbolGroup(const char &currentChar) {                                         //Для перехода по матрице, выбор нужной строки
        if (currentChar < 0) return 0;                                                    //Группа с Кириллицей
        else if (currentChar >= NUMBERS_BEGIN && currentChar <= NUMBERS_END) return 1;    //Группа чисел
        else if (currentChar == SPACE || currentChar == EOL || currentChar == CR || currentChar == EOS) return 2;   //Заключительные символы
        else return 3;                                                                    //Все остальные не корректны
    }

    //Проверка: Подходит ли слово условию
    bool checkWord(char *word, int length) {
        if (length == 1) {
            return getSymbolGroup(word[0]) == 1;
        } else
            return getSymbolGroup(word[0]) == 1 && getSymbolGroup(word[length - 1]) == 1;
    }

    //Добавление слова в результирующий вектор
    void addWord(vector<Word> &result, char *text, int beginP, int endP) {
        int length = endP - beginP;
        char *line = (char *) calloc(length + 1, sizeof(char));
        for (int i = 0; i < length; ++i) {
            line[i] = text[beginP + i];
        }
        result.emplace_back(Word(line, checkWord(line, length)));
    }

    //Метод выполняющий лексический анализ согласно матрице переходов КА
    void WordAnalysis(char *text, vector<Word> &result) {
        int currentPosition = 0;
        int beginPosition = 0;
        char currentChar;
        LexCondition condition = LexCondition::S;

        while (text[currentPosition] != EOS) {
            currentChar = text[currentPosition];

            if (condition == LexCondition::S && getSymbolGroup(currentChar) < 3) {
                if (getSymbolGroup(currentChar) == 2) {
                    ++currentPosition;
                    continue;
                }
                beginPosition = currentPosition;
            }
            condition = (LexCondition) matrix[getSymbolGroup(currentChar)][condition];

            if (condition == LexCondition::E) {
                while (getSymbolGroup(currentChar) != 2) {
                    currentChar = text[++currentPosition];
                }
                condition = LexCondition::S;
                beginPosition = currentPosition;
            }

            if (condition == LexCondition::F) {
                addWord(result, text, beginPosition, currentPosition);
                condition = LexCondition::S;
            }
            ++currentPosition;
        }

        if (condition == LexCondition::G) {
            addWord(result, text, beginPosition, currentPosition);
        }
    }
};