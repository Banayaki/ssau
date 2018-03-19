class Word {
private:
    bool valid;
    wchar_t *str;

public:
    Word(wchar_t *str, bool valid) {
        this->str = str;
        this->valid = valid;
    }

    wchar_t *getStr() {
        return this->str;
    }

    bool getValid() {
        return this->valid;
    }
};

//Класс - лексический анализатор, анализирует текст используя Конечный Автомат (далее КА)
class LexicalAnalyzer {
private:
    const int matrix[4][4] = {      //              S   G   X   E
            4, 1, 1, 4,             //  А,...,я     E   G   G   E
            2, 2, 2, 4,             //  0,...,9     X   X   X   E
            3, 4, 3, 4,             //  spaces      F   E   F   E
            4, 4, 4, 4              //  other       E   E   E   E
    };

    enum LexCondition {         //Возможные состояния КА
        S, G, X, F, E              //S - начальное, G - корректное, Х - помогает остледить окончание на цифру, F - заключительное, Е - некорректно
    };

public:

    int getSymbolGroup(const wchar_t &currentChar) {                                                                    //Для перехода по матрице, выбор нужной строки
        if (currentChar >= 1040 && currentChar <= 1103) return 0;                                                       //Группа с Кириллицей
        else if (currentChar >= NUMBERS_BEGIN && currentChar <= NUMBERS_END) return 1;                                  //Группа чисел
        else if (currentChar == SPACE || currentChar == EOL || currentChar == CR || currentChar == EOS) return 2;       //Заключительные символы
        else return 3;                                                                                                  //Все остальные не корректны
    }

    //Добавление слова в результирующий вектор
    void addWord(vector<Word> &result, wchar_t *text, int beginP, int endP, bool isValid) {
        int length = endP - beginP;
        wchar_t *line = (wchar_t *) calloc(length + 1, sizeof(wchar_t));
        for (int i = 0; i < length; ++i) {
            line[i] = text[beginP + i];
        }
        result.emplace_back(Word(line, isValid));
    }

    //Метод выполняющий лексический анализ согласно матрице переходов КА
    void wordAnalysis(wchar_t *text, vector<Word> &result) {
        int currentPosition = 0;
        int beginPosition = 0;
        wchar_t currentChar;
        LexCondition condition = LexCondition::S;

        while (text[currentPosition] != EOS) {
            currentChar = text[currentPosition];

            if (condition == LexCondition::S) {
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
                addWord(result, text, beginPosition, currentPosition, false);
                condition = LexCondition::S;
                beginPosition = currentPosition;
            }

            if (condition == LexCondition::F) {
                addWord(result, text, beginPosition, currentPosition, true);
                condition = LexCondition::S;
            }
            ++currentPosition;
        }

        if (condition == LexCondition::X) {
            addWord(result, text, beginPosition, currentPosition, true);
        }
    }
};