#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

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
            throw ERROR;
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
    }


    LinkedList(LinkedList *list) {
        addAll(list);
    }

    Element *get(const int &index) {
        Element *x = begin;
        if (index == size) {
            return nullptr;
        }
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

    void add(const int &index,const T &value) {
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
        //TODO проследить за nullptr
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

    void set(const int &index,const T &value) {
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
        last = this->get(size - 2);
        last->setNext(nullptr);
    }

    void removeFirst() {
        begin = this->get(1);
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
        for (Element *current = begin; current != last; ++current) {
            ss << current->getValue() << EOL;
        }
        return ss.str();
    }

    void clear() {
        for (Element *current = begin; current != last; ++current) {
            delete current;
        }
    }
};

class ExecutorLp4 : public Executor {
public:
    template<typename T>
    void readFile(LinkedList<T> &list) {
        T x = {};
        while (!fin.eof()) {
            fin >> x;
            list.add(x);
        }
    }
};

template<typename T>
double shellSort(LinkedList<T> &list) {
    LARGE_INTEGER start, finish, freq;
    QueryPerformanceFrequency(&freq);
    QueryPerformanceCounter(&start);

    int j;
    T tmp;
    int size = list.getSize();
    for (int step = size / 2; step > 0; step /= 2) {
        for (int i = step; i < size; i++) {
            tmp = list.get(i)->getValue();
            for (j = i; j >= step; j -= step) {
                if (tmp < list.get(j - step)->getValue()) {
                    list.get(j)->setValue(list.get(j - step)->getValue());
                } else break;
            }

            list.get(j)->setValue(tmp);
        }
    }

    QueryPerformanceCounter(&finish);
    return (finish.QuadPart - start.QuadPart) / (double) freq.QuadPart;
}


int main() {
//    createFiles();
    //TODO некорректный формат не работает ыыыы
    ExecutorLp4 executor;
    LinkedList<double> list;
    executor.openFile();
    executor.getFout().open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    executor.readFile(list);
    executor.printAll(list.toString());
    list.clear();
    executor.getFout().close();
    executor.getFin().close();
    return 0;
}