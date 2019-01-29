#ifndef LAB_1_POLYNOM_H
#define LAB_1_POLYNOM_H

class Polynom {
private:
    MyVector<double> coefficients;
    const int *degree;


public:
    Polynom(double &coefficients, const int &countOfCoef);

    bool isHeterogeneity();

    int getDegreeOfPolynom();

    double getCoefficient(const int &position);

    MyVector<double> getAllCoefficients();

    void normalize();

    string toString();
};

#endif //LAB_1_POLYNOM_H