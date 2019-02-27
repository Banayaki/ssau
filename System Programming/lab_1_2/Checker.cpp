#include <iostream>
#include "Executor.cpp"

using namespace std;

Executor executor;

int function(int a, int b, int c) {
    c *= 2;
    b /= 2;
    a *= a;
    c = c - b + 1;
    a -= 7;
    if (abs(a) < executor.getEps()) {
        executor.printAll("Arithmetic exception. Divide by zero. Exiting....");
    }
    return c / a;
}

int main() {
    try {
        int a, b, c;
        while (true) {
            a = executor.getInteger();
            b = executor.getInteger();
            c = executor.getInteger();
            executor.openFile("Props.txt");
            executor.printAll("Calculating the expression with params: a = " + to_string(a) + ", b = " + to_string(b) +
                              ", c = " + to_string(c));
            executor.printAll(to_string(function(a, b, c)));
            if (!executor.wishToContinue())
                return 0;
        }

    } catch (exception &e) {
        cout << "Exception thrown: " + string(e.what());
    }

}

void check(const int &number) {
}