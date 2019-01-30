#include "../headers/GeneralHeader.h"

PolynomResolver::PolynomResolver() {

}

PolynomResolver::PolynomResolver(const Polynom &polynom) {
    this->target(polynom);
}

void PolynomResolver::target(const Polynom &polynom) {
    this->polynom = polynom;
    derivativePolynom();
    calcRadius();
}

void PolynomResolver::derivativePolynom() {
    int degree = this->polynom.getDegreeOfPolynom();
    auto *coefficients = new double[degree];
    for (int i = degree, pos = 0; i > 0; --i, ++pos) {
        double newCoef = this->polynom.getCoefficient(pos) * i;
        coefficients[pos] = newCoef;
    }

    this->firstDerivative = Polynom(coefficients, degree - 1);
}

MyVector<double> PolynomResolver::solvePolynom() {
    MyVector<double> delimiters = splitRoots();
    MyVector<double> result;

    double x = 0;
    double approxRoot = 0;
    bool isRoot = true;
    for (int i = 0; i < delimiters.size(); i += 2) {
        isRoot = true;
        x = delimiters[i];
        approxRoot = x - (this->polynom.value(x) / this->firstDerivative.value(x));
        while (abs(x - approxRoot) > this->EPS) {
            x = approxRoot;
            approxRoot = x - (this->polynom.value(x) / this->firstDerivative.value(x));
            if (approxRoot < delimiters[i] || approxRoot > delimiters[i + 1]) {
                isRoot = false;
                break;
            }
        }
        if (isRoot) {
            result.push_back(approxRoot);
        }
    }

    return result;
}

MyVector<double> PolynomResolver::splitRoots() {
    //Precision parameters
    double dx = 10e-6;
    double length = 10e-5;

    MyVector<double> delimiters;
    double radius = this->rootRadius;
    double left = -radius;
    double rightBorder = radius;
    double leftVal = 0;
    double rightVal = 0;
    double right = 0;
    double leftBorder;

    while (left < rightBorder) {
        right = left + length;
        leftBorder = left;

        leftVal = this->polynom.value(left);
        rightVal = this->polynom.value(right);

        if ((leftVal < 0 && rightVal > 0) || (leftVal > 0 && rightVal < 0)) {
            leftVal = this->firstDerivative.value(left);

            while (left < right) {
                rightVal = this->firstDerivative.value(left + dx);
                if ((leftVal < 0 && rightVal > 0) || (leftVal > 0 && rightVal < 0)) {
                    left = right;
                    break;
                }
                leftVal = rightVal;
                left += dx;
            }

            delimiters.push_back(leftBorder);
            delimiters.push_back(left);

        } else {
            leftVal = this->firstDerivative.value(left);
            rightVal = this->firstDerivative.value(right);
            if ((leftVal < 0 && rightVal > 0) || (leftVal > 0 && rightVal < 0)) {
                delimiters.push_back(leftBorder);
                delimiters.push_back(right);
            }
            left = right;
        }
    }
    return delimiters;
}

/**
 * Find circle where are the roots by formulas
 */
void PolynomResolver::calcRadius() {
    int degree = this->polynom.getDegreeOfPolynom();
    // Values for finding up borders, for positive roots
    int positiveFirstNegativePos = -1;
    double positiveMaxAbsoluteNegative = 0;

    // for negative roots
    int negativeFirstNegativePos = -1;
    double negativeMaxAbsoluteNegative = 0;

    bool odd = !(degree & 1);

    for (int i = 0; i < degree; ++i, odd = !odd) {
        double coefficient = this->polynom.getCoefficient(i);
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
    double heterogeneity = this->polynom.getCoefficient(degree);

    if (heterogeneity < 0) {
        if (abs(heterogeneity) > positiveMaxAbsoluteNegative)
            positiveMaxAbsoluteNegative = abs(heterogeneity);
        if (abs(heterogeneity) > negativeMaxAbsoluteNegative)
            negativeMaxAbsoluteNegative = abs(heterogeneity);
        if (positiveFirstNegativePos == -1)
            positiveFirstNegativePos = degree;
        if (negativeFirstNegativePos == -1)
            negativeFirstNegativePos = degree;
    }

    double positiveRadius = 1;
    double negativeRadius = 1;

    if (positiveFirstNegativePos != -1)
        positiveRadius =
                1.5 + pow(positiveMaxAbsoluteNegative / this->polynom.getCoefficient(0),
                          1 / ((positiveFirstNegativePos == 0) ? 1 : positiveFirstNegativePos));
    if (negativeFirstNegativePos != -1)
        negativeRadius =
                1.5 + pow(negativeMaxAbsoluteNegative / this->polynom.getCoefficient(0),
                          1 / ((negativeFirstNegativePos == 0) ? 1 : negativeFirstNegativePos));

    this->rootRadius = (positiveRadius > negativeRadius) ? positiveRadius : negativeRadius;
}

string PolynomResolver::toString() {
    string text = "Current polynom: ";
    int degree = this->polynom.getDegreeOfPolynom();
    for (int i = 0; i < degree; ++i) {
        text += to_string(this->polynom.getCoefficient(i)) + "x^" + to_string(degree - i) + " + ";
    }
    text += to_string(this->polynom.getCoefficient(degree)) + "\r\n";
    text += "Radius ~ " + to_string(this->rootRadius) + "\r\n";
    return text;
}