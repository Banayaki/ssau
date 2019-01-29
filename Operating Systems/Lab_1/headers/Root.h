#ifndef LAB_1_ROOT_H
#define LAB_1_ROOT_H

class Root {
private:
    const double realPart;
    const double imaginaryPart;
public:

    Root(const double &realPart, const double &imaginaryPart);

    double getRealPart();

    double getImaginaryPart();

    string toString();
};

#endif //LAB_1_ROOT_H
