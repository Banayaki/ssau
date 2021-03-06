#ifndef LINE_H
#define LINE_H

#include "Point.h"

class Line {
private:
    double A;
    double B;
    double C;
public:
    Line(const int &A, const int &B, const int &C);

    Line(const Point &first, const Point &second);

    void refresh(const Point &first, const Point &second);

    bool isInLine(const Point &point);

    string toString();

    double getA() const;

    double getB() const;

    double getC() const;
};

#endif