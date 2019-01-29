#include "../headers/GeneralHeader.h"
#include "../headers/PolynomResolver.h"

PolynomResolver::PolynomResolver(Polynom &polynom) {
    this->polynom = &polynom;
    derivativePolynom();
    calcRadius();
}

void PolynomResolver::derivativePolynom() {
    int degree = this->polynom->getDegreeOfPolynom();
    auto *firstCoefs = new double[degree];
    auto *secondCoefs = new double[degree - 1];
    for (int i = degree, pos = 0; i > 1; --i, ++pos) {
        double newCoef = this->polynom->getCoefficient(pos) * i;
        firstCoefs[pos] = newCoef;
        secondCoefs[pos] = newCoef * (i - 1);
    }
    firstCoefs[degree - 1] = this->polynom->getCoefficient(degree - 1);

    this->firstDerivative = Polynom(*firstCoefs, degree);
    this->secondDerivative = Polynom(*secondCoefs, degree - 1);
}

MyVector<Root> PolynomResolver::solvePolynom() {

}

MyVector<double> PolynomResolver::splitRoots() {
    auto spliters = MyVector<double>();
    double radius = this->rootRadius;
    double degree = this->polynom->getDegreeOfPolynom();
    //todo оптимальный выбор
    double dx = 10e-2;
    double length = (radius + radius) / degree;
    double left = radius;
    double leftVal = 0;
    double rightVal = 0;
    double leftBorder;
    double right = 0;
    for (int i = 0; i <= degree; ++i) {
        while (true) {
            right = left + length;
            leftBorder = left;

            leftVal = this->polynom->value(left);
            rightVal = this->polynom->value(right);
//            Как минимум один корень на промежутке, возможно нужно что бы на промежутке он был единственный
            if ((leftVal < 0 && rightVal > 0) || (leftVal > 0 && rightVal < 0)) {
                leftVal = this->firstDerivative.value(left);
                while (left < right) {
                    rightVal = this->firstDerivative.value(left + dx);
                    if ((leftVal < 0 && rightVal > 0) || (leftVal > 0 && rightVal < 0)) {
                        //монотонна слева
                        left = right;
                        break;
                    }
                    leftVal = rightVal;
                    left += dx;
                }
                break;

            } else {
                left = right;
            }
        }


        spliters.push_back(leftBorder);
        spliters.push_back(left);
    }
    return spliters;
}

/**
 * Find circle where are the roots by formulas
 */
void PolynomResolver::calcRadius() {
    // Values for finding up borders, for positive roots
    int positiveFirstNegativePos = -1;
    double positiveMaxAbsoluteNegative = 0;

    // for negative roots
    int negativeFirstNegativePos = -1;
    double negativeMaxAbsoluteNegative = 0;

    bool odd = this->polynom->getDegreeOfPolynom() & 1;

    for (int i = 0; i <= this->polynom->getDegreeOfPolynom(); ++i, odd = !odd) {
        double coefficient = this->polynom->getCoefficient(i);
        if (positiveFirstNegativePos == -1 && coefficient < 0) positiveFirstNegativePos = i;
        if (coefficient < 0 && abs(coefficient) > positiveMaxAbsoluteNegative)
            positiveMaxAbsoluteNegative = abs(coefficient);

        if (negativeFirstNegativePos == -1) {
            if ((odd && coefficient < 0) || (!odd && (-1 * coefficient) < 0)) negativeFirstNegativePos = i;
        }
        if ((odd && coefficient < 0 && abs(coefficient) > positiveMaxAbsoluteNegative) ||
            (!odd && (-1) * coefficient < 0 && abs(coefficient) > positiveMaxAbsoluteNegative))
            negativeMaxAbsoluteNegative = abs(coefficient);
    }
    double positiveRadius = 0;
    double negativeRadius = 0;
    if (positiveFirstNegativePos != -1)
        positiveRadius =
                1 + pow(positiveMaxAbsoluteNegative / this->polynom->getCoefficient(0), 1 / positiveFirstNegativePos);
    else if (negativeFirstNegativePos != -1)
        negativeRadius =
                1 + pow(negativeMaxAbsoluteNegative / this->polynom->getCoefficient(0), 1 / negativeFirstNegativePos);

    this->rootRadius = (positiveRadius > negativeRadius) ? positiveRadius : negativeRadius;
}