#include "../headers/GeneralHeader.h"
#include "../headers/polynom.h"

Polynom::Polynom(double &coefficients, const int &countOfCoef) {
    this->degree = &countOfCoef;
    this->coefficients = MyVector<double>(coefficients, countOfCoef + 1);
}

double Polynom::value(const double &x) {
    double result = 0;
    int degree = this->getDegreeOfPolynom();
    for (int i = 0; i <= degree; ++i) {
        result += (degree - i) * this->getCoefficient(i);
    }
    return result;
}

int Polynom::getDegreeOfPolynom() {
    return *this->degree;
}

MyVector<double> Polynom::getAllCoefficients() {
    return coefficients;
}

double Polynom::getCoefficient(const int &position) {
    return coefficients[position];
}

//todo compareTO
bool Polynom::isHeterogeneity() {
    return this->coefficients[this->degree] == 0;
}

Polynom::Polynom() {}
