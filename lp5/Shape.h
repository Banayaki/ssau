
#ifndef LP5_SHAPE_H
#define LP5_SHAPE_H

#include "BrokenLine.h"

class Shape : public BrokenLine {
private:
    unsigned double perimeter;
    bool isInsertion = false;
public:
    Shape();

    Shape(const Shape &shape);

    Shape(const Point &point);

    Shape(const unsigned long &count, const vector<Point> p, const vector<Line> l);

    Shape(const BrokenLine &polygon);

    Shape(const vector<Point> &points);

    ~Shape();

    const Point &operator[](const unsigned long &n);

    const bool operator>(const Shape &shape);

    const bool operator<(const Shape &shape);

    const bool operator>=(const Shape &shape);

    const bool operator<=(const Shape &shape);

    const bool operator==(const Shape &Shape);

    const bool operator!=(const Shape &Shape);

    Shape &operator=(const Shape &shape);

    Shape &operator+=(const Shape &shape);

    Shape &operator-=(const Shape &shape);

    Shape &operator+(const Shape &shape);

    Shape &operator-(const Shape &shape);

    void swap(Shape &shape);

    string toString();

    unsigned long getSize();

    void findPerimeter();
};


#endif //LP5_SHAPE_H
