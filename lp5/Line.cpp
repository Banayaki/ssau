#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include "Line.h"
#include "Point.h"

Line::Line(const int &A, const int &B, const int &C) {
        this->A = A;
        this->B = B;
        this->C = C;
    }

    Line::Line(const Point &first, const Point &second) {
        refresh(first, second);
    }

    void Line::refresh(const Point &first, const Point &second) {
        double x1 = first.getX();
        double x2 = second.getX();
        double y1 = first.getY();
        double y2 = second.getY();
        this->A = y1 - y2;
        this->B = x2 - x1;
        this->C = (x1 * y2) - (x2 * y1);
    }

    bool Line::isInLine(const Point &point) {
        return (A * point.getX() + B * point.getY() + C) == 0;
    }

    string Line::toString() {
        return to_string(A) + "x " + to_string(B) + "y " + to_string(C) + " = 0";
    }
