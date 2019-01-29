#ifndef LAB_1_POLYNOMRESOLVER_H
#define LAB_1_POLYNOMRESOLVER_H

#include "polynom.h"

class PolynomResolver {
public:
    PolynomResolver(Polynom &polynom);

    bool hasTrivialSolution();

    MyVector<Root> onlyRealRoots();

    MyVector<Root> solvePolynom();

private:
    Polynom *polynom;
    Polynom firstDerivative;
    Polynom secondDerivative;
    double rootRadius;

    MyVector<double> splitRoots();

    void derivativePolynom();

    void calcRadius();
};

#endif //LAB_1_POLYNOMRESOLVER_H
