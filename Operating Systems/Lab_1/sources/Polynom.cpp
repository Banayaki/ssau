#include "../headers/GeneralHeader.h"

Polynom::Polynom(double *coefficients, const int &countOfCoef) {
    this->degree = countOfCoef;
    this->coefficients = MyVector<double>(coefficients, countOfCoef + 1);
}

double Polynom::value(const double &x) {
    double result = 0;
    int degree = this->getDegreeOfPolynom();
    for (int i = 0; i <= degree; ++i) {
        result += this->getCoefficient(i) * pow(x, degree - i);
    }
    return result;
}

int Polynom::getDegreeOfPolynom() {
    return this->degree;
}

double Polynom::getCoefficient(const int &position) {
    return coefficients[position];
}


Polynom::Polynom() {}

string Polynom::toString() {
    return this->coefficients.toString();
}