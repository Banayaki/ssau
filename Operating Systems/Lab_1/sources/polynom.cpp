#include "../headers/GeneralHeader.h"
#include "../headers/polynom.h"

Polynom::Polynom(double &coefficients, const int &countOfCoef) {
    this->degree = &countOfCoef;
    this->coefficients = MyVector<double>(coefficients, countOfCoef + 1);
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
