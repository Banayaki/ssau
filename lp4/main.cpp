#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

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

int chooseTestType(Executor &executor) {
    cout
            << "Print 0 to sort your file\nPrint 1 to sort 1.000 elements\nPrint 2 to sort 5.000 elements\nPrint 3 to sort 10.000 elements"
            << endl;
    int type;
    cin >> type;
    while (!cin || type < 0 || type > 3) {
        cout << INCORRECT_INPUT << endl;
        executor.clearInputStream(cin);
        cin >> type;
    }
    return type;
}

template<typename T>
void openAndSort(ExecutorLp4 &executor, LinkedList<T> &list, const string &fileName) {
    executor.openFile(fileName);
    executor.readFile(list);
    double time = shellSort(list);
    executor.printAll("----- List sorted by " + to_string(time) + " -----" + EOL);
//    executor.getFout() << list.toString();
//    list.clear();
}

template<typename T>
void runTest(ExecutorLp4 &executor, LinkedList<T> &list) {
    int type = chooseTestType(executor);
    string fileName;
    if (type == 1) {
        executor.printAll("***----- Test on 1.000 elements -----***");
        fileName = "1_000_Elements.txt";
        openAndSort(executor, list, fileName);
        fileName = "L_1_000_Elements.txt";
        openAndSort(executor, list, fileName);
        fileName = "R_1_000_Elements.txt";
        openAndSort(executor, list, fileName);
        executor.printAll("***----- End of test -----***");
    } else if (type == 2) {
        executor.printAll("***----- Test on 5.000 elements -----***");
        fileName = "5_000_Elements.txt";
        openAndSort(executor, list, fileName);
        fileName = "L_5_000_Elements.txt";
        openAndSort(executor, list, fileName);
        fileName = "R_5_000_Elements.txt";
        openAndSort(executor, list, fileName);
        executor.printAll("***----- End of test -----***");
    } else if (type == 3) {
        executor.printAll("***----- Test on 10.000 elements -----***");
        fileName = "10_000_Elements.txt";
        openAndSort(executor, list, fileName);
        fileName = "L_10_000_Elements.txt";
        openAndSort(executor, list, fileName);
        fileName = "R_10_000_Elements.txt";
        openAndSort(executor, list, fileName);
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
    }
}

int main() {
//    createRandomFiles();
//    createLightlyRandomFiles();
//    createReverseFiles();
    //TODO некорректный формат не работает ыыыы
    //TODO сравнение даблов
    //TODO не работает clear
    //TODO сравнение времени сортировок
    ExecutorLp4 executor;
    LinkedList<double> list;
    executor.getFout().open(R"(C:\Users\Banayaki\Desktop\tests\output.txt)");
    runTest(executor, list);
    executor.getFout().close();
    executor.getFin().close();
    return 0;
}