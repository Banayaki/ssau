
#ifndef LP5_SHAPE_H
#define LP5_SHAPE_H

#include "BrokenLine.h"

class Shape : public BrokenLine {
private:

public:
    Shape();

    Shape(const Shape &shape);

    Shape(const Point &point);

    Shape(const BrokenLine &polygon);

    Shape(const vector<Point> &points);

    ~Shape();

    Shape &operator=(const Shape &shape);

    Shape &operator+=(Shape &shape);

    Shape &operator-=(Shape &shape);

    Shape operator+(Shape &shape);

    Shape operator-(Shape &shape);

    bool haveIntersection(const Shape &shape);

    bool isInPolygon(const Point &point, const double &eps);
};

ifstream &operator>>(ifstream &stream, Shape &shape);

#endif //LP5_SHAPE_H
