#include "Point.h"

Point::Point(const double &x, const double &y) {
    this->x = x;
    this->y = y;
    isInPolygon = false;
}

Point::Point(const double &x, const double &y, const bool &isInPolygon) {
    this->x = x;
    this->y = y;
    this->isInPolygon = isInPolygon;
}

Point::~Point() = default;

double Point::getX() const {
    return x;
}

double Point::getY() const {
    return y;
}

bool Point::isIsInPolygon() const {
    return isInPolygon;
}

void Point::setIsInPolygon(bool isInPolygon) {
    this->isInPolygon = isInPolygon;
}
