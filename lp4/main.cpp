#include <limits>
#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

/*
 * Класс исполнитель для 4-ой лабараторной рабы
 * Родительский класс Executor, предназначенный для работы с потокакми ввода, вывода,
 * открытия и закрытия файлов и еще несколько служебных методов и функций
 * ExecutorLp4 не переоопределяет методов родительского класса
 * Добавляет новый - чтение файла
 */

class ExecutorLp4 : public Executor {
public:
    /*
     * Выполняет функцию чтения файла и записи данных в коллекцию LinkedList
     * Аргументом функции является LinkedList в которой мы запоминаем данные
     * Сам метод читает из файла данные типа, который имеет LinkedList
     *
     * @param LinkedList<T>
     * @see Executor
     */
    template<typename T>
    void readFile(LinkedList<T> &list) {
        T x;
        while (!fin.eof()) {
            x = numeric_limits<T>::quiet_NaN();
            fin >> x;
            if (x != numeric_limits<T>::quiet_NaN()) list.add(x);
        }
        list.removeLast();
        this->fin.close();
    }
};

/*
 * Сортировка Шелла
 * Функция считает время выполнения кода (время выполнения сортировки)
 * Помимо этого, очевидно, что она сортирует коллекцию LinkedList
 * Аргументом функции является ссылка на коллекцию
 *
 * Сама сортировка Шелла - есть модификация сортировки вставками
 *
 * Сортировка работает быстрее дефолтной сортировки вставками,
 * т.к. коллекция элементов делится на некие подгруппы, т.е.
 * мы формируем не, отсортированную и не отсортированную
 * последовательностии, а множество отсортированных подпоследовательностей.
 * Вконце сортировки уже не будет разделения на группы, а начнется обычная
 * сортировка вставками, но т.к. все элементы максимально упорядочены, то и
 * вконце потребуется малое количество действий
 *
 * Можно ускорить сортировку если использовать метод Седжвика (выбор оптимальной длины
 * подпоследовательностей)
 *
 *
 * @param LinkedList<T>
 * @return double
 */
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

/*
 * Функция сравнивает значения затраченного времени на сортировку файла с разным содержимым
 * и выводи результат в файл и консоль
 *
 * @param double*
 * @return string
 */
string compare(const double *times) {
    if (times[0] < times[1] && times[0] < times[2]) {
        if (times[1] < times[2]) {
            string line = " " + to_string(times[0]) + " < " + to_string(times[1]) + " < " + to_string(times[2]) + "\n";
            line += "File with random elements sorts faster.\n";
            return line;
        } else {
            string line = " " + to_string(times[0]) + " < " + to_string(times[2]) + " < " + to_string(times[1]) + "\n";
            line += "File with random elements sorts faster.\n";
            return line;
        }
    } else if (times[1] < times[0] && times[1] < times[2]) {
        if (times[0] < times[2]) {
            string line = " " + to_string(times[1]) + " < " + to_string(times[0]) + " < " + to_string(times[2]) + "\n";
            line += "File with light random elements sorts faster.\n";
            return line;
        } else {
            string line = " " + to_string(times[1]) + " < " + to_string(times[2]) + " < " + to_string(times[0]) + "\n";
            line += "File with light random elements sorts faster.\n";
            return line;
        }
    } else {
        if (times[1] < times[0]) {
            string line = " " + to_string(times[2]) + " < " + to_string(times[1]) + " < " + to_string(times[0]) + "\n";
            line += "File with reverse elements sorts faster.\n";
            return line;
        } else {
            string line = " " + to_string(times[2]) + " < " + to_string(times[0]) + " < " + to_string(times[1]) + "\n";
            line += "File with reverse elements sorts faster.\n";
            return line;
        }
    }
}

