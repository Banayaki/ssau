#include "Point.h"

Point::Point(const double &x, const double &y) {
    this->x = x;
    this->y = y;
}

Point::~Point() = default;

double Point::getX() const {
    return x;
}

double Point::getY() const {
    return y;
}
