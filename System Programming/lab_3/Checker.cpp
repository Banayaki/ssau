#include <iostream>
#include "Executor.cpp"

using namespace std;

Executor executor;

int function(int a, int b) {
    if (a == b) {
        return 10;
    } 
    if (a < b) {
        return (3 * a - 5) / (b - 1);
    }
    return (1 - b*b)/a;
}

int main() {
    try {
        while (true) {
            int a, b;
            executor.openFile("Props.txt");
            a = executor.getInteger();
            b = executor.getInteger();
            executor.printAll("Calculating the expression with params: a = " + to_string(a) + ", b = " + to_string(b));
            executor.printAll(to_string(function(a, b)));
            if (!executor.wishToContinue())
                return 0;
        }
    } catch (exception &e) {
        cout << "Exception thrown: " + string(e.what());
    }
}