/*
 * Функция которая открывает файл, читает значения, заполняет коллекцию,
 * вызывает сортировку и возвращает время затраченное на сортировку
 * коллекции.
 * Так же очищает после всех операций контейнер и закрывает поток чтения из файла
 *
 * @param ExecutorLp4, LinkedList<T>, string
 * @return double
 */
template<typename T>
double openAndSort(ExecutorLp4 &executor, LinkedList<T> &list, const string &fileName) {
    executor.openFile(fileName);
    executor.readFile(list);
    double time = shellSort(list);
    executor.printAll("----- List sorted by " + to_string(time) + " -----" + EOL);
//    executor.getFout() << list.toString();
    list.clear();
    executor.getFin().close();
    return time;
}

/*
 * Функция, которая запускает тест, взависимости от выбранного типа теста
 * Тест - чтение, заполнение, сортировка и вывод результата
 * Четыре вида тестов:
 * - Три файла: заполненный рандомными элементами, почти отсортированный и отсортированный в обратном порядке
 * 1) в каждом файле 1.000 элементов
 * 2) в каждом файле 5.000 элементов
 * 3) в каждом файле 10.000 элементов
 * - Один файл: файл выбранный пользователем, любое кол-во данных, любой тип
 *
 * @param ExecutorLp4, LinkedList<T>
 */
template<typename T>
void runTest(ExecutorLp4 &executor, LinkedList<T> &list) {
    int type = chooseTestType(executor);
    string fileName;
    double times[3];
    if (type == 1) {
        executor.printAll("***----- Test on 1.000 elements -----***\n");
        fileName = "1_000_Elements.txt";
        times[0] = openAndSort(executor, list, fileName);
        fileName = "L_1_000_Elements.txt";
        times[1] = openAndSort(executor, list, fileName);
        fileName = "R_1_000_Elements.txt";
        times[2] = openAndSort(executor, list, fileName);
        executor.printAll(compare(times));
        executor.printAll("***----- End of test -----***");
    } else if (type == 2) {
        executor.printAll("***----- Test on 5.000 elements -----***\n");
        fileName = "5_000_Elements.txt";
        times[0] = openAndSort(executor, list, fileName);
        fileName = "L_5_000_Elements.txt";
        times[1] = openAndSort(executor, list, fileName);
        fileName = "R_5_000_Elements.txt";
        times[2] = openAndSort(executor, list, fileName);
        executor.printAll(compare(times));
        executor.printAll("***----- End of test -----***");
    } else if (type == 3) {
        executor.printAll("***----- Test on 10.000 elements -----***\n");
        fileName = "10_000_Elements.txt";
        times[0] = openAndSort(executor, list, fileName);
        fileName = "L_10_000_Elements.txt";
        times[1] = openAndSort(executor, list, fileName);
        fileName = "R_10_000_Elements.txt";
        times[2] = openAndSort(executor, list, fileName);
        executor.printAll(compare(times));
        executor.printAll("***----- End of test -----***");
    } else {
        executor.printAll("***----- Custom test -----***");
        while (!executor.getFin().is_open()) {
            executor.openFile();
        }
        executor.readFile(list);
        double time = shellSort(list);
        executor.printAll("----- List sorted by " + to_string(time) + " -----" + EOL);
        executor.getFout() << list.toString();
        list.clear();
        executor.getFin().close();
        executor.printAll("***----- End of test -----***\n");
    }
}

/*
 * Первые три закоментированные строки запускают создание файлов со случайными значениями в них
 * Вызывает функцию runTest - в которой происходит то, ради чего написана программа
 * В остальном нет ничего интересного
 */
int main() {
//    createRandomFiles();
//    createLightlyRandomFiles();
//    createReverseFiles();
    ExecutorLp4 executor;
    executor.getFout().open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    bool isWorking = true;
    while (isWorking) {
        LinkedList<double> list;
        try {
            runTest(executor, list);
        } catch (runtime_error &ex) {
            executor.printAll(ex.what());
        }
        isWorking = executor.wishToContinue();
    }
    return 0;
}