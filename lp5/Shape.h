
#ifndef LP5_SHAPE_H
#define LP5_SHAPE_H

#include "BrokenLine.h"

class Shape : public BrokenLine {
private:

public:
    Shape();

    Shape(const Shape &shape);

    Shape(const Point &point);

    Shape(const unsigned long &count, const vector<Point> &p, const vector<Line> &l);

    Shape(const BrokenLine &polygon);

    Shape(const vector<Point> &points);

    ~Shape();

//    const Point &operator[](const unsigned long &n);
//
//    const bool operator>(const Shape &shape);
//
//    const bool operator<(const Shape &shape);
//
//    const bool operator>=(const Shape &shape);
//
//    const bool operator<=(const Shape &shape);
//
//    const bool operator==(const Shape &Shape);
//
//    const bool operator!=(const Shape &Shape);

    Shape &operator=(const Shape &shape);

    Shape &operator+=(Shape &shape);

    Shape &operator-=(Shape &shape);

    Shape &operator+(Shape &shape);

    Shape &operator-(Shape &shape);

//    static void swap(Shape &first, Shape &second);

    bool haveIntersection(const Shape &shape);

    bool isInPolygon(const Point &point, const double &eps);
};

ifstream &operator>>(ifstream &stream, Shape &shape);
//
//ostream &operator<<(ostream &stream, Shape &shape);
//
//ofstream &operator<<(ofstream &stream, Shape &shape);

#endif //LP5_SHAPE_H
