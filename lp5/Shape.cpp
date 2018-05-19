//
// Created by Banayaki on 19-May-18.
//

#include "Shape.h"

Shape::Shape() : BrokenLine() {
    perimeter = 0;
}

Shape::Shape(const Shape &shape) {
    *this = shape;
}

Shape::Shape(const Point &point) : BrokenLine(point) {
    perimeter = 0;
}

Shape::Shape(const unsigned long &count, const vector<Point> p, const vector<Line> l) : BrokenLine(count, p, l) {
    findPerimeter();
}

Shape::Shape(const BrokenLine &polygon) : BrokenLine(polygon) {
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
    findPerimeter();
}

Shape::Shape(const vector<Point> &points) : BrokenLine(points) {
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
    findPerimeter();
}

Shape::~Shape() = default;

const Point &Shape::operator[](const unsigned long &n) {
    return BrokenLine::operator[](n);
}

const bool Shape::operator>(const Shape &shape) {
    return this->perimeter > shape.perimeter;
}

const bool Shape::operator<(const Shape &shape) {
    return this->perimeter < shape.perimeter;
}

const bool Shape::operator>=(const Shape &shape) {
    return this->perimeter >= shape.perimeter;
}

const bool Shape::operator<=(const Shape &shape) {
    return this->perimeter <= shape.perimeter;
}

const bool Shape::operator==(const Shape &shape) {
    return this->perimeter == shape.perimeter;
}

const bool Shape::operator!=(const Shape &shape) {
    return this->perimeter != shape.perimeter;
}

Shape &Shape::operator=(const Shape &shape) {
    if (this != &shape) {
        Shape shp(shape);
        Shape::swap(*this, shp);
    }
    return *this;
}

Shape &Shape::operator+=(const Shape &shape) {
    *this = *this + shape;
    return *this;
}

Shape &Shape::operator-=(const Shape &shape) {
    *this = *this - shape;
    return *this;
}

Shape &Shape::operator+(const Shape &shape) {
    this->lines.erase(lines.end());
    this->BrokenLine::operator+(*dynamic_cast<BrokenLine *>(shape));
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
    return *this;
}

Shape &Shape::operator-(const Shape &shape) {
    this->lines.erase(lines.end());
    this->BrokenLine::operator-(*dynamic_cast<BrokenLine *>(shape));
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
    return *this;
}

static void Shape::swap(Shape &first, Shape &second) {
    BrokenLine::swap(
            *dynamic_cast<BrokenLine *>(first),
            *dynamic_cast<BrokenLine *>(second)
    );
    auto tmp1 = first.perimeter;
    first.perimeter = second.perimeter;
    second.perimeter = tmp1;
    auto tmp2 = first.isInsertion;
    first.isInsertion = second.isInsertion;
    second.isInsertion = tmp2;
}

string Shape::toString() {
    return BrokenLine::toString();
}

void findPerimeter() {

}
