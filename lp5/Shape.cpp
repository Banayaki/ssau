//
// Created by Banayaki on 19-May-18.
//

#include "Shape.h"

Shape::Shape() : BrokenLine() {
    perimeter = 0;
}

Shape::Shape(const Shape &shape) {

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
        shp.swap(*this);
    }
    return *this;
}

Shape &Shape::operator+=(const Shape &shape) {
    return <#initializer#>;
}

Shape &Shape::operator-=(const Shape &shape) {
    return <#initializer#>;
}

Shape &Shape::operator+(const Shape &shape) {
    return <#initializer#>;
}

Shape &Shape::operator-(const Shape &shape) {
    return <#initializer#>;
}

void Shape::swap(Shape &shape) {
    this->BrokenLine::swap(*dynamic_cast<BrokenLine *>(shape));
    auto tmp1 = this->perimeter;
    this->perimeter = shape.perimeter;
    shape.perimeter = tmp1;
    auto tmp2 = this->isInsertion;
    this->isInsertion = shape.isInsertion;
    shape.isInsertion = tmp2;
}

string Shape::toString() {
    return BrokenLine::toString();
}

void findPerimeter() {

}
