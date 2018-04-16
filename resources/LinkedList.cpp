
/*
 * Односвязный список
 * Это контейнер, который знает о том где его первый элемент, последний и размер
 *
 * Содержит inner-class Element который служит для работы с элементами коллекции
 * Так каждый элемент структуры содержит своё собственное значение и
 * Указывает на следующий элемент, если элемент последний то он содержит пустой
 * указатель
 *
 * LinkedList может хранить в себе любой тип/класс
 *
 * Имеет два конструктора:
 *      1) Конструктор без параметров
 *      2) Копирующий конструктор
 *
 * Так же имеет ряд методов для работы с элементами колеекции...
 *
 * Стандартные геттеры и сеттеры
 *
 * hasNext() возвращающая true если следующий элемент есть
 *
 * get(int index) метод возвращающий элемент стоящий на месте index, стоит отметить
 * что метод работает в среднем за время O(n/2)
 *
 * методы содержащие в названии add:
 * Очевидно что они добавляют элементы, addFirst(Т) добавляет элемент в начало коллекции
 * ( сложность О(1) ), аdd(Т) без указание индекса добавляет элемент в конец списка ( О(1) )
 * ну и add(index, T) добавление элемента в указанную позицию ( О(n) )
 * функии addAll() добавляют все элементы другого списка в тот из которого вызван метод
 * можно добавить как в конец списка, так и в указанную позицию
 *
 * метод set() изменяющий значение элемента по индексу
 *
 * методы семейства remove() - удаляют элементы, первый или последний за ( O(1) )
 * или по индексу за ( O(n) )
 *
 * startOfList и endOfList возвращают указатели на первый и последний элемент соотвественно
 *
 * toString() возвращает все элементы коллекции перечисленные в строке
 *
 * clear() очищает контейнер, память и тп
 */
template<typename T>
class LinkedList {
public:
    class Element;

private:
    Element *begin;
    Element *last;
    int size;

    void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw runtime_error(OUT_OF_BOUNDS);
        }
    }

public:
    class Element {
    private:
        T value;
        Element *next;
    public:
        Element() {}

        Element(const T &value, Element *next) {
            this->value = value;
            this->next = next;
        }

//        ~Element() {
//            delete next;
//        }

        Element *getNext() {
            return this->next;
        }

        T getValue() {
            return this->value;
        }

        void setNext(Element *next) {
            this->next = next;
        }

        bool hasNext() {
            return next != nullptr;
        }

        void setValue(const T &current) {
            this->value = current;
        }
    };


    LinkedList() {
        this->size = 0;
        this->begin = nullptr;
        this->last = nullptr;
    }


    LinkedList(LinkedList *list) {
        addAll(list);
    }

    ~LinkedList() {
        this->clear();
    }

    Element *get(const int &index) {
        checkPositionIndex(index);
        Element *x = begin;
        for (int i = 0; i < index; ++i) {
            x = x->getNext();
        }
        return x;
    }

    void addFirst(const T &value) {
        Element *newBegin = new Element(value, begin);
        begin = newBegin;
    }

    void add(const T &value) {
        Element *current = new Element(value, nullptr);
        if (size == 0) {
            begin = current;
            last = current;
        } else {
            last->setNext(current);
            last = current;
        }
        ++size;
    }

    void add(const int &index, const T &value) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        }
        if (index == 0) {
            addFirst(value);
        }
        Element *past = get(index - 1);
        Element tmp = *past->getNext();
        past->setNext(new Element(value, &tmp));
    }

    void addAll(const int &index, LinkedList *list) {
        checkPositionIndex(index);
        if (list->getSize() == 0) {
            return;
        }
        Element *target;
        target = this->get(index - 1);
        Element *tmp = target->getNext();
        for (int i = 0; i < list->getSize(); ++i) {
            target->setNext(list->get(i));
            target = target->getNext();
        }
        target->setNext(tmp);
        if (index == size) last = target;
    }

    void addAll(LinkedList *list) {
        addAll(size, list);
    }

    void set(const int &index, const T &value) {
        checkPositionIndex(index);
        Element *target = this->get(index);
        target->setValue(value);
    }

    void remove(const int &index) {
        checkPositionIndex(index);
        if (index == 0) {
            removeFirst();
        } else if (index == size - 1) {
            removeLast();
        } else {
            this->get(index - 1)->setNext(this->get(index + 1));
        }
    }

    void removeLast() {
        if (size == 0)
            return;
        if (size == 1) {
            begin = nullptr;
            last = nullptr;
            size = 0;
            return;
        }
        last = this->get(size - 2);
        last->setNext(nullptr);
        --size;
    }

    void removeFirst() {
        if (size == 0)
            return;
        if (size == 1) {
            begin = nullptr;
            last = nullptr;
            size = 0;
            return;
        }
        begin = this->get(1);
        --size;
    }

    Element *startOfList() {
        return this->begin;
    }

    Element *endOfList() {
        return this->last;
    }

    int getSize() {
        return size;
    }

    string toString() {
        stringstream ss;
        Element *current;
        for (current = begin; current != last; current = current->getNext()) {
            ss << current->getValue() << EOL;
        }
        ss << current->getValue() << EOL;
        return ss.str();
    }

    void clear() {
        for (Element *current = begin; current != last;) {
            Element *next = current->getNext();
            delete current;
            current = next;
        }
        begin = nullptr;
        last = nullptr;
        size = 0;
    }
};