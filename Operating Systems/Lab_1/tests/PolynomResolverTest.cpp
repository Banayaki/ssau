#include "../headers/GeneralHeader.h"

class Test {
private:
    Executor executor;

    void solve(int degree, double *coefs) {
        PolynomResolver polynomResolver;

        auto *polynom = new Polynom(coefs, degree);
        polynomResolver.target(*polynom);
        string text = polynomResolver.toString();
        MyVector<double> roots = polynomResolver.solvePolynom();
        text += "Roots: ";
        for (int i = 0; i < roots.size(); ++i) {
            text += to_string(roots[i]) + ", ";
        }
        executor.printAll(text);
    }

public:
    void autoTest() {
        double *coefs;

        //Test for degree 2
        executor.printAll("Example with degree = 2");
        int degree = 2;
        // y=x^2
        coefs = new double[3]{1, 0, 0};
        solve(degree, coefs);

        //y = (x-2)^2 = x^2 - 4x + 4
        coefs = new double[3]{1, -4, 4};
        solve(degree, coefs);

        // y = x^2 + 2
        coefs = new double[3]{1, 0, 2};
        solve(degree, coefs);

        //y = x^2 - 3x + 2
        coefs = new double[3]{1, -3, 2};
        solve(degree, coefs);

        //y = x^2 - 0.0011 + 10e-9
        coefs = new double[3]{1, -0.0011, 10e-9};
        solve(degree, coefs);

        //Test for degree 5
        executor.printAll("Example with degree = 5");
        degree = 5;
        coefs = new double[6]{3, 5, 7, -4, -10};
        solve(degree, coefs);

        coefs = new double[6]{-3, 5, 7, -4, -10};
        solve(degree, coefs);

        coefs = new double[6]{3, -5, -7, -4, -10};
        solve(degree, coefs);
    }

    void userTest() {

    }
};

int main() {
    Test test;
    test.autoTest();
}