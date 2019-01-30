#ifndef LAB_1_POLYNOMRESOLVER_H
#define LAB_1_POLYNOMRESOLVER_H

#include "Polynom.h"

class PolynomResolver {
public:
    PolynomResolver();

    PolynomResolver(const Polynom &polynom);

    MyVector<double> solvePolynom();

    void target(const Polynom &polynom);

    string toString();

private:
    Polynom polynom;
    Polynom firstDerivative;
    double rootRadius;
    double EPS = 10e-6;

    MyVector<double> splitRoots();

    void derivativePolynom();

    void calcRadius();
};

#endif //LAB_1_POLYNOMRESOLVER_H
